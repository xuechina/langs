package com.xuek.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

/**
 * ����Ⱦ��,����������
 */
public class MyPointRenderer1 extends AbstractMyRenderer{

	public void onDrawFrame(GL10 gl) {
		//�����ɫ������
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//���û�ͼ��ɫ
		gl.glColor4f(1f, 0f, 0f, 1f);
		
		//����ģ����ͼ����
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//��������Ĳ���
		GLU.gluLookAt(gl,0f,0f,5f, 0f, 0f, 0f, 0f,1f,0f);
		
		//��ת�Ƕ�
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);
		
		//���������
		float r = 0.5f ;//�뾶
		List<Float> coordsList = new ArrayList<Float>();
		float x = 0f,y = 0f,z = 1f ;
		float zstep = 0.01f ;
		for(float alpha = 0f ; alpha < Math.PI * 6 ; alpha = (float) (alpha + Math.PI / 16)){
			x = (float) (r * Math.cos(alpha));
			y = (float) (r * Math.sin(alpha));
			z = z - zstep ;
			coordsList.add(x);
			coordsList.add(y);
			coordsList.add(z);
		}
		
		//ת�����Ϊ������
		ByteBuffer ibb = ByteBuffer.allocateDirect(coordsList.size() * 4);
		ibb.order(ByteOrder.nativeOrder());
		FloatBuffer fbb = ibb.asFloatBuffer();
		for(float f : coordsList){
			fbb.put(f);
		}
		ibb.position(0);
		
		//ָ������ָ��
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, ibb);
		gl.glDrawArrays(GL10.GL_POINTS, 0, coordsList.size() / 3);
	}
}

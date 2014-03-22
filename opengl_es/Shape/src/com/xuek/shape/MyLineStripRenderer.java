package com.xuek.shape;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import com.xuek.shape.util.BufferUtil;

/**
 * �ߴ�,��������,���պ�
 */
public class MyLineStripRenderer extends AbstractMyRenderer{

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
		float zstep = 0.005f ;
		for(float alpha = 0f ; alpha < Math.PI * 6 ; alpha = (float) (alpha + Math.PI / 32)){
			x = (float) (r * Math.cos(alpha));
			y = (float) (r * Math.sin(alpha));
			z = z - zstep ;
			coordsList.add(x);
			coordsList.add(y);
			coordsList.add(z);
		}
		
		//ָ������ָ��
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2ByteBuffer(coordsList));
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, coordsList.size() / 3);
	}
}

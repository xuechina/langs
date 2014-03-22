package com.xuek.shape;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import com.xuek.shape.util.BufferUtil;

/**
 * �����δ�,������
 */
public class MyTriangleRenderer extends AbstractMyRenderer{

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
		float[] coords = {
			-r,r,0,
			-r,-r,0,
			r,r,0,
			r,-r,0,
		};
		//ָ������ָ��
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2ByteBuffer(coords));
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}

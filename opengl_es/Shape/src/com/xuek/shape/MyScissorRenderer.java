package com.xuek.shape;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.xuek.shape.util.BufferUtil;

import android.opengl.GLU;

/**
 * ������
 */
public class MyScissorRenderer extends AbstractMyRenderer{

	private int width ;
	private int height ;
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//��ƽɫ
		gl.glClearColor(0f, 0f, 0f, 1f);
		//���ö��㻺��������
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.width = width ;
		this.height = height ;
		
		gl.glViewport(0, 0, width, height);
		ratio = (float)width / (float)height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 3f, 7f);
	}
	
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glColor4f(1f, 0f, 0f, 1f);
		
		//����ģ����ͼ����
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//��������Ĳ���
		GLU.gluLookAt(gl,0f,0f,5f, 0f, 0f, 0f, 0f,1f,0f);
		
		//���ü���
		gl.glEnable(GL10.GL_SCISSOR_TEST);
		
		//��ת�Ƕ�
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);
		
		float[] coords = {
			-ratio,1f,2f ,
			-ratio,-1f,2f ,
			ratio,1f,2f ,
			ratio,-1f,2f 
		};
		
		//��ɫ����
		float[][] colors = {
				{1f,0f,0f,1f},
				{0f,1f,0f,1f},
				{0f,0f,1f,1f},
				{1f,1f,0f,1f},
				{0f,1f,1f,1f},
				{1f,0f,1f,1f},
		};
		
		int step = 20 ;
		for(int i = 0 ; i < 6 ; i ++){
			//���ü�����
			gl.glScissor(i * 20, i * 20, width - (i * 20 * 2), height - (i * 20 * 2));
			//������ɫ
			gl.glColor4f(colors[i][0],colors[i][1],colors[i][2],colors[i][3]);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2ByteBuffer(coords));
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		}
	}
}

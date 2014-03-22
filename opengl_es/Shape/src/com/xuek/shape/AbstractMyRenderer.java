package com.xuek.shape;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ����Ⱦ��,����������
 */
public abstract class AbstractMyRenderer implements android.opengl.GLSurfaceView.Renderer{
	
	public float ratio;
	//
	public float xrotate = 0f;//Χ��x����ת�Ƕ�
	public float yrotate = 0f;//Χ��x����ת�Ƕ�
	

	/**
	 * 1.
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//��ƽɫ
		gl.glClearColor(0f, 0f, 0f, 1f);
		//���ö��㻺��������
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	}

	/**
	 * 2.
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//�����ӿ�
		gl.glViewport(0, 0, width, height);
		ratio = (float)width / (float)height;
		//ͶӰ����
		gl.glMatrixMode(GL10.GL_PROJECTION);
		//���ص�λ����
		gl.glLoadIdentity();
		//����ƽ��ͷ��
		gl.glFrustumf(-ratio, ratio, -1, 1, 3f, 7f);
	}

	/**
	 * 3.
	 */
	public abstract void onDrawFrame(GL10 gl);
}

package com.xuek.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

/**
 * ����,������
 */
//�Զ�����Ⱦ��
class MyRenderer implements android.opengl.GLSurfaceView.Renderer{
	private float ratio;

	//��㴴��ʱ
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//��������ɫ
		gl.glClearColor(0, 0, 0, 1);
		//���ö��㻺����.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	}

	//���sizeʱ
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//�����ӿ�,������������
		gl.glViewport(0, 0, width, height);
		
		ratio = (float)width / (float)height;
		
		//����ģʽ,ͶӰ����,openGL����״̬��
		gl.glMatrixMode(GL10.GL_PROJECTION);
		//���ص�λ����
		gl.glLoadIdentity();
		//ƽ��ͷ��
		gl.glFrustumf(-1f, 1f, -ratio, ratio, 3, 7);
	}

	//��ͼ
	public void onDrawFrame(GL10 gl) {
		//eyex,eyey,eyez:�������������
		//centerx,centery,centerz:����Ĺ۲��.
		//upx,upx,upx:ָ���������ϵ�����
		
		//�����ɫ������
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//ģ����ͼ����
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();//���ص�λ����
		GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);
		
		//��������
		//��������
		//����������
		float[] coords = {
			0f,ratio,2f,
			-1f,-ratio,2f,
			1f,-ratio,2f
		};
		
		//�����ֽڻ������ռ�,��Ŷ�����������
		ByteBuffer ibb = ByteBuffer.allocateDirect(coords.length * 4);
		//���õ�˳��(����˳��)
		ibb.order(ByteOrder.nativeOrder());
		//���ö�����������
		FloatBuffer fbb = ibb.asFloatBuffer();
		fbb.put(coords);
		//��λָ���λ��,�Ӹ�λ�ÿ�ʼ��ȡ��������
		ibb.position(0);
		
		//���û�ͼ��ɫ,��ɫ
		gl.glColor4f(1f, 0f, 0f, 1f);
		
		//3:3ά��,ʹ����������ֵ��ʾһ����
		//type:ÿ������������� 
		//stride:0,���.
		//ibb:ָ�����㻺����
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, ibb);
		//����������
		//0:��ʼ��:
		//3:���Ƶ������
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	}
}

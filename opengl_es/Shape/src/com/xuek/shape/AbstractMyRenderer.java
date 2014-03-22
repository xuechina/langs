package com.xuek.shape;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 点渲染器,绘制螺旋线
 */
public abstract class AbstractMyRenderer implements android.opengl.GLSurfaceView.Renderer{
	
	public float ratio;
	//
	public float xrotate = 0f;//围绕x轴旋转角度
	public float yrotate = 0f;//围绕x轴旋转角度
	

	/**
	 * 1.
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//清平色
		gl.glClearColor(0f, 0f, 0f, 1f);
		//启用顶点缓冲区数组
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	}

	/**
	 * 2.
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//设置视口
		gl.glViewport(0, 0, width, height);
		ratio = (float)width / (float)height;
		//投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		//加载单位矩阵
		gl.glLoadIdentity();
		//设置平截头体
		gl.glFrustumf(-ratio, ratio, -1, 1, 3f, 7f);
	}

	/**
	 * 3.
	 */
	public abstract void onDrawFrame(GL10 gl);
}

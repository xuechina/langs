package com.xuek.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

/**
 * 入门,三角形
 */
//自定义渲染器
class MyRenderer implements android.opengl.GLSurfaceView.Renderer{
	private float ratio;

	//表层创建时
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//设置清屏色
		gl.glClearColor(0, 0, 0, 1);
		//启用顶点缓冲区.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	}

	//表层size时
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//设置视口,输出画面的区域
		gl.glViewport(0, 0, width, height);
		
		ratio = (float)width / (float)height;
		
		//矩阵模式,投影矩阵,openGL基于状态机
		gl.glMatrixMode(GL10.GL_PROJECTION);
		//加载单位矩阵
		gl.glLoadIdentity();
		//平截头体
		gl.glFrustumf(-1f, 1f, -ratio, ratio, 3, 7);
	}

	//绘图
	public void onDrawFrame(GL10 gl) {
		//eyex,eyey,eyez:放置眼球的坐标
		//centerx,centery,centerz:眼球的观察点.
		//upx,upx,upx:指定眼球向上的向量
		
		//清除颜色缓存区
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//模型视图矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();//加载单位矩阵
		GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);
		
		//画三角形
		//绘制数组
		//三角形座标
		float[] coords = {
			0f,ratio,2f,
			-1f,-ratio,2f,
			1f,-ratio,2f
		};
		
		//分配字节缓存区空间,存放顶点坐标数据
		ByteBuffer ibb = ByteBuffer.allocateDirect(coords.length * 4);
		//设置的顺序(本地顺序)
		ibb.order(ByteOrder.nativeOrder());
		//放置顶点坐标数组
		FloatBuffer fbb = ibb.asFloatBuffer();
		fbb.put(coords);
		//定位指针的位置,从该位置开始读取顶点数据
		ibb.position(0);
		
		//设置绘图颜色,红色
		gl.glColor4f(1f, 0f, 0f, 1f);
		
		//3:3维点,使用三个坐标值表示一个点
		//type:每个点的数据类型 
		//stride:0,跨度.
		//ibb:指定顶点缓冲区
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, ibb);
		//绘制三角形
		//0:起始点:
		//3:绘制点的数量
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	}
}

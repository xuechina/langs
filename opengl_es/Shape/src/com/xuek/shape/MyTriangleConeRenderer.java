package com.xuek.shape;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import com.xuek.shape.util.BufferUtil;

/**
 * 棱锥,正方形
 */
public class MyTriangleConeRenderer extends AbstractMyRenderer{

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0f, 0f, 0f, 1f);
		//顶点缓冲区
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//启用眼色缓冲区
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	}
	
	public void onDrawFrame(GL10 gl) {
		//清除颜色缓冲区和深度缓冲区
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
		//设置绘图颜色
		gl.glColor4f(1f, 0f, 0f, 1f);
		
		//启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
		//启用表面剔除
		gl.glEnable(GL10.GL_CULL_FACE);
		//指定前面()
		//ccw:counter clock wise-->逆时针
		//cw:clock wise--> 顺时针
		gl.glFrontFace(GL10.GL_CCW);
		//剔除背面
		gl.glCullFace(GL10.GL_BACK);
		
		//GL10.GL_SMOOTH:平滑着色(默认)
		//GL10.GL_FLAT:单调模式
		gl.glShadeModel(GL10.GL_FLAT);
		
		//操作模型视图矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//设置眼球的参数
		GLU.gluLookAt(gl,0f,0f,5f, 0f, 0f, 0f, 0f,1f,0f);
		
		//旋转角度
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);
		
		//计算点坐标
		float r = 0.5f ;//半径
		float x = 0f,y = 0f,z = -0.5f ;
		
		/******************** 锥面 ************************/
		//顶点坐标集合
		List<Float> coordsList = new ArrayList<Float>();
		//添加锥顶点
		coordsList.add(0f);
		coordsList.add(0f);
		coordsList.add(0.5f);
		
		//顶点颜色集合
		List<Float> colorList = new ArrayList<Float>();
		colorList.add(1f);//r
		colorList.add(0f);//g
		colorList.add(0f);//b
		colorList.add(1f);//a
		
		/******************** 锥底 ************************/
		//锥底坐标
		List<Float> coordsConeBottomList = new ArrayList<Float>();
		coordsConeBottomList.add(0f);
		coordsConeBottomList.add(0f);
		coordsConeBottomList.add(-0.5f);
		
		boolean flag = false ;
		//底面
		for(float alpha = 0f ; alpha < Math.PI * 6 ; alpha = (float) (alpha + Math.PI / 8)){
			//锥面
			x = (float) (r * Math.cos(alpha));
			y = (float) (r * Math.sin(alpha));
			coordsList.add(x);
			coordsList.add(y);
			coordsList.add(z);
			
			//锥底坐标
			coordsConeBottomList.add(x);
			coordsConeBottomList.add(y);
			coordsConeBottomList.add(z);
			
			//点颜色值
			if(flag = !flag){
				//黄色
				colorList.add(1f);
				colorList.add(1f);
				colorList.add(0f);
				colorList.add(1f);
			}
			else{
				//红色
				colorList.add(1f);
				colorList.add(0f);
				colorList.add(0f);
				colorList.add(1f);
			}
		}
		//点颜色值
		if(flag = !flag){
			//黄色
			colorList.add(1f);
			colorList.add(1f);
			colorList.add(0f);
			colorList.add(1f);
		}
		else{
			//红色
			colorList.add(1f);
			colorList.add(0f);
			colorList.add(0f);
			colorList.add(1f);
		}
		
		//颜色缓冲区
		ByteBuffer colorBuffer = BufferUtil.list2ByteBuffer(colorList);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		//绘制锥面
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2ByteBuffer(coordsList));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, coordsList.size() / 3);
		
		//剔除正面
		gl.glCullFace(GL10.GL_FRONT);
		//绘制锥底
		colorBuffer.position(4);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2ByteBuffer(coordsConeBottomList));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, coordsConeBottomList.size() / 3);
		
		
	}
}

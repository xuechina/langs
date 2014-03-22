package com.xuek.shape;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import com.xuek.shape.util.BufferUtil;

/**
 * ��׶,������
 */
public class MyTriangleConeRenderer extends AbstractMyRenderer{

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0f, 0f, 0f, 1f);
		//���㻺����
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//������ɫ������
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	}
	
	public void onDrawFrame(GL10 gl) {
		//�����ɫ����������Ȼ�����
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
		//���û�ͼ��ɫ
		gl.glColor4f(1f, 0f, 0f, 1f);
		
		//������Ȳ���
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
		//���ñ����޳�
		gl.glEnable(GL10.GL_CULL_FACE);
		//ָ��ǰ��()
		//ccw:counter clock wise-->��ʱ��
		//cw:clock wise--> ˳ʱ��
		gl.glFrontFace(GL10.GL_CCW);
		//�޳�����
		gl.glCullFace(GL10.GL_BACK);
		
		//GL10.GL_SMOOTH:ƽ����ɫ(Ĭ��)
		//GL10.GL_FLAT:����ģʽ
		gl.glShadeModel(GL10.GL_FLAT);
		
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
		float x = 0f,y = 0f,z = -0.5f ;
		
		/******************** ׶�� ************************/
		//�������꼯��
		List<Float> coordsList = new ArrayList<Float>();
		//���׶����
		coordsList.add(0f);
		coordsList.add(0f);
		coordsList.add(0.5f);
		
		//������ɫ����
		List<Float> colorList = new ArrayList<Float>();
		colorList.add(1f);//r
		colorList.add(0f);//g
		colorList.add(0f);//b
		colorList.add(1f);//a
		
		/******************** ׶�� ************************/
		//׶������
		List<Float> coordsConeBottomList = new ArrayList<Float>();
		coordsConeBottomList.add(0f);
		coordsConeBottomList.add(0f);
		coordsConeBottomList.add(-0.5f);
		
		boolean flag = false ;
		//����
		for(float alpha = 0f ; alpha < Math.PI * 6 ; alpha = (float) (alpha + Math.PI / 8)){
			//׶��
			x = (float) (r * Math.cos(alpha));
			y = (float) (r * Math.sin(alpha));
			coordsList.add(x);
			coordsList.add(y);
			coordsList.add(z);
			
			//׶������
			coordsConeBottomList.add(x);
			coordsConeBottomList.add(y);
			coordsConeBottomList.add(z);
			
			//����ɫֵ
			if(flag = !flag){
				//��ɫ
				colorList.add(1f);
				colorList.add(1f);
				colorList.add(0f);
				colorList.add(1f);
			}
			else{
				//��ɫ
				colorList.add(1f);
				colorList.add(0f);
				colorList.add(0f);
				colorList.add(1f);
			}
		}
		//����ɫֵ
		if(flag = !flag){
			//��ɫ
			colorList.add(1f);
			colorList.add(1f);
			colorList.add(0f);
			colorList.add(1f);
		}
		else{
			//��ɫ
			colorList.add(1f);
			colorList.add(0f);
			colorList.add(0f);
			colorList.add(1f);
		}
		
		//��ɫ������
		ByteBuffer colorBuffer = BufferUtil.list2ByteBuffer(colorList);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		//����׶��
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2ByteBuffer(coordsList));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, coordsList.size() / 3);
		
		//�޳�����
		gl.glCullFace(GL10.GL_FRONT);
		//����׶��
		colorBuffer.position(4);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2ByteBuffer(coordsConeBottomList));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, coordsConeBottomList.size() / 3);
		
		
	}
}

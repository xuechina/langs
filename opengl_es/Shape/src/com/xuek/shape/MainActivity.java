package com.xuek.shape;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class MainActivity extends Activity {

	//
    private AbstractMyRenderer render;
	private MyGLSurfaceView view;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MyGLSurfaceView(this);
        render = new MyScissorRenderer();
        view.setRenderer(render);
        //GLSurfaceView.RENDERMODE_CONTINUOUSLY:≥÷–¯‰÷»æ(ƒ¨»œ)
        //GLSurfaceView.RENDERMODE_WHEN_DIRTY:‘‡‰÷»æ,√¸¡Ó‰÷»æ
        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(view);
    }
    
    class MyGLSurfaceView extends GLSurfaceView{
		public MyGLSurfaceView(Context context) {
			super(context);
		}
		public MyGLSurfaceView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	float step = 5f ;
    	//up
    	if(keyCode == KeyEvent.KEYCODE_DPAD_UP){
    		render.xrotate = render.xrotate - step ;
    	}
    	else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
    		render.xrotate = render.xrotate + step ;
    	}
    	else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
    		render.yrotate = render.yrotate + step ;
    	}
    	else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
    		render.yrotate = render.yrotate - step ;
    	}
    	//«Î«Û‰÷»æ,∫Õ‘‡‰÷»æ≈‰∫œ π”√
    	view.requestRender();
    	return super.onKeyDown(keyCode, event);
    }
}

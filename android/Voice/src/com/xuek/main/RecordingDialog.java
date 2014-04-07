package com.xuek.main;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.xuek.voice.R;



public class RecordingDialog extends Dialog {
	private ImageView ivAnimation;
	private AnimationDrawable draw;
	
	public RecordingDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation_dialog);

		initView();
	}


	public void initView() {
		ivAnimation = (ImageView) findViewById(R.id.ivAnimation);
		ivAnimation.setBackgroundResource(R.anim.animation_list);
		draw = (AnimationDrawable) ivAnimation.getBackground();

		if (draw.isRunning()) {
			draw.stop();
		} else {
			draw.start();
		}
	}
	
	
   
	
}

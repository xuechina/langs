package com.jerry.anim;


import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author mint
 * 反转视图View
 */
public class ReverseViewActivity extends Activity implements OnClickListener{

	private Animator mStartAnim, mEndAnim;
	private View mLoginView, mRegisterView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reverse_view);
		
		init();
	}
	private void init() {
		mLoginView = findViewById(R.id.view_login);
		mRegisterView = findViewById(R.id.view_register);
		mStartAnim = AnimatorInflater.loadAnimator(ReverseViewActivity.this, R.animator.rotate_start);
		mEndAnim = AnimatorInflater.loadAnimator(ReverseViewActivity.this, R.animator.rotate_end);
		findViewById(R.id.login).setOnClickListener(this);
		findViewById(R.id.register).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.login ) {
			loadAnim(mLoginView, mRegisterView);
		}else if(v.getId() == R.id.register){
			loadAnim(mRegisterView, mLoginView);
		}
	}
	
	private void loadAnim(final View startView, final View endView){
		mStartAnim.setTarget(startView);
		mStartAnim.start();
		mStartAnim.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				startView.setVisibility(View.GONE);
				endView.setVisibility(View.VISIBLE);
				mEndAnim.setTarget(endView);
				mEndAnim.start();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}

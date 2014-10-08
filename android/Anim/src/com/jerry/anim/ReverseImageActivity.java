package com.jerry.anim;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * @author mint
 * 反转图片Image
 * 参考链接：http://livehappy.iteye.com/blog/1004399
 */
public class ReverseImageActivity extends Activity {
	private ImageView imgView;
	private boolean bool = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reverse_image);
        imgView = (ImageView) findViewById(R.id.imgView);
        imgView.setOnClickListener(new ImgViewListener());
    }
    class ImgViewListener implements OnClickListener {
    	@Override
    	public void onClick(View v) {
    		Animation animation = AnimationUtils.loadAnimation(ReverseImageActivity.this, R.anim.back_scale);
    		animation.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}
				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				@Override
				public void onAnimationEnd(Animation animation) {
					if(bool){
						imgView.setImageResource(R.drawable.back);
						bool = false;
					}else {
						imgView.setImageResource(R.drawable.front);
						bool = true;
					}
					imgView.startAnimation(AnimationUtils.loadAnimation(ReverseImageActivity.this, R.anim.front_scale));
				}
			});
    		imgView.startAnimation(animation);
    	}
    }
}

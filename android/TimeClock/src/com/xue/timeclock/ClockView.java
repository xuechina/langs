package com.xue.timeclock;


import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.TranslateAnimation;

public class ClockView extends SurfaceView implements SurfaceHolder.Callback{

	private SurfaceHolder mHolder;
	private Paint mPaint;
	private int mHeight, mWidth;
	private float marginLeft, marginRight;
	/**
	 * 时间的X,Y坐标
	 */
	private float mXCoord, mYCoord;
	/**
	 * 小时，分钟
	 */
	private int mHour, mMin;
	private float mMargin;
	private Resources mResource;
	private Canvas mCanvas;
	private TranslateAnimation mTranslateAnimation;
	/**
	 * 动画的X，Y坐标
	 */
	private float animXCoord, animYCoord;
	
	/**
	 * 取一个比View高度小的值，用于分配24个小时。若用View的高度，会导致在View的最底部位置（手指无法触及），才会出现24:00
	 */
	private float mTimeHeight;
	
	/**
	 * 触摸事件发生时的X，Y坐标
	 */
	private float mEventX, mEventY;
	/**
	 * 时间
	 */
	private String mTimeText;
	
	private Bitmap mBackground;
	public ClockView(Context context) {
		super(context);
		mResource = context.getResources();
		marginLeft = mResource.getDimension(R.dimen.clock_margin_left);
		marginRight = mResource.getDimension(R.dimen.clock_margin_right);
		mMargin = mResource.getDimension(R.dimen.time_text_big);
		setFocusable(true);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setAntiAlias(true);
//		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
//				(int) (LayoutParams.MATCH_PARENT - resource.getDimension(R.dimen.time_low))));
		
		mColorMatrix = new ColorMatrix(); 
		mBackground = BitmapFactory.decodeResource(mResource, R.drawable.ic_launcher);
	}

	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			mCanvas = mHolder.lockCanvas();
			mCanvas.drawColor(Color.BLACK);
			mEventX = event.getX();
			mEventY = event.getY();
			
			mColorMatrix.setRotate(0, mEventY); 
			mColorFilter = new ColorMatrixColorFilter(mColorMatrix); 
			mPaint.setColorFilter(mColorFilter);
			mCanvas.drawBitmap(mBackground, src, dst, mPaint);
			
			int temp = (int) ((mEventY - mMargin)/ mTimeHeight * (24 * 60));
			if (temp < 0) {
				mHour = 0;
				mMin = 0;
			}else{
				mHour = temp / 60;
				mMin = temp % 60 / 15 * 15;
				if (mMin >= 45) {
					mMin = 30;
				}
				if (mHour >= 24 ) {
					mHour = 24;
					mMin = 0;
				}
				
			}
			mTimeText = mHour + ":" + String.format("%02d", mMin);
			mYCoord = mEventY;
			if (mEventY < mMargin) {
				mYCoord = mMargin;
			}else if(mEventY > mHeight - mMargin){
				mYCoord = mHeight - mMargin;
			}
			mTranslateAnimation = new TranslateAnimation(animXCoord, mEventX, animYCoord, mEventY);
			mTranslateAnimation.setDuration(1000);
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				mPaint.setStrokeWidth(0.1f);
				mPaint.setTextSize(mResource.getDimension(R.dimen.time_text_big));
//				mCanvas.drawText(text, width/2 - resource.getDimension(R.dimen.clock_center_xmargin), event.getY(), mPaint);
				mCanvas.drawText(mTimeText, mWidth/2, mYCoord, mPaint);
//				setTextLocation(text);
//				mCanvas.drawText(text, textCenterX, event.getY() - textBaselineY, mPaint);  
				break;
			case MotionEvent.ACTION_MOVE:
				mPaint.setStrokeWidth(0.05f);
				mPaint.setTextSize(mResource.getDimension(R.dimen.time_text_small));
				if (mWidth / 2 > mEventX) 
					mXCoord = mWidth - marginRight;
				else
					mXCoord = marginLeft;
				mCanvas.drawText(mTimeText, mXCoord, mYCoord, mPaint);
				
				break;
			default:
				break;
			}
			
			startAnimation(mTranslateAnimation);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mHolder.unlockCanvasAndPost(mCanvas);
		}
		animXCoord = mXCoord;
		animYCoord = mEventY;
		return super.onTouchEvent(event);
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mHeight = getHeight();
		mWidth = getWidth();
		animXCoord = mWidth / 2;
		animYCoord = mHeight / 2;
		mTimeHeight = mHeight - mResource.getDimension(R.dimen.time_text_big)
				* 2;
		animXCoord = mWidth / 2;
		animYCoord = mHeight / 2;

		try {
			mCanvas = mHolder.lockCanvas();
			mCanvas.drawColor(Color.BLACK);
			src = new Rect(0, 0, mBackground.getWidth(), mBackground.getHeight());
			dst = new Rect(0, 0, mWidth, mHeight);
			mCanvas.drawBitmap(mBackground, src, dst, null);
			
			SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm");
			String date = sDateFormat.format(new java.util.Date());
			mPaint.setStrokeWidth(0.1f);
			mPaint.setTextSize(mResource.getDimension(R.dimen.time_text_big));
			mCanvas.drawText(date, mWidth / 2, mHeight / 2, mPaint);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mHolder.unlockCanvasAndPost(mCanvas);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	/** 文本中轴线X坐标 */  
    private float textCenterX;  
    /** 文本baseline线Y坐标 */  
    private float textBaselineY;
	private Rect src;
	private Rect dst;
	private ColorMatrix mColorMatrix;
	private ColorMatrixColorFilter mColorFilter;  
	/** 
     * 定位文本绘制的位置 
     */  
    private void setTextLocation(String text) {  
    	FontMetrics fm = mPaint.getFontMetrics();  
        //文本的宽度  
        float textWidth = mPaint.measureText(text);  
//        float textCenterVerticalBaselineY = height / 2 - fm.descent + (fm.descent - fm.ascent) / 2;  
        float textCenterVerticalBaselineY = fm.descent + (fm.descent - fm.ascent) / 2;  
        textCenterX = (float)mWidth / 2 - textWidth / 2;  
        textBaselineY = textCenterVerticalBaselineY;  
    }  
}

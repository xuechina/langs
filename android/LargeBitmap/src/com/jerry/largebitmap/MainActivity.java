package com.jerry.largebitmap;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.opengl.GLES10;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * @author jerry
 * xuechinahb@gmail.com
 * Jun 20, 2014
 */
public class MainActivity extends Activity {

	private ImageView imageView;
	
	private int mDisplayWidth;
	@SuppressWarnings("unused")
	private int mDisplayHeight;
	
	private int mMaxTextureSize;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		imageView = (ImageView) findViewById(R.id.image);
		imageView.setOnTouchListener(new TouchListener());
		
		System.out.println("getMaxTextureSize() = " + getMaxTextureSize());
		mMaxTextureSize = getMaxTextureSize();
		System.out.println("mMaxTextureSize = " + mMaxTextureSize );
		getDisplayWH();
//		load();
		load2();
//		load3();
	}
	
	/**
	 * get display width and height
	 */
	void getDisplayWH(){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		mDisplayHeight = displaymetrics.heightPixels;
		mDisplayWidth = displaymetrics.widthPixels;
		
	}
	
	/**
	 * get max texture size; 
	 * Notice: don't call in the first Activity, otherwise it always return 0. as you can see, I add {@linkplain HomeActivity} which is the entry of app. 
	 * @return
	 */
	int getMaxTextureSize(){  
		 int[] maxTextureSize = new int[1];  
	        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE,maxTextureSize, 0);  
	        return maxTextureSize[0];  
    } 
	
	/**
	 * load bitmap from resouce.
	 * the better way is to use synchronized thread.
	 * Notice: Maybe throws exception， “Caused by: java.lang.IllegalArgumentException: height must be > 0”。Because mMaxTextureSize is 0.
	 */
	@SuppressWarnings("unused")
	private void load() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
		
		System.out.println( "bitmap.getWidth() = " + bitmap.getWidth() + "--bitmap.getHeight() =  " + bitmap.getHeight());
		imageView.setScaleType(ScaleType.MATRIX);  
		Matrix matrix = new Matrix(); 
		float displayWidth = mDisplayWidth;
		matrix.postScale(displayWidth/bitmap.getWidth(), 1);
		Bitmap b = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), mMaxTextureSize , matrix, true);
		imageView.setImageBitmap(b);
	}

	private void load2() {
		try {
			Drawable drawable = createLargeDrawable(R.raw.test);
			imageView.setImageDrawable(drawable);
			imageView.setScaleType(ScaleType.MATRIX);
			Matrix matrix = new Matrix();
			float displayWidth = mDisplayWidth;
			matrix.postScale(displayWidth / drawable.getMinimumWidth() , 1);
			imageView.setImageMatrix(matrix);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void load3(){
		try {
			Drawable drawable = createLargeDrawable(R.raw.test);
			System.out.println("drawable.getMinimumWidth() = " + drawable.getMinimumWidth() + "--drawable.getMinimumHeight() = " + drawable.getMinimumHeight());
			Bitmap bitmap = Bitmap.createBitmap(drawable.getMinimumWidth(), drawable.getMinimumHeight(), Bitmap.Config.ARGB_8888);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			drawable.draw(new Canvas(bitmap));
			
			imageView.setScaleType(ScaleType.MATRIX);  
//			Matrix matrix = new Matrix(); 
//			float displayWidth = mDisplayWidth;
//			float maxTextureSize = mMaxTextureSize;
//			matrix.postScale(displayWidth/bitmap.getWidth(), maxTextureSize / drawable.getMinimumHeight());
//			matrix.postScale(displayWidth/bitmap.getWidth(), 1);
//			Bitmap b = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), mMaxTextureSize , matrix, false);
			Bitmap b =  Bitmap.createScaledBitmap(bitmap, mDisplayWidth, mMaxTextureSize, false);
			
			imageView.setImageBitmap(b);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * http://stackoverflow.com/questions/15655713/dealing-with-androids-texture-size-limit
	 * @param resId
	 * @return
	 * @throws IOException
	 */
	@SuppressLint("NewApi")
	private Drawable createLargeDrawable(int resId) throws IOException {

	    InputStream is = getResources().openRawResource(resId);
	    BitmapRegionDecoder brd = BitmapRegionDecoder.newInstance(is, true);

	    try {
	        if (brd.getWidth() <= mMaxTextureSize && brd.getHeight() <= mMaxTextureSize) {
	            return new BitmapDrawable(getResources(), is);
	        }

	        int rowCount = (int) Math.ceil((float) brd.getHeight() / (float) mMaxTextureSize);
	        int colCount = (int) Math.ceil((float) brd.getWidth() / (float) mMaxTextureSize);

	        BitmapDrawable[] drawables = new BitmapDrawable[rowCount * colCount];

	        for (int i = 0; i < rowCount; i++) {

	            int top = mMaxTextureSize * i;
	            int bottom = i == rowCount - 1 ? brd.getHeight() : top + mMaxTextureSize;

	            for (int j = 0; j < colCount; j++) {

	                int left = mMaxTextureSize * j;
	                int right = j == colCount - 1 ? brd.getWidth() : left + mMaxTextureSize;

	                Bitmap b = brd.decodeRegion(new Rect(left, top, right, bottom), null);
	                BitmapDrawable bd = new BitmapDrawable(getResources(), b);
	                bd.setGravity(Gravity.TOP | Gravity.LEFT);
	                drawables[i * colCount + j] = bd;
	            }
	        }

	        LayerDrawable ld = new LayerDrawable(drawables);
	        for (int i = 0; i < rowCount; i++) {
	            for (int j = 0; j < colCount; j++) {
	                ld.setLayerInset(i * colCount + j, mMaxTextureSize * j, mMaxTextureSize * i, 0, 0);
	            }
	        }

	        return ld;
	    }
	    finally {
	        brd.recycle();
	    }
	}
	class TouchListener implements OnTouchListener {
		
		/** 记录是拖拉照片模式还是放大缩小照片模式 */
		private int mode = 0;// 初始状态  
		/** 拖拉照片模式 */
		private static final int MODE_DRAG = 1;
		/** 放大缩小照片模式 */
		private static final int MODE_ZOOM = 2;
		
		/** 用于记录开始时候的坐标位置 */
		private PointF startPoint = new PointF();
		/** 用于记录拖拉图片移动的坐标位置 */
		private Matrix matrix = new Matrix();
		/** 用于记录图片要进行拖拉时候的坐标位置 */
		private Matrix currentMatrix = new Matrix();
	
		/** 两个手指的开始距离 */
		private float startDis;
		/** 两个手指的中间点 */
		private PointF midPoint;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			/** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			// 手指压下屏幕
			case MotionEvent.ACTION_DOWN:
				mode = MODE_DRAG;
				// 记录ImageView当前的移动位置
				currentMatrix.set(imageView.getImageMatrix());
				startPoint.set(event.getX(), event.getY());
				break;
			// 手指在屏幕上移动，改事件会被不断触发
			case MotionEvent.ACTION_MOVE:
				// 拖拉图片
				if (mode == MODE_DRAG) {
					float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
					float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
					// 在没有移动之前的位置上进行移动
					matrix.set(currentMatrix);
					matrix.postTranslate(dx, dy);
				}
				// 放大缩小图片
				else if (mode == MODE_ZOOM) {
					float endDis = distance(event);// 结束距离
					if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
						float scale = endDis / startDis;// 得到缩放倍数
						matrix.set(currentMatrix);
						matrix.postScale(scale, scale,midPoint.x,midPoint.y);
					}
				}
				break;
			// 手指离开屏幕
			case MotionEvent.ACTION_UP:
				// 当触点离开屏幕，但是屏幕上还有触点(手指)
			case MotionEvent.ACTION_POINTER_UP:
				mode = 0;
				break;
			// 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
			case MotionEvent.ACTION_POINTER_DOWN:
				mode = MODE_ZOOM;
				/** 计算两个手指间的距离 */
				startDis = distance(event);
				/** 计算两个手指间的中间点 */
				if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
					midPoint = mid(event);
					//记录当前ImageView的缩放倍数
					currentMatrix.set(imageView.getImageMatrix());
				}
				break;
			}
			imageView.setImageMatrix(matrix);
			return true;
		}

		/** 计算两个手指间的距离 */
		@SuppressLint("FloatMath")
		private float distance(MotionEvent event) {
			float dx = event.getX(1) - event.getX(0);
			float dy = event.getY(1) - event.getY(0);
			/** 使用勾股定理返回两点之间的距离 */
			return FloatMath.sqrt(dx * dx + dy * dy);
		}

		/** 计算两个手指间的中间点 */
		private PointF mid(MotionEvent event) {
			float midX = (event.getX(1) + event.getX(0)) / 2;
			float midY = (event.getY(1) + event.getY(0)) / 2;
			return new PointF(midX, midY);
		}

	}
	
	
}

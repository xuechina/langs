package com.xue.timeclock;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;

public class MainActivity extends Activity {

	private ClockView mClockView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mClockView = new ClockView(this);
		setContentView(mClockView);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mClockView.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}

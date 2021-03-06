package com.speedata.kt40helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import com.speedata.kt40helper_.R;

public class DisplayTest extends Activity{

	boolean finished;
	private Message msg;
	Thread mThread = new  Thread();
	LcdDrawView mView;
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 1){
				LcdDrawView.flag += 1;
				if(LcdDrawView.flag > 6){
					LcdDrawView.flag = 0;
					finished = false;
					checkDialog();
				}
			}
			mView.invalidate();
			return;
		}
	};
	
	private void checkDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
			.setTitle(R.string.display)
			.setMessage(R.string.lcdtesttip)
			.setNegativeButton(R.string.successed, new OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					DisplayTest.this.setResult(1);
					DisplayTest.this.finish();
				}
			})
			.setPositiveButton(R.string.failed, new OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					DisplayTest.this.setResult(-1);
					DisplayTest.this.finish();
				}
			})
			;
		builder.show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mView = new LcdDrawView(this);
		setContentView(mView);
		msg = mHandler.obtainMessage(1);
		mHandler.sendMessage(msg);
		finished = true;
	}

	@Override
	protected void onPause() {
		LcdDrawView.flag = 0;
		finished = false;
		super.onPause();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
        break;

        case MotionEvent.ACTION_UP:
        	msg = mHandler.obtainMessage(1);
			mHandler.sendMessage(msg);
        break;
        }
		return super.onTouchEvent(event);
	}
	
}
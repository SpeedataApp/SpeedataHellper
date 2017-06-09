package com.speedata.kt40helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.speedata.kt40helper_.R;

public class UsbTest extends MyActivity implements OnClickListener {
	private TextView mTextView;
	private TextView tvBack;
	private TextView tvTitle;
	private final static String ACTION = "android.hardware.usb.action.USB_STATE";
	BroadcastReceiver usBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION)) {
				boolean connected = intent.getExtras().getBoolean("connected");
				if (connected) {
					mTextView.setText(getResources().getString(
							R.string.usb_state_connect));
				} else {
					mTextView.setText(getResources().getString(
							R.string.usb_state_no_connect));
				}
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.usb_test);
		mTextView = (TextView) findViewById(R.id.usbtext);
		tvBack = getBackTextView();
		tvTitle = getBackTitle();
		tvTitle.setText(getResources().getString(R.string.action_nornal_charge));
		tvBack.setOnClickListener(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION);
		registerReceiver(usBroadcastReceiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void onClick(View v) {
		if (v == tvBack) {
			finish();
		}
	}

}

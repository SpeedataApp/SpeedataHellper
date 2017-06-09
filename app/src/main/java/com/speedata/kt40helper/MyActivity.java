package com.speedata.kt40helper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.speedata.kt40helper_.R;

public class MyActivity extends Activity {

	TextView tvBack;
	TextView tvTitle;
	TextView tvRight;
	ImageView imgClick;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setContentView(R.layout.title);
		super.onCreate(savedInstanceState);
	}

	// public void setview(int id){
	// setContentView(id);
	// }
	public TextView getBackTextView() {
		tvBack = (TextView) findViewById(R.id.tv_back);
		return tvBack;
	}

	public TextView getBackTitle() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		return tvTitle;
	}

	public ImageView getImgClick() {
		imgClick = (ImageView) findViewById(R.id.imv_click_me);
		return imgClick;
	}

	public TextView getRight() {
		tvRight = (TextView) findViewById(R.id.tv_select_pic);
		return tvRight;
	}
}

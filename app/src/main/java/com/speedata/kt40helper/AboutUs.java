package com.speedata.kt40helper;

import org.xml.sax.XMLReader;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.speedata.kt40helper_.R;

public class AboutUs extends MyActivity implements OnClickListener,
		Html.ImageGetter, Html.TagHandler {
	private TextView tvBack;
	private TextView tvTitle;
	private TextView tvContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		tvBack = getBackTextView();
		tvTitle = getBackTitle();
		tvContent = (TextView) findViewById(R.id.tv_about_us);
		tvTitle.setText(getResources().getString(R.string.about_title));
		tvBack.setOnClickListener(this);
		tvContent.setText(Html.fromHtml(
				getResources().getString(R.string.about_speedata), this, this));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == tvBack) {
			finish();
		}
	}

	@Override
	public void handleTag(boolean opening, String tag, Editable output,
			XMLReader xmlReader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Drawable getDrawable(String source) {
		// TODO Auto-generated method stub
		return null;
	}
}

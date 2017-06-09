package com.speedata.kt40helper;

import com.speedata.kt40helper_.R;
import com.speedata.utils.Contancts;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 开屏页
 * 
 */
public class SplashActivity extends MyActivity {
	private RelativeLayout rootLayout;
	private TextView versionText;
	private TextView tvtitle;

	private static final int sleepTime = 3000;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_splash);

		rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
		versionText = (TextView) findViewById(R.id.tv_version);
		tvtitle = (TextView) findViewById(R.id.title);
//		tvtitle.setText(getResources().getString(R.string.home_title)
//				+ Contancts.VERSION);
		 versionText.setText(getResources().getString(R.string.home_title)+getVersion());
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1500);
		rootLayout.startAnimation(animation);
	}

	@Override
	protected void onStart() {
		super.onStart();

		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
				}
				startActivity(new Intent(SplashActivity.this,
						HomeActivity.class));
				finish();
			}
		}).start();

	}

	/**
	 * 获取当前应用程序的版本号
	 */
	private String getVersion() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
			String version = packinfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "版本号错误";
		}
	}
}

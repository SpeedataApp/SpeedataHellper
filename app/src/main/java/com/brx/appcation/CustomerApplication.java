package com.brx.appcation;

import android.app.Application;

import com.elsw.log.MyExceptionHandler;

public class CustomerApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// 错误日志־
		MyExceptionHandler.getInstanceMyExceptionHandler().init(this);
	}
}

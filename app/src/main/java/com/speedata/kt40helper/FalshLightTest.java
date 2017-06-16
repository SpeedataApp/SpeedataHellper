package com.speedata.kt40helper;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.SystemProperties;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.speedata.kt40helper_.R;

public class FalshLightTest extends MyActivity {
	private Camera camera;// = Camera.open();
	private Parameters parameter;// = camera.getParameters();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_falshlight);
		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					openLight();
				} else {
					closeLight();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (SystemProperties.get("persist.sys.iscamera").equals("close")) {
			SystemProperties.set("persist.sys.scanstopimme", "true");
			Intent opencam = new Intent();
			opencam.setAction("com.se4500.opencamera");
			this.sendBroadcast(opencam, null);
		}
		camera = Camera.open();
		parameter = camera.getParameters();
		System.out.println("ceshi----");
		openLight();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			camera = Camera.open();
			parameter = camera.getParameters();
			System.out.println("ceshi----");
			openLight();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void openLight() {
		// 打开闪光灯关键代码
		camera.startPreview();
		parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
		camera.setParameters(parameter);
		Toast.makeText(this, "open", Toast.LENGTH_SHORT).show();
	}

	public void closeLight() {// 关闭闪关灯关键代码
		parameter = camera.getParameters();
		parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(parameter);// 关闭闪关灯关键代码
		parameter = camera.getParameters();
		parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(parameter);
		Toast.makeText(this, "close", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
		if (SystemProperties.get("persist.sys.iscamera").equals("open")) {
			SystemProperties.set("persist.sys.scanstopimme", "false");
			Intent opencam = new Intent();
			opencam.setAction("com.se4500.closecamera");
			this.sendBroadcast(opencam, null);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
		if (SystemProperties.get("persist.sys.iscamera").equals("open")) {
			SystemProperties.set("persist.sys.scanstopimme", "false");
			Intent opencam = new Intent();
			opencam.setAction("com.se4500.closecamera");
			this.sendBroadcast(opencam, null);
		}
	}
}

package com.speedata.kt40helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.speedata.kt40helper_.R;

public class FalshLightTest extends MyActivity {
	Camera camera;// = Camera.open();
	Parameters parameter;// = camera.getParameters();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_falshlight);
		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO 自动生成的方法存根
				if (arg1) {
					openLight();
				} else {
					closeLight();
				}
			}
		});
		// if (judgeSe4500()) {
		// judgePropert();
		// } else {
		if (judgeSe4500()) {
			Intent intent = new Intent();
			intent.setAction("com.se4500.opencamera");
			this.sendBroadcast(intent);
			SystemClock.sleep(300);
		}

		camera = Camera.open();
		parameter = camera.getParameters();
		System.out.println("ceshi----");
		openLight();
		// }

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try {
			camera = Camera.open();
			parameter = camera.getParameters();
			System.out.println("ceshi----");
			openLight();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private boolean judgeSe4500() {
		File DeviceName = new File("proc/se4500");
		try {
			BufferedWriter CtrlFile = new BufferedWriter(new FileWriter(
					DeviceName, false));
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return false;
		} // open
	}

	/**
	 * 判断快捷扫描是否勾选 不勾选跳转到系统设置中进行设置
	 */
	private void judgePropert() {
		String result = SystemProperties.get("persist.sys.keyreport", "true");
		if (result.equals("true")) {

			// android.os.SystemProperties.set(
			// "persist.sys.keyreport", "true");
			new AlertDialog.Builder(this)
					.setTitle(R.string.key_test_back_title)
					.setMessage(R.string.action_dialog_setting_config)
					.setPositiveButton(
							R.string.action_dialog_setting_config_sure_go,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(
											Settings.ACTION_ACCESSIBILITY_SETTINGS);
									startActivityForResult(intent, 1);
								}
							})
					.setNegativeButton(R.string.action_exit_cancel,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									finish();
								}

							}

					).show();
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		camera.release();
		if (judgeSe4500()) {
			Intent intent = new Intent();
			intent.setAction("com.se4500.closecamera");
			this.sendBroadcast(intent);
		}
		super.onDestroy();
	}
}

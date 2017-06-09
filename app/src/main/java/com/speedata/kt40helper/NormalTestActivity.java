package com.speedata.kt40helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.speedata.adapter.ListViewAdapter;
import com.speedata.kt40helper_.R;
import com.speedata.psam3310.Psam3310TestActivity;
import com.speedata.utils.SystemVersion;

public class NormalTestActivity extends MyActivity implements OnClickListener {

	private ListView listSystemVersion;
	private ListView listNormal;
	private ListViewAdapter adapter1;
	private ListViewAdapter adapter2;
	private Context mContext;
	private TextView tvBack;
	private TextView tvTitle;

	private SensorManager sm;
	private Sensor ligthSensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_normal_test);
		tvBack = getBackTextView();
		tvBack.setOnClickListener(this);
		tvTitle = getBackTitle();
		tvTitle.setText(getString(R.string.action_home_nornal_test));
		mContext = this;
		listSystemVersion = (ListView) findViewById(R.id.list_system_version);
		listNormal = (ListView) findViewById(R.id.list_normal_test);
		listNormal.requestFocus();
		adapter1 = new ListViewAdapter(this,
				new int[] { R.drawable.ic_launcher },
				new String[] { getResources().getString(
						R.string.action_version_kernal)
						+ SystemVersion.getFormattedKernelVersion()
						+ "\n"
						+ getResources().getString(R.string.action_version_sdk)
						+ android.os.Build.VERSION.SDK
						+ "\n"
						+ getResources().getString(
								R.string.action_version_system)
						+ android.os.Build.VERSION.RELEASE 
						+ "\n"
						+ "Model:"
						+ android.os.Build.MODEL}, true);
		listSystemVersion.setAdapter(adapter1);
		adapter2 = new ListViewAdapter(this, new String[] {
				getString(R.string.action_nornal_display),
				getString(R.string.action_nornal_camera),
				getString(R.string.action_camera_flashlight),
				getString(R.string.action_nornal_charge),
				// getString(R.string.action_nornal_headset),
				getString(R.string.action_nornal_key),
				getString(R.string.action_nornal_sd),
				getString(R.string.action_nornal_sound),
				getString(R.string.action_nornal_light_sensor_test),
				getString(R.string.action_nornal_psam_test) }, false);
		// adapter2=new ListViewAdapter();
		// adapter2.addItem(getString(R.string.action_nornal_display));
		// for (int i = 0; i < 20; i++) {
		// adapter2.addItem(i + "");
		// }
		listNormal.setAdapter(adapter2);
		listNormal.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					intent.setClass(mContext, DisplayTest.class);
					break;
				case 1:
					intent.setClass(mContext, CameraTest.class);
					break;
				case 2:
					intent.setClass(mContext, FalshLightTest.class);
					break;
				case 3:
					intent.setClass(mContext, UsbTest.class);
					break;
				case 4:
					intent.setClass(mContext, KeyTest.class);
					break;
				case 5:
					// intent.setClass(mContext, SdcardTest.class);
					// Intent mIntent = new Intent();
					ComponentName comp = new ComponentName(
							"com.mediatek.filemanager",
							"com.mediatek.filemanager.FileManagerOperationActivity");
					intent.setComponent(comp);
					intent.setAction("android.intent.action.VIEW");
					// startActivity(mIntent);
					break;
				case 6:
					intent.setClass(mContext, RecordActivity.class);
					break;
				case 7:
					intent.setClass(mContext, LightSenSorActivity.class);
					break;
				case 8:
					intent.setClass(mContext, Psam3310TestActivity.class);
					break;
				default:
					break;
				}
				mContext.startActivity(intent);
			}

		});

		// // 获取SensorManager对象
		// sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		// // 获取Sensor对象
		// ligthSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
		//
		// sm.registerListener(new MySensorListener(), ligthSensor,
		// SensorManager.SENSOR_DELAY_NORMAL);
	}

	// public class MySensorListener implements SensorEventListener {
	//
	// public void onAccuracyChanged(Sensor sensor, int accuracy) {
	//
	// }
	//
	// public void onSensorChanged(SensorEvent event) {
	// // 获取精度
	// float acc = event.accuracy;
	// // 获取光线强度
	// float lux = event.values[0];
	//
	// tvTitle.setText(getString(R.string.action_home_nornal_test)
	// + "  "
	// + getResources().getString(
	// R.string.action_light_sensor_value) + lux);
	// // sb.append("acc ----> " + acc);
	// // sb.append("\n");
	// // sb.append("lux ----> " + lux);
	// // sb.append("\n");
	// //
	// // tvValue.setText(sb.toString());
	// }
	//
	// }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == tvBack) {
			this.finish();
		}
	}
}

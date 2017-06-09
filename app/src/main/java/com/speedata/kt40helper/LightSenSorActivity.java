package com.speedata.kt40helper;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.speedata.kt40helper_.R;

public class LightSenSorActivity extends MyActivity implements OnClickListener {
	private Context mContext;
	private TextView tvBack;
	private TextView tvTitle;
	private TextView tvResult;

	private SensorManager sm;
	private Sensor ligthSensor;
	private StringBuffer sb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_light_sensor_test);
		tvResult = (TextView) findViewById(R.id.tv_light_sensor);
		sb = new StringBuffer();
		tvBack = getBackTextView();
		tvBack.setOnClickListener(this);
		tvTitle = getBackTitle();
		tvTitle.setText(getString(R.string.action_nornal_light_sensor_test));
		mContext = this;
		// 获取SensorManager对象
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		// 获取Sensor对象
		ligthSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

		sm.registerListener(new MySensorListener(), ligthSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	public class MySensorListener implements SensorEventListener {

		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}

		public void onSensorChanged(SensorEvent event) {
			// 获取精度
			float acc = event.accuracy;
			// 获取光线强度
			float lux = event.values[0];

			// tvTitle.setText(getString(R.string.action_home_nornal_test)
			// + "  光感：" + lux);
//			sb.append("acc ----> " + acc);
//			sb.append("\n");
//			sb.append("lux ----> " + lux);
//			sb.append("\n");
			//
			tvResult.setText(getResources().getString(
					R.string.action_light_sensor_value)
					+ lux);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == tvBack) {
			this.finish();
		}
	}

}

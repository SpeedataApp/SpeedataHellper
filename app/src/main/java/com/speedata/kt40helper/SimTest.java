package com.speedata.kt40helper;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.speedata.kt40helper_.R;

public class SimTest extends Activity implements OnClickListener{
	
    private Button mFailButton;
    private Button mSuccessButton;

    private TextView mTotalImei;
    private TextView mValueImei;
    private TextView mTotalNetwork;
    private TextView mValueNetwork;
    private TextView mTotalPhone;
    private TextView mValuePhone;
    private TextView mValueSimoper;

    private static final int TYPE_UNKNOWN = 0;
    private static final int TYPE_GPRS = 1;
    private static final int TYPE_EDGE = 2;
    private static final int TYPE_UMTS = 3;
    private static final int TYPE_HSDPA = 8;
    private static final int TYPE_HSUPA = 9;
    private static final int TYPE_HSPA = 8;
    private static final int TYPE_CDMA = 4;
    private static final int TYPE_EVDO_0 = 5;
    private static final int TYPE_EVDO_A = 6;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.sim_test);

		mFailButton = (Button) findViewById(R.id.failed);
		mSuccessButton = (Button) findViewById(R.id.successed);
        	mValueImei = (TextView) findViewById(R.id.valueimei);
        	mValueNetwork = (TextView) findViewById(R.id.valuenetwork);
        	mValuePhone = (TextView) findViewById(R.id.valuephone);
        	mValueSimoper = (TextView) findViewById(R.id.simoper);
		mFailButton.setOnClickListener(this);
		mSuccessButton.setOnClickListener(this);

		TelephonyManager tm = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
		String strimei = tm.getDeviceId();
		mValueImei.setText(strimei);
		int network_type = tm.getNetworkType();
		if(network_type == TYPE_UNKNOWN){
			mValueNetwork.setText("UNKNOWN");
		}else if(network_type == TYPE_GPRS){
			mValueNetwork.setText("GPRS");
		}else if(network_type == TYPE_EDGE){
			mValueNetwork.setText("EDGE");
		}else if(network_type == TYPE_UMTS){
			mValueNetwork.setText("UMTS");
		}else if(network_type == TYPE_HSDPA){
			mValueNetwork.setText("HSDPA");
		}else if(network_type == TYPE_HSUPA){
			mValueNetwork.setText("HSUPA");
		}else if(network_type == TYPE_HSPA){
			mValueNetwork.setText("HSPA");
		}else if(network_type == TYPE_CDMA){
			mValueNetwork.setText("CDMA");
		}else if(network_type == TYPE_EVDO_0){
			mValueNetwork.setText("EVDO_0");
		}else if(network_type == TYPE_EVDO_A){
			mValueNetwork.setText("EVDO_A");
		}else{
			mValueNetwork.setText("UNKNOWN");
		}
		int phone_type = tm.getPhoneType();
		if(phone_type == 0){
			mValuePhone.setText("UNKNOWN");
		}else if(phone_type == 1){
			mValuePhone.setText("WCDMA");
		}else if(phone_type == 2){
			mValuePhone.setText("CDMA");
		}else{
			mValuePhone.setText("UNKNOWN");
		}
		String stroperator = tm.getSimOperator();
		if(stroperator != null){
			if(stroperator.equals("46000") || stroperator.equals("46002") || stroperator.equals("46007")){
				mValueSimoper.setText("China Mobile");
			}else if(stroperator.equals("46001")){
				mValueSimoper.setText("China Unicom");
			}else if(stroperator.equals("46003")){
				mValueSimoper.setText("China Telecom");
			}	
		}
	}
	

	public void onClick(View v) {
		if(v == mFailButton){
			this.setResult(-1);
			finish();
		}else if(v == mSuccessButton){
			this.setResult(1);
			finish();
		}
		
	}
	
}

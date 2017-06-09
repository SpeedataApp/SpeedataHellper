package com.speedata.kt40helper;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.speedata.kt40helper_.R;
import com.speedata.kt40helper_.R.id;
import com.speedata.kt40helper_.R.layout;
import com.speedata.kt40helper_.R.string;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.geomobile.tda8029.DataConversionUtils;
import cn.geomobile.tda8029.DeviceControl;
import cn.geomobile.tda8029.serial_native;

public class PsamTestActivity extends MyActivity implements OnClickListener {

	private TextView tvTitle, tvBack, tvReturnCmd;
	private Button btnSend;

	private static final String DEVFILE_PATH = "/sys/class/misc/mtgpio/pin";
	private static final String SERIALPORT_PATH = "/dev/ttyMT1";
	private DeviceControl DevCtrl;
	private EditText edvAdpuCmd;

	private serial_native NativeDev;

	private static final byte[] WAKEUP = { (byte) 0xAA };

	private Timer timer;


	private static final byte[] SET_POWER_UP_S10 = { 0x60, 0x00, 0x00,
			(byte) 0xC1, (byte) 0xa1 };
	private static final byte[] SET_POWER_UP_ISO = { 0x60, 0x00, 0x00, 0x69,
			0x09 };
	private static final byte[] SET_POWER_UP_S9 = { 0x60, 0x00, 0x00, 0x6B,
			0x0b };
	private static final byte[] SET_POWER_ISO_3V = { 0x60, 0x00, 0x01, 0x6d,
		0x00, 0x0c };
	private static final byte[] SET_POWER_EMV_3V = { 0x60, 0x00, 0x01, 0x6d,
			0x01, 0x0d };// 01101101
	private static final byte[] SET_POWER_ISO_5V = { 0x60, 0x00, 0x01, 0x6e,
			0x00, 0x0F };
	private static final byte[] SET_POWER_EMV_5V = { 0x60, 0x00, 0x01, 0x6e,
			0x01, 0x0E };// 01101110
	private static final byte[] SET_POWER_ISO_18V = { 0x60, 0x00, 0x01, 0x68,
			0x00, 0x09 };
	private static final byte[] SET_POWER_EMV_18V = { 0x60, 0x00, 0x01, 0x68,
			0x01, 0x08 };

	private static final byte[] SET_BAUD = { 0x60, 0x00, 0x02, 0x0b, 0x11, 0x00, 0x46 };

	private boolean isFirst = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_psam_test);
		tvBack = getBackTextView();
		tvBack.setOnClickListener(this);
		tvTitle = getBackTitle();
		tvTitle.setText(R.string.title_activity_psam_test);
		btnSend = (Button) findViewById(R.id.btn_psam_send);
		tvReturnCmd = (TextView) findViewById(R.id.tv_psam_return);
		btnSend.setOnClickListener(this);
		edvAdpuCmd = (EditText) findViewById(R.id.ev_adpucmd);
		edvAdpuCmd.setText("0084000008");
		try {
			DevCtrl = new DeviceControl(DEVFILE_PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "不支持", Toast.LENGTH_LONG).show();
			finish();
		}

		NativeDev = new serial_native();
		if ((NativeDev.OpenComPort(SERIALPORT_PATH) < 0)) {
			Toast.makeText(this, "打开串口失败", Toast.LENGTH_LONG).show();
			finish();
		}
		DevCtrl.PowerOnDevice();
		if (timer == null) {
			timer = new Timer();
		}
		timer.schedule(new MyTask(), 10, 300);
		// SystemClock.sleep(150);
		Handler tempHandlelr = new Handler();
		tempHandlelr.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
//				NativeDev.WritePort(WAKEUP);
//				SystemClock.sleep(100);
//				NativeDev.WritePort(SET_BAUD);
//				SystemClock.sleep(200);		
//				NativeDev.WritePort(WAKEUP);
//				SystemClock.sleep(200);	
				NativeDev.WritePort(WAKEUP);
				SystemClock.sleep(100);
				NativeDev.WritePort(SET_POWER_UP_S9);
				SystemClock.sleep(100);	
				NativeDev.WritePort(WAKEUP);
				SystemClock.sleep(100);	
				NativeDev.WritePort(SET_POWER_EMV_3V);
			}
		}, 1000 * 1);
	}

	// private class setPowerTask extends TimerTask{
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	//
	// }
	// }
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (DevCtrl != null) {
			DevCtrl.PowerOffDevice();
		}
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		NativeDev.CloseComPort();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_psam_send:
			sendAdpu();
			break;
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}
	}

	private void sendAdpu() {
		// TODO Auto-generated method stub
		String temp = edvAdpuCmd.getText().toString();
		if ("".equals(temp)) {
			Toast.makeText(this, "cann't be null", Toast.LENGTH_SHORT).show();
			return;
		}
		byte[] adpucmd = DataConversionUtils.hexStringToByteArray(temp);
		sendCupCmd(adpucmd);
	}

	private void sendCupCmd(byte[] sendapud) {
		// byte[] sendapud = sendStringToBytes(sendstring);
		NativeDev.WritePort(WAKEUP);
		SystemClock.sleep(500);

		int apudlen = sendapud.length;
		byte[] sendhead = new byte[4];
		sendhead[0] = 0x60;
		if (apudlen > 255) {
			sendhead[1] = (byte) (apudlen % 255);
			sendhead[2] = (byte) 0xff;
		} else {
			sendhead[1] = 0x00;
			sendhead[2] = (byte) apudlen;
		}
		sendhead[3] = 0x00;

		byte[] sendbyte = new byte[apudlen + 5];

		System.arraycopy(sendhead, 0, sendbyte, 0, sendhead.length);

		System.arraycopy(sendapud, 0, sendbyte, 4, sendapud.length);

		sendbyte[apudlen + 4] = sendbyte[0];
		for (int i = 1; i < (apudlen + 4); i++) {
			sendbyte[apudlen + 4] ^= sendbyte[i];
		}
		/************ debug ****************/
		String cnt1 = new String();
		for (byte a : sendbyte) {
			cnt1 += String.format("%02x ", a);
			// Log.d("###sendbyte###", String.format("%02x", a));
		}
		Log.d("###sendbyte###", cnt1);
		/************ debug ****************/
		NativeDev.WritePort(sendbyte);
		// myself = true;
	}

	private static final int BUFSIZE = 32;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			byte[] data = (byte[]) msg.obj;
			// if (data[0] == 0x60) {
			// byte[] temp = new byte[data.length - 3];
			// System.arraycopy(data, 3, temp, 0, temp.length);
			// tvReturnCmd.append(DataConversionUtils.byteArrayToStringLog(
			// temp, temp.length) + "\n");
			// }
			tvReturnCmd.append(DataConversionUtils.byteArrayToStringLog(data,
					data.length) + "\n");
		};
	};

	class MyTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			// if (isFirst) {
			// isFirst = false;
			// NativeDev.WritePort(SET_POWER_ISO_5V);
			// }
			byte[] buf = NativeDev.ReadPort(BUFSIZE);
			if (buf != null) {
				Log.d("###tda###", "read end");
				msg.what = 1;
				msg.obj = buf;
				handler.sendMessage(msg);
			}
		}

	}
}

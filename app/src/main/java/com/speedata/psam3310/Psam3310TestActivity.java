package com.speedata.psam3310;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.serialport.SerialPort;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.geomobile.tda8029.DataConversionUtils;

import com.speedata.kt40helper.MyActivity;
import com.speedata.kt40helper_.R;
import com.speedata.utils.MyLogger;

public class Psam3310TestActivity extends MyActivity implements
		View.OnClickListener {

	private Button btnActivite, btnGetRomdan, btnSendAdpu, btnClear;
	private EditText edvADPU;
	private TextView tvShowData;
	private SerialPort mSerialPort;
	private int fd;
	private DeviceControl mDeviceControl;
	private MyLogger logger = MyLogger.jLog();
	private Context mContext;
	private Timer timer;

	private TextView tvTitle, tvBack, tvReturnCmd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mContext = this;
		initUI();
		initDevice();
		if (timer == null) {
			timer = new Timer();
			timer.schedule(new ReadTask(), 1000, 300);
		}
		// byte[] cmd =
		// DataConversionUtils.hexStringToByteArray("80f0800008122a31632a3bafe0");
		// logger.d("bb--" +
		// DataConversionUtils.byteArrayToString(DataConversionUtils
		// .HexString2Bytes("80f0800008122a31632a3bafe0")));
	}

	private void initUI() {
		setContentView(R.layout.activity_psam_test_final);
		tvBack = getBackTextView();
		tvBack.setOnClickListener(this);
		tvTitle = getBackTitle();
		tvTitle.setText(R.string.title_activity_psam_test);
		btnActivite = (Button) findViewById(R.id.btn_active);
		btnGetRomdan = (Button) findViewById(R.id.btn_get_ramdon);
		btnSendAdpu = (Button) findViewById(R.id.btn_send_adpu);
		btnClear = (Button) findViewById(R.id.btn_clear);
		btnActivite.setOnClickListener(this);
		btnGetRomdan.setOnClickListener(this);
		btnSendAdpu.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		tvShowData = (TextView) findViewById(R.id.tv_show_message);
		tvShowData.setMovementMethod(ScrollingMovementMethod.getInstance());
		edvADPU = (EditText) findViewById(R.id.edv_adpu_cmd);
		edvADPU.setText("0084000008");
		// pin = { 0x00, 0x20, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00 };
		edvADPU.setText("0020000003");
		edvADPU.setText("80f0800008122a31632a3bafe0");
		// edvADPU.setText("80f002 00 01 02");
	}

	String send_data = "";

	@Override
	public void onClick(View v) {
		if (v == btnActivite) {
			mSerialPort.WriteSerialByte(fd, getPowerCmd());
			send_data = DataConversionUtils.byteArrayToString(getPowerCmd());
			tvShowData.setText(getResources().getString(R.string.text_send_pck)
					+ ":" + send_data + "\n");
		} else if (v == btnGetRomdan) {
			mSerialPort.WriteSerialByte(fd, getRomdan());
			send_data = DataConversionUtils.byteArrayToString(getRomdan());
			tvShowData.setText(getResources().getString(R.string.text_send_pck)
					+ "：\n" + send_data + "\n");
		} else if (v == btnSendAdpu) {
			String temp_cmd = edvADPU.getText().toString();
			if ("".equals(temp_cmd) || temp_cmd.length() % 2 > 0
					|| temp_cmd.length() < 4) {
				Toast.makeText(mContext, "请输入有效命令！", Toast.LENGTH_SHORT).show();
				return;
			}
			mSerialPort.WriteSerialByte(fd,
					adpuPackage(DataConversionUtils.HexString2Bytes(temp_cmd)));
			send_data = temp_cmd;
			tvShowData.setText(getResources().getString(R.string.text_send_pck)
					+ "：\n" + send_data + "\n");
		} else if (v == btnClear) {
			tvShowData.setText("");
		} else if (v == tvBack) {
			finish();
		}
	}

	private String gpio = "48";

	private void initDevice() {
		mSerialPort = new SerialPort();
		String version = android.os.Build.VERSION.RELEASE;
		if (version.equals("5.1")) {
			gpio = "7";
		} else {
			gpio = "48";
		}
		try {
			// 19200
			// /dev/ttyMT2 kt45
			// /dev/ttyMT1 kt40
			// mSerialPort.OpenSerial("/dev/ttyMT2", 19200);
			mSerialPort.OpenSerial("/dev/ttyMT1", 19200);
			fd = mSerialPort.getFd();
			logger.d("--onCreate--open-serial=" + fd);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "无串口权限,强制退出！", Toast.LENGTH_LONG).show();
			e.printStackTrace();
			System.exit(0);
		}
		try {
			mDeviceControl = new DeviceControl("sys/class/misc/mtgpio/pin");
		} catch (IOException e) {
			e.printStackTrace();
			mDeviceControl = null;
			return;
		}
		mDeviceControl.PowerOnDevice(gpio);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDeviceControl.PowerOffDevice(gpio);
		mSerialPort.CloseSerial(fd);
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			byte[] temp_cmd = (byte[]) msg.obj;
			byte[] byte_len = new byte[2];
			byte_len[0] = temp_cmd[3];
			byte_len[1] = temp_cmd[2];
			int len = DataConversionUtils.byteArrayToInt(byte_len);
			if (len <= 6) {
				tvShowData.append("error:"
						+ DataConversionUtils.byteArrayToString(temp_cmd));
				return;
			}
			byte[] temp_data = new byte[len - 6];
			logger.d("---len=" + len + temp_cmd[0] + "  " + temp_cmd[1] + "  "
					+ temp_cmd.length);
			String data;// = DataConversionUtils.byteArrayToString(temp_cmd);

			tvShowData.append(getResources().getString(R.string.text_rece_pck)
					+ "：\n" + DataConversionUtils.byteArrayToString(temp_cmd)
					+ "\n");
			// tvShowData.append("命令头：" +
			// DataConversionUtils.byteArrayToString(new
			// byte[]{temp_cmd[0], temp_cmd[1]}) + "\n");
			// tvShowData.append("长度字：" +
			// DataConversionUtils.byteArrayToString(new
			// byte[]{temp_cmd[2], temp_cmd[3]}) + "-->" + DataConversionUtils
			// .byteArrayToInt(new byte[]{temp_cmd[3], temp_cmd[2]}) + "\n");
			// tvShowData.append("设备标识：" +
			// DataConversionUtils.byteArrayToString(new
			// byte[]{temp_cmd[4], temp_cmd[5]}) + "\n");
			// tvShowData.append("命令码：" +
			// DataConversionUtils.byteArrayToString(new
			// byte[]{temp_cmd[6], temp_cmd[7]}) + "\n");
			// tvShowData.append("状态字：" +
			// DataConversionUtils.byteArrayToString(new
			// byte[]{temp_cmd[8]}));
			// if (temp_cmd[8] == 0) {
			// tvShowData.append("-->成功\n");
			// }
			tvShowData.append(getResources()
					.getString(R.string.text_parse_data) + "：" + "\n");
			for (int i = 0; i < len - 6; i++) {
				temp_data[i] = temp_cmd[i + 9];
			}
			if (len <= 6) {
				return;
			}
			tvShowData.append(DataConversionUtils.byteArrayToString(temp_data));
			// String temp = tvShowData.getText().toString();
			// if (temp.length() < 10000) {
			// tvShowData.append(data + "\n");
			// } else {
			// tvShowData.setText(data);
			// }
		}
	};

	/**
	 * 读串口
	 */
	private class ReadTask extends TimerTask {
		public void run() {
			try {
				byte[] temp1 = mSerialPort.ReadSerial(fd, 1024);
				// logger.d("----read--run");
				logger.d("----read");
				if (temp1 != null) {
					logger.d("----read--ok---"
							+ DataConversionUtils.byteArrayToStringLog(temp1,
									temp1.length));
					Message msg = new Message();
					msg.what = 1;
					msg.obj = temp1;
					handler.sendMessage(msg);
					SystemClock.sleep(1000);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 打包adpu指令
	 * 
	 * @param cmd
	 * @return
	 */
	private byte[] adpuPackage(byte[] cmd) {
		byte[] result = new byte[cmd.length + 9];
		result[0] = (byte) 0xaa;
		result[1] = (byte) 0xbb;
		result[2] = (byte) (cmd.length + 5);
		result[3] = 0x00;
		result[4] = 0x00;
		result[5] = 0x00;
		result[6] = cardType;
		result[7] = 0x06;
		result[result.length - 1] = 0x51;
		System.arraycopy(cmd, 0, result, 8, cmd.length);
		return result;
	}

	private byte cardType = 0x13;// 0x13 卡1 0x23卡2

	private byte[] getRomdan() {
		// 0084000008
		// aabb 0A00 0000 2306 00A4040012 51
		// aabb 0a00 0000 2306 0084000008 51
		// aabb 0500 0000 1306 0084000008 51
		byte[] cmd = new byte[] { (byte) 0xaa, (byte) 0xbb, 0x0a, 0x00, 0x00,
				0x00, cardType, 0x06, 0x00, (byte) 0x84, 0x00, 0x00, 0x08, 0x51 };
		return cmd;
	}

	private byte[] getPowerCmd() {
		// aabb05000000110651
		// aabb05000000120651
		// //IC卡复位3V
		// aabb05000000110651
		// IC卡复位5V
		// aabb05000000120651

		// /**************************************接触卡SAM1
		// 操作************************************************/
		// IC卡复位3V
		// aabb05000000110651
		//
		// //IC卡复位5V
		// aabb05000000120651
		//
		// //IC卡收发数据
		// aabb0A000000130600A404000051
		// aabb0A000000130600A404001251
		//
		// //IC卡去活
		// aabb05000000140651
		//
		// //IC卡热复位
		// aabb05000000150651
		//
		//
		//
		// /**************************************接触卡SAM2操作************************************************/
		// //IC卡复位3V
		// aabb05000000210651
		//
		// //IC卡复位5V
		// aabb05000000220651
		//
		// //IC卡收发数据
		// aabb0A000000230600A404000051
		// aabb0A000000230600A404001251
		//
		// //IC卡去活
		// aabb05000000240651
		//
		// //IC卡热复位
		// aabb05000000250651
		byte[] cmd = new byte[] { (byte) 0xaa, (byte) 0xbb, 0x05, 0x00, 0x00,
				0x00, cardPower, (byte) 0x06, 0x51 };
		// byte[] cmd = new byte[]{(byte) 0xaa, (byte) 0xbb, 0x05, 0x00, 0x00,
		// 0x00, 0x11, (byte)
		// 0x06, 0x51};
		return cmd;
	}

	private byte cardPower = 0x11;// 0x21卡2 0x11卡1

	/**
	 * 获取当前应用程序的版本号
	 */
	private String getVersion() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
			String version = packinfo.versionName;
			return version;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return "版本号错误";
		}
	}
}

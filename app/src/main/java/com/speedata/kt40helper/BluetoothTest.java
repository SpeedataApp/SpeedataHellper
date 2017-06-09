package com.speedata.kt40helper;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.speedata.kt40helper_.R;

public class BluetoothTest extends MyActivity implements OnClickListener {
	private static final int REQUEST_ENABLE_BT = 2;
	private static final String TAG = "BluetoothTest";
	TextView tvShowSelf, tvShowResult;
	TextView txt_see;
	TextView txt_scan;
	BluetoothAdapter mBluetoothAdapter;
	ArrayAdapter<String> mArrayAdapter;
	List<String> lstDevices = new ArrayList<String>();
	Button btn_switch;
	Button btn_see;
	Button btn_scan;
	ListView list;
	CountDownTimer see_timer;
	CountDownTimer scan_timer;

	private TextView tvBack;
	private TextView tvTitle;
	private TextView tvRight;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);

		tvRight = getRight();
		tvRight.setVisibility(View.VISIBLE);
		tvRight.setOnClickListener(this);
		tvBack = getBackTextView();
		tvBack.setOnClickListener(this);
		tvTitle = getBackTitle();
		tvTitle.setText(getResources()
				.getString(R.string.action_home_bluetooth));
		tvShowSelf = (TextView) findViewById(R.id.textView1);
		txt_see = (TextView) findViewById(R.id.textView2);
		txt_scan = (TextView) findViewById(R.id.tv_stop_scan);
		tvShowResult = (TextView) findViewById(R.id.tvconnect_result);
		// 绑定XML中的ListView，作为Item的容器
		list = (ListView) findViewById(R.id.listView1);

		// 获取蓝牙适配器
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lstDevices);
		if (mBluetoothAdapter == null) {
			// Device does not support Bluetooth
			tvShowSelf.setText("fail");
			// 退出程序
			BluetoothTest.this.finish();
		}

		btn_switch = (Button) findViewById(R.id.btn_switch);
		btn_switch.setOnClickListener(this);
		btn_switch.setText("OFF");
		btn_see = (Button) findViewById(R.id.btn_switch_see);
		btn_see.setOnClickListener(this);
		btn_see.setEnabled(false);
		btn_scan = (Button) findViewById(R.id.bv_scan);
		btn_scan.setOnClickListener(this);
		btn_scan.setText(getResources().getString(R.string.bluetooth_scan)
				+ ":OFF");
		btn_scan.setEnabled(false);

		// 判断蓝牙是否已经被打开
		if (mBluetoothAdapter.isEnabled()) {
			// 打开
			showSelfAddress();
			btn_switch.setText("ON");
			btn_see.setEnabled(true);
			btn_scan.setEnabled(true);
		} else {
			tvShowSelf.setText(getResources().getString(
					R.string.bluetooth_state_noopen));
		}

		see_timer = new CountDownTimer(120000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				txt_see.setText(getResources().getString(
						R.string.bluetooth_left_see_time)
						+ millisUntilFinished / 1000 + "s");
			}

			@Override
			public void onFinish() {
				// 判断蓝牙是否已经被打开
				if (mBluetoothAdapter.isEnabled()) {
					showSelfAddress();
					btn_see.setEnabled(true);
					txt_see.setText(getResources().getString(
							R.string.bluetooth_device_cannot_see));
				}
			}
		};
		scan_timer = new CountDownTimer(20000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				// 剩余时间
				txt_scan.setText(getResources().getString(
						R.string.bluetooth_left_scan_time)
						+ millisUntilFinished / 1000 + "s");
			}

			@Override
			public void onFinish() {
				// 判断蓝牙是否已经被打开
				if (mBluetoothAdapter.isEnabled()) {
					btn_scan.setEnabled(true);
					// 关闭扫描
					mBluetoothAdapter.cancelDiscovery();
					btn_scan.setText(getResources().getString(
							R.string.bluetooth_scan)
							+ ":OFF");
					txt_scan.setText(getResources().getString(
							R.string.bluetooth_scan_stop));
				}
			}
		};
		list.setOnItemClickListener(new ItemClickEvent());

		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				String action = intent.getAction();
				// When discovery finds a device
				// mArrayAdapter.clear();
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					// Get the BluetoothDevice object from the
					// Intent
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					String str = "";
					if (device.getBondState() == BluetoothDevice.BOND_NONE) {
						str = getResources().getString(
								R.string.bluetooth_state_no_pair)
								+ "|"
								+ device.getName()
								+ "|"
								+ device.getAddress();
					} else if (device.getBondState() == BluetoothDevice.BOND_BONDING) {
						str = getResources().getString(
								R.string.bluetooth_state_pairing)
								+ "|"
								+ device.getName()
								+ "|"
								+ device.getAddress();
					} else if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
						str = getResources().getString(
								R.string.bluetooth_state_pair)
								+ "|"
								+ device.getName()
								+ "|"
								+ device.getAddress();
					}
					int position = lstDevices.indexOf(str);
					if (position == -1)// 防止重复添加
					{
						lstDevices.add(str); // 获取设备名称和mac地址
					} else {
						lstDevices.remove(position);
						lstDevices.add(str);
					}
					mArrayAdapter.notifyDataSetChanged();

				} else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED
						.equals(action)) {
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					switch (device.getBondState()) {
					case BluetoothDevice.BOND_BONDING:
						Log.d("BlueToothTestActivity", "正在配对......");
						break;
					case BluetoothDevice.BOND_BONDED:
						Log.d("BlueToothTestActivity", "完成配对");
						System.out.println("BlueToothTestActivity-start");
						connect(device);
						System.out.println("BlueToothTestActivity--end");
						break;
					case BluetoothDevice.BOND_NONE:
						Log.d("BlueToothTestActivity", "取消配对");
					default:
						break;
					}
				}

			}
		};

		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(mReceiver, filter); // Don't forget to
												// unregister during
												// onDestroy
	}

	BroadcastReceiver mReceiver;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// android.os.Process.killProcess(android.os.Process.myPid());
		mBluetoothAdapter.disable();
		unregisterReceiver(mReceiver);
	}

	private List<BluetoothDevice> mBluetoothDeviceList = new ArrayList<BluetoothDevice>();

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;
		case R.id.btn_switch: {
			String str = btn_switch.getText().toString();
			ShowConnectResult("");
			if (str == "OFF") {
				if (!mBluetoothAdapter.isEnabled()) {
					// 打开蓝牙
					Intent enableBtIntent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
					showSelfAddress();
					btn_see.setEnabled(true);
					btn_scan.setText(getResources().getString(
							R.string.bluetooth_scan)
							+ ":OFF");
					btn_scan.setEnabled(true);
				}
			} else {
				// 关闭蓝牙
				mBluetoothAdapter.disable();
				btn_switch.setText("OFF");
				// mBluetoothDeviceList.clear();
				// mArrayAdapter.clear();
				lstDevices.clear();
				mArrayAdapter.notifyDataSetChanged();
				list.setAdapter(mArrayAdapter);
				btn_see.setEnabled(false);
				btn_scan.setEnabled(false);
			}

			break;
		}
		case R.id.btn_switch_see: {
			// 开启可见
			Intent enableBtIntent_See = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			startActivityForResult(enableBtIntent_See, 3);
			showSelfAddress();
			btn_see.setEnabled(false);
			see_timer.start();

			break;
		}
		case R.id.bv_scan: {
			String str = btn_scan.getText().toString();
			if (str.contains("OFF")) {
				showSelfAddress();
				if (mBluetoothAdapter.isEnabled()) {
					// 开始扫描
					mBluetoothAdapter.startDiscovery();
					// tvTop.setText("s6");
					btn_scan.setText(getResources().getString(
							R.string.bluetooth_scan)
							+ ":ON");
					mBluetoothDeviceList.clear();
					mArrayAdapter.clear();
					mArrayAdapter.notifyDataSetChanged();
					list.setAdapter(mArrayAdapter);
					ShowConnectResult("");
					// Create a BroadcastReceiver for ACTION_FOUND

					scan_timer.start();
				}
			} else {
				// 关闭扫描
				mBluetoothAdapter.cancelDiscovery();
				btn_scan.setText(getResources().getString(
						R.string.bluetooth_scan)
						+ ":OFF");
				scan_timer.cancel();
				txt_scan.setText(getResources().getString(
						R.string.bluetooth_scan_stop));
			}

			break;
		}
		case R.id.tv_select_pic:
			Intent mIntent = new Intent();
			ComponentName comp = new ComponentName("com.mediatek.filemanager",
					"com.mediatek.filemanager.FileManagerOperationActivity");
			mIntent.setComponent(comp);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mIntent.setAction("android.intent.action.VIEW");
			startActivityForResult(mIntent,0);
			break;
		default:
			break;
		}
	}

	public static Intent getImageFileIntent() {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// Uri uri = Uri.fromFile(new File(filename));
		// intent.setDataAndType(uri, "image/*");
		return intent;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				btn_switch.setText("ON");
				// tvTop.setText("s4");
				showSelfAddress();

				// 获取蓝牙列表
				Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
						.getBondedDevices();
				mBluetoothDeviceList.clear();
				mArrayAdapter.clear();
				mArrayAdapter.notifyDataSetChanged();
				// If there are paired devices

				if (pairedDevices.size() > 0) {
					// txt.setText("s3");
					ShowConnectResult(getResources().getString(
							R.string.bluetooth_state_pair)
							+ "：" + pairedDevices.size());
					// Loop through paired devices
					for (BluetoothDevice device : pairedDevices) {
						// Add the name and address to an array adapter to show
						// in a ListView
						mArrayAdapter.add(getResources().getString(
								R.string.bluetooth_state_pair)
								+ "|"
								+ device.getName()
								+ "|"
								+ device.getAddress());
					}
					list.setAdapter(mArrayAdapter);
				}
			} else {
				finish();
			}
		}
	}

	ProgressDialog mProgressDialog;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 1) {
				ShowConnectResult((String) msg.obj
						+ getResources().getString(
								R.string.bluetooth_state_pair));
			} else if (msg.what == 0) {

				ShowConnectResult((String) msg.obj
						+ getResources().getString(
								R.string.bluetooth_state_pair));
			}
			if (mProgressDialog != null) {
				mProgressDialog.cancel();
			}
			super.handleMessage(msg);
		}

	};

	private void connect(final BluetoothDevice btDev) {
		final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
		// showRemind(getResources()
		// .getString(R.string.bluetooth_state_connecting));
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				try {
					UUID uuid = UUID.fromString(SPP_UUID);
					BluetoothSocket btSocket = btDev
							.createRfcommSocketToServiceRecord(uuid);
					Log.d("BlueToothTestActivity", "开始连接...");
					// mBluetoothAdapter.cancelDiscovery();

					btSocket.connect();
					// SystemClock.sleep(1000);
					String message = "link success ff";
					OutputStream mmOutStream = btSocket.getOutputStream();
					mmOutStream.write(message.getBytes());
					msg.what = 1;

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = 0;
				}
				msg.obj = btDev.getName();
				handler.sendMessage(msg);
			}
		}).start();

	}

	private void showRemind(String showdata) {
		if (mProgressDialog != null) {
			mProgressDialog.cancel();// = new ProgressDialog(this);
		}
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle(showdata);
		mProgressDialog.show();
		System.out.println("mProgressDialog.show");
	}

	private void ShowConnectResult(String data) {
		tvShowResult.setText(data);
	}

	class ItemClickEvent implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (mBluetoothAdapter.isDiscovering())
				mBluetoothAdapter.cancelDiscovery();
			String str = mArrayAdapter.getItem(arg2);
			String[] values = str.split("\\|");
			String address = values[2];
			Log.e("address", values[2]);
			BluetoothDevice btDev = mBluetoothAdapter.getRemoteDevice(address);
			try {
				Boolean returnValue = false;
				if (btDev.getBondState() == BluetoothDevice.BOND_NONE) {
					// 利用反射方法调用BluetoothDevice.createBond(BluetoothDevice
					// remoteDevice);
					Method createBondMethod = BluetoothDevice.class
							.getMethod("createBond");
					Log.d("BlueToothTestActivity", "开始配对");
					showRemind(getResources().getString(
							R.string.bluetooth_state_pairing));
					returnValue = (Boolean) createBondMethod.invoke(btDev);

				} else if (btDev.getBondState() == BluetoothDevice.BOND_BONDED) {
					connect(btDev);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void showSelfAddress() {
		tvShowSelf.setText(getResources().getString(
				R.string.bluetooth_self_name)
				+ mBluetoothAdapter.getName()
				+ " MAC | "
				+ mBluetoothAdapter.getAddress());
	}

	private void sendDataToPairedDevice(String message, BluetoothDevice device) {
		byte[] toSend = message.getBytes();
		try {
			UUID applicationUUID = UUID
					.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
			BluetoothSocket socket = device
					.createInsecureRfcommSocketToServiceRecord(applicationUUID);
			OutputStream mmOutStream = socket.getOutputStream();
			mmOutStream.write(toSend);
			// Your Data is sent to BT connected paired device ENJOY.
		} catch (IOException e) {
			Log.e(TAG, "Exception during write", e);
		}
	}

}

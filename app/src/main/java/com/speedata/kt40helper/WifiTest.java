package com.speedata.kt40helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.speedata.kt40helper_.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class WifiTest extends MyActivity implements OnClickListener {

	private TextView tvBack;
	private TextView tvTitle;
	private TextView tvWifiState;
	private Button bvSet;
	private Button bvOpenIE;
	private WebView mWebView;
	private WifiManager wifi_service;
	private WifiInfo wifiInfo;

	private Handler handler;
	private int level; // 信号强度值
	private Context mContext;
	private ImageView wifi_image;
	private Timer timer;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		super.onDestroy();
		timer.cancel();
	}
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi_test);
		mContext = this;
		bvSet = (Button) findViewById(R.id.btnSet);
		bvOpenIE = (Button) findViewById(R.id.btnOpen);
		wifi_image = (ImageView) findViewById(R.id.img_wifi);
		bvOpenIE.setOnClickListener(this);
		bvSet.setOnClickListener(this);
		tvBack = getBackTextView();
		tvBack.setOnClickListener(this);
		tvTitle = getBackTitle();
		tvTitle.setText(getString(R.string.action_home_wifi));
		// mWebView = (WebView) findViewById(R.id.web);
		tvWifiState = (TextView) findViewById(R.id.tv_wifi_state);
		// mWebView.setVisibility(View.GONE);
		wifi_service = (WifiManager)mContext.getSystemService(WIFI_SERVICE);
		wifiInfo = wifi_service.getConnectionInfo();
		judgeWifi();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				wifiInfo = wifi_service.getConnectionInfo();
				// 获得信号强度值
				level = wifiInfo.getRssi();
				// 根据获得的信号强度发送信息
				if (level <= 0 && level >= -50) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				} else if (level < -50 && level >= -70) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				} else if (level < -70 && level >= -80) {
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				} else if (level < -80 && level >= -100) {
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 5;
					handler.sendMessage(msg);
				}

			}

		}, 1000, 5000);
		// 使用Handler实现UI线程与Timer线程之间的信息传递,每5秒告诉UI线程获得wifiInto
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				// 如果收到正确的消息就获取WifiInfo，改变图片并显示信号强度
				case 1:
					wifi_image.setImageResource(R.drawable.single4);
					ShowWifiState(getResources().getString(
							R.string.wifi_state_perfect));// ,
					break;
				case 2:
					wifi_image.setImageResource(R.drawable.single3);
					ShowWifiState(getResources().getString(
							R.string.wifi_state_better));// + level +
					// " 信号较好",
					break;
				case 3:
					wifi_image.setImageResource(R.drawable.single2);
					ShowWifiState(getResources().getString(
							R.string.wifi_state_normal));// ,
					break;
				case 4:
					wifi_image.setImageResource(R.drawable.single1);
					ShowWifiState(getResources().getString(
							R.string.wifi_state_bad));// + level +
					// " 信号较差",
					break;
				case 5:
					wifi_image.setImageResource(R.drawable.single0);
					ShowWifiState(getResources().getString(
							R.string.wifi_state_no_single));// + level + " 无信号",
					break;
				default:
					// 以防万一
					wifi_image.setImageResource(R.drawable.single0);
					ShowWifiState(getResources().getString(
							R.string.wifi_state_no_single));// ,
															// Toast.LENGTH_SHORT).show();
				}
			}

		};
		// String result=pingHost("202.108.22.103");
		// if(result.equalsIgnoreCase("success")){
		// tvWifiState.append("Internet访问");
		// }else{
		// tvWifiState.append("无Internet访问");
		// }
	}

	private void judgeWifi() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (!mWifi.isConnected()) {
			// Do whatever
			new AlertDialog.Builder(this)
					.setTitle(R.string.dialog_title_remind)
					.setMessage(R.string.dialog_no_wifi)
					.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									Intent intent = new Intent();
									intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
									startActivity(intent);
								}
							})
					.setNegativeButton(R.string.back,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).show();
		}
	}

	private void ShowWifiState(String state) {

		int ipAddress = wifiInfo.getIpAddress();
		String ip = ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
				+ (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
		if (wifiInfo != null) {
			tvWifiState.setText("Name:" + wifiInfo.getSSID() + "\nIP:" + ip
					+ "\nMac:" + wifiInfo.getMacAddress() + "\n speed:"
					+ wifiInfo.getLinkSpeed() + " \n" + wifiInfo.getBSSID()
					+ "\n" + state);
		} else {
			tvWifiState.setText(getResources().getString(
					R.string.wifi_not_connect));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == tvBack) {
			finish();
		} else if (v == bvOpenIE) {
			// mWebView.setVisibility(View.VISIBLE);
			// mWebView.loadUrl("http://www.speedatagroup.com/");
			// wifi_image.setVisibility(View.INVISIBLE);
			Uri uri = Uri.parse("http://www.speedatagroup.com/");

			Intent intent = new Intent(Intent.ACTION_VIEW, uri);

			startActivity(intent);
		} else if (v == bvSet) {
			Intent intent = new Intent();
			intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
			startActivity(intent);
		}
	}

	public static String pingHost(String str) {
		String resault = "";
		try {
			// TODO: Hardcoded for now, make it UI configurable
			Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + str);
			int status = p.waitFor();
			if (status == 0) {
				// mTextView.setText("success") ;
				resault = "success";
			} else {
				resault = "faild";
				// mTextView.setText("fail");
			}
		} catch (IOException e) {
			// mTextView.setText("Fail: IOException"+"\n");
		} catch (InterruptedException e) {
			// mTextView.setText("Fail: InterruptedException"+"\n");
		}

		return resault;
	}
}

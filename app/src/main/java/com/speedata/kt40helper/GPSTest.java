package com.speedata.kt40helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.speedata.kt40helper_.R;
import com.speedata.utils.MyLogger;

public class GPSTest extends MyActivity implements OnClickListener {
	MyLogger logger;

	TextView tvBack;
	TextView tvTitle;
	TextView tvResult;
	RadioGroup radioWorkType;
	RadioButton typeGPS;
	RadioButton typeGPSWifi;
	Button bvScan;
	Button bvSet;
	boolean stop = false;
	Context mContext;
	// gps
	LocationManager locationManager;

	// baidu定位相关
	public MyLocationListenner myListener = new MyLocationListenner();
	public LocationClient mLocClient;
	private static BDLocation currentLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gps_test);
		logger = MyLogger.jLog();
		caledar = Calendar.getInstance();
		radioWorkType = (RadioGroup) findViewById(R.id.radio_gps_work_type);
		typeGPS = (RadioButton) findViewById(R.id.radio_gps);
		typeGPSWifi = (RadioButton) findViewById(R.id.radio_gps_wifi);
		tvResult = (TextView) findViewById(R.id.tv_show);
		tvBack = (TextView) findViewById(R.id.tv_back);
		tvBack.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText(getString(R.string.action_home_gps));
		bvScan = (Button) findViewById(R.id.bv_scan);
		bvSet = (Button) findViewById(R.id.bv_set);
		bvScan.setOnClickListener(this);
		bvSet.setOnClickListener(this);

		radioWorkType.check(0);
		mContext = this;
		if (!isGpsEnable()) {
			remind(getResources().getString(R.string.dialog_open_gps),
					getResources().getString(R.string.ok), 0);
		}
		typeGPSWifi.setChecked(true);
		// typeGPSWifi.setOnCheckedChangeListener(this);
		// typeGPS.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == tvBack) {
			this.finish();
		} else if (v == bvScan) {
			tvResult.setText("");
			if (stop) {
				// 停止
				stop = false;
				bvScan.setText(mContext.getString(R.string.gps_start));
				// radioWorkType.setClickable(true);
				typeGPS.setClickable(true);
				typeGPSWifi.setClickable(true);
				StopGetLocation();
			} else {
				stop = true;
				// radioWorkType.setClickable(false);
				typeGPS.setClickable(false);
				typeGPSWifi.setClickable(false);

				bvScan.setText(mContext.getString(R.string.gps_stop));
				// 工作模式
				int key = radioWorkType.getCheckedRadioButtonId();
				switch (key) {
				case R.id.radio_gps:
					remind(getResources()
							.getString(R.string.remind_gps_content),
							getResources().getString(R.string.sure), 1);
					// startGPSLoction();
					break;
				case R.id.radio_gps_wifi:
					// startBaiDuLocation();
					if (judgeWifi()) {
						remind(getResources().getString(R.string.remind_baidu),
								getResources().getString(R.string.sure), 2);
					} else {
						remind(getResources().getString(
								R.string.remind_baidu_no_wifi), getResources()
								.getString(R.string.sure), 2);
					}

					break;
				default:
					break;
				}
			}

		} else if (v == bvSet) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivityForResult(intent, 1);
		}
	}

	private void stopGPSLoction() {
		// TODO Auto-generated method stub
		locationManager.removeUpdates(locationListener);
		locationManager.removeGpsStatusListener(gpsStausListener);
	}

	// Gps是否可用
	private boolean isGpsEnable() {
		locationManager = ((LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE));
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	Calendar caledar;
	LocationListener locationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			System.out.println("-----");
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(Location location) {
			caledar.setTimeInMillis(location.getTime());
			tvResult.setText("\nlatitude" + location.getLatitude()
					+ "\nlongtitude" + location.getLongitude() + "\ntime"
					+ caledar.getTime());
			System.out.println("location" + caledar.getTime());
		}
	};
	Listener gpsStausListener = new Listener() {

		@Override
		public void onGpsStatusChanged(int event) {
			switch (event) {
			case GpsStatus.GPS_EVENT_FIRST_FIX: {
				// fixed = true;
				break;
			}
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS: {
				GpsStatus currentgpsStatus = locationManager.getGpsStatus(null);
				List<GpsSatellite> satelliteList = new ArrayList<GpsSatellite>();
				int maxSatellites = currentgpsStatus.getMaxSatellites();
				logger.d("当前所有卫星数目为" + maxSatellites);
				Iterator<GpsSatellite> iters = currentgpsStatus.getSatellites()
						.iterator();
				mSatelliteCount = 0;
				// tvResult.setText("可用卫星");
				mSatellitesStrenthList = new ArrayList<Float>();
				String result = "";
				while (iters.hasNext() && mSatelliteCount <= maxSatellites) {
					// satelliteList.add(iters.next());
					result += iters.next().getSnr();
					// tvResult.append("," + iters.next().getSnr());
					mSatelliteCount++;
				}
				result += ",总数" + mSatelliteCount;
				// tvResult.append(",总数" + mSatelliteCount + "\n");
				Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
				break;
			}
			case GpsStatus.GPS_EVENT_STARTED: {
				break;
			}
			case GpsStatus.GPS_EVENT_STOPPED: {
				break;
			}
			default:
				break;
			}
		}
	};
	private int mSatelliteCount = 0;
	private List<Float> mSatellitesStrenthList;

	private void startGPSLoction() {
		// 获取位置管理服务
		// LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) this.getSystemService(serviceName);
		// 查找到服务信息
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.ACCURACY_HIGH); // 高功耗

		String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
		Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
		// updateToNewLocation(location);
		// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米

		locationManager.requestLocationUpdates(provider, 1 * 1000, 10,
				locationListener);
		locationManager.addGpsStatusListener(gpsStausListener);
	}

	private boolean iscancel = false;

	private void remind(String message, String buttonMessage, final int op) {
		switch (op) {
		case 0:
			iscancel = true;
			break;
		case 1:
		case 2:
			iscancel = false;
			break;
		default:
			break;
		}
		new AlertDialog.Builder(this)
				.setTitle(R.string.dialog_title_remind)
				.setMessage(message)
				.setPositiveButton(buttonMessage,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (op) {
								// 提示打开gps
								case 0:
									Intent intent = new Intent(
											Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivityForResult(intent, 1);
									break;
								// 提示需要在空旷区定位
								case 1:
									startGPSLoction();
									break;
								// 提示没有打开wifi，需要到空旷处通过百度SDK获取GPS坐标
								case 2:
									startBaiDuLocation();
									break;

								default:
									break;
								}
							}
						}).setCancelable(iscancel).show();
		// .setNegativeButton(R.string.back,
		// new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog,
		// int which) {
		//
		// }
		// }).show();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null)// || mMapView == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nreturn code : ");
			int locType = location.getLocType();
			switch (locType) {
			case 161:
				sb.append(getResources().getString(R.string.baidu_wifi_result));
				break;
			case 61:
				sb.append(getResources().getString(R.string.baidu_gps_result));
				break;
			default:
				sb.append(locType);
				break;
			}

			sb.append("\ncity:");
			sb.append(location.getCity());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (locType == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (locType == BDLocation.TypeNetWorkLocation) {
				/**
				 * 格式化显示地址信息
				 */
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			sb.append("\nsdk version : ");
			sb.append(mLocClient.getVersion());
			// System.out.println("current_city:" + location.getAddrStr());
			tvResult.setText(sb.toString());
		}
	}

	private void startBaiDuLocation() {

		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setIsNeedAddress(true);
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		logger.d("mLocClient.start()");
	}

	private void stopBaiDuLocation() {
		// TODO Auto-generated method stub
		// mLocClient.removeNotifyEvent(arg0)
		if (mLocClient != null) {
			mLocClient.stop();
		}
	}

	private boolean judgeWifi() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (!mWifi.isConnected()) {
			return false;
		} else
			return true;
	}

	@Override
	protected void onDestroy() {
		if (stop) {
			// 停止
			StopGetLocation();
		}
		super.onDestroy();
	}

	private void StopGetLocation() {
		int key = radioWorkType.getCheckedRadioButtonId();
		stop = false;
		bvScan.setText(mContext.getString(R.string.gps_start));
		switch (key) {
		case R.id.radio_gps:
			stopGPSLoction();
			break;
		case R.id.radio_gps_wifi:
			stopBaiDuLocation();
			break;
		default:
			break;
		}
	}

	// @Override
	// public void onCheckedChanged(CompoundButton buttonView, boolean
	// isChecked) {
	// // TODO Auto-generated method stub
	// if(stop){
	// new AlertDialog.Builder(mContext)
	// .setTitle(R.string.dialog_title_remind)
	// .setMessage(R.string.dialog_must_stop_first)
	// .setPositiveButton(R.string.action_exit_sure,
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// // TODO Auto-generated method stub
	// StopGetLocation();
	// }
	// }).setNegativeButton(R.string.back, new DialogInterface.OnClickListener()
	// {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// }
	// } ).show();
	// }
	// }

}

package com.speedata.kt40helper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.speedata.kt40helper_.R;

import java.io.FileOutputStream;

import static android.content.ContentValues.TAG;

public class BarcodeTest extends MyActivity implements OnClickListener {
    //解码广播
    private String RECE_DATA_ACTION = "com.se4500.onDecodeComplete";
    //调用扫描广播
    private String START_SCAN_ACTION = "com.geomobile.se4500barcode";
    //停止扫描广播
    private String STOP_SCAN = "com.geomobile.se4500barcodestop";
    TextView tvBack;
    TextView tvTitle;
    EditText tvResult;
    RadioGroup radioWorkType;
    RadioButton typeSingle;
    RadioButton typeRepeat;
    RadioButton typeTired;
    Button bvScan;
    Button bvSet, bvClear;
    // Button bvAbletAll;
    // Button bvDisableAll;
    private CheckBox mCheckBoxSound, mCheckBoxVibration;

    boolean stop = false;
    Context mContext;

    // // 4500
    // private BarCodeReader bcr = null;
    // // se955
    // private DeviceControl mDeviceControl;
    //
    // boolean isSe4500 = false;
    //
    // private ProgressDialog mProgressDialog;

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        mContext = this;
        // if (mProgressDialog == null) {
        // mProgressDialog = new ProgressDialog(mContext);
        // }
        // mProgressDialog.setTitle("正在初始化请稍后");
        // mProgressDialog.show();
    }

    Vibrator vibrator;
    private SoundPool soundPool;
    private int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        // 注册系统广播 接受扫描到的数据
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(RECE_DATA_ACTION);
        registerReceiver(receiver, iFilter);
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load("/system/media/audio/ui/VideoRecord.ogg", 0);
        //判断扫描快捷是否勾选
        String result = SystemProperties.get("persist.sys.keyreport", "true");
        if (result.equals("false")) {
            keyreport = true;
            android.os.SystemProperties.set("persist.sys.keyreport", "true");
        }
    }

    Handler handler = new Handler();

    private void initUI() {
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_barcode_test);
        // bvAbletAll = (Button) findViewById(R.id.bv_enable_all);
        // bvDisableAll = (Button) findViewById(R.id.bv_disable_all);
        bvSet = (Button) findViewById(R.id.bv_set);
        bvSet.setVisibility(View.GONE);
        bvClear = (Button) findViewById(R.id.bv_clear);
        bvSet.setOnClickListener(this);
        bvClear.setOnClickListener(this);
        // bvDisableAll.setOnClickListener(this);
        // bvAbletAll.setOnClickListener(this);
        radioWorkType = (RadioGroup) findViewById(R.id.radio_barcode_work_type);
        typeSingle = (RadioButton) findViewById(R.id.radio_single);
        typeRepeat = (RadioButton) findViewById(R.id.radio_repeat);
        typeTired = (RadioButton) findViewById(R.id.radio_tired);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvResult = (EditText) findViewById(R.id.tv_show);
        tvTitle.setText(getString(R.string.action_home_scan));

        mCheckBoxSound = (CheckBox) findViewById(R.id.checkbox_sound);
        mCheckBoxVibration = (CheckBox) findViewById(R.id.checkbox_vibration);
        // if (isSe4500) {
        // } else {
        // tvTitle.setText(getString(R.string.action_home_scan));
        // bvAbletAll.setVisibility(View.GONE);
        // bvDisableAll.setVisibility(View.GONE);
        // bvSet.setVisibility(View.GONE);
        // }
        bvScan = (Button) findViewById(R.id.bv_scan);
        bvScan.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        typeSingle.setChecked(true);
        mContext = this;
        // tvResult.setMovementMethod(new ScrollingMovementMethod());
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(android.content.Context context, android.content.Intent intent) {
//			tvResult.requestFocus();
            String action = intent.getAction();
            System.out.println("------------BroadcastReceiver-" + action);
            if (action.equals(RECE_DATA_ACTION)) {
                String data = intent.getStringExtra("se4500");//原始数据
                if (mCheckBoxSound.isChecked()) {
                    // 声音
                    soundPool.play(soundId, 1, 1, 0, 0, 1);
                }
                if (mCheckBoxVibration.isChecked() && !"".equals(data)) {
                    // 震动
                    vibrator.vibrate(500);
                    System.out.println("-----vibrator");
                }
                System.out.println("======barcode====" + data + "====");
//                tvResult.append(data);
                if (tvResult.getLineCount() >= 10) {
                    tvResult.setText(data);
                }
//				 else {
//				 tvResult.append(data);// + "\n");
//				 }
//				tvResult.requestFocus();
                if (data.startsWith("http")) {
                    System.out.println("http---data====" + data);
                    Uri uri = Uri.parse(data);
                    Intent intents = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intents);
                }
                if (isFlag) {
                    cancelRepeat();
                    handler.postDelayed(startTask, 0);
                } else {
                    cancelRepeat();
                }
            }
        }

    };
    private boolean keyreport = false;

    //	/**
//	 * 判断快捷扫描是否勾选 不勾选跳转到系统设置中进行设置
//	 */
//	private void judgePropert() {
//		android.os.SystemProperties.set("persist.sys.keyreport", "true");
//		String result = SystemProperties.get("persist.sys.keyreport", "true");
//		if (result.equals("false")) {
//			new AlertDialog.Builder(this)
//					.setTitle(R.string.key_test_back_title)
//					.setMessage(R.string.action_dialog_setting_config)
//					.setPositiveButton(
//							R.string.action_dialog_setting_config_sure_go,
//							new DialogInterface.OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									// TODO Auto-generated method stub
//									Intent intent = new Intent(
//											Settings.ACTION_ACCESSIBILITY_SETTINGS);
//									startActivityForResult(intent, 1);
//								}
//							})
//					.setNegativeButton(R.string.action_exit_cancel,
//							new DialogInterface.OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									// TODO Auto-generated method stub
//									finish();
//								}
//
//							}
//
//					).show();
//		}
//	}


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        cancelRepeat();
        vibrator.cancel();
        new Thread(new Runnable() {
            @Override
            public void run() {
                stopScanService();
            }
        }).start();
        if (keyreport) {
            android.os.SystemProperties.set("persist.sys.keyreport", "false");
        }
        super.onDestroy();
    }

    private boolean isRepeat = false;


    private boolean isStop = true;
    private boolean isFlag = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bv_scan:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (SystemProperties.get("persist.sys.scanheadtype").equals("6603")) {
                            startScanService();
                        }
                    }
                }).start();
                if (typeSingle.isChecked()) {
                    startScan();
                } else if (typeRepeat.isChecked() || typeTired.isChecked()) {
                    bvScan.setText("扫描");
                    if (isStop) {
                        handler.removeCallbacks(startTask);
                        handler.postDelayed(startTask, 0);
                        bvScan.setText("停止");
                        isFlag = true;
                        isStop = false;
                        typeRepeat.setEnabled(false);
                        typeSingle.setEnabled(false);
                        typeTired.setEnabled(false);
                    } else {
                        bvScan.setText("扫描");
                        isFlag = false;
                        isStop = true;
                        cancelRepeat();
                        typeRepeat.setEnabled(true);
                        typeSingle.setEnabled(true);
                        typeTired.setEnabled(true);
                    }


                } else {

                }

                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.bv_clear:
                tvResult.setText("");
                break;
            case R.id.bv_set:
                PackageManager packageManager = getPackageManager();
                Intent intent = new Intent();
                intent = packageManager.getLaunchIntentForPackage("com.android.settings");
                startActivityForResult(intent, 1);
            default:
                break;
        }
    }

    ;

    public void writeFileData(String fileName, String message) {
        try {
            FileOutputStream fout = mContext.openFileOutput(fileName,
                    Activity.MODE_APPEND);
            byte[] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startScanService() {//启动扫描服务
//        SystemProperties.set("persist.sys.keyreport", "true");
        Intent Barcodeintent = new Intent();
        Barcodeintent.setPackage("com.geomobile.oemscanservice");
        mContext.startService(Barcodeintent);
    }

    private void stopScanService() {//停止扫描服务
//        SystemProperties.set("persist.sys.keyreport", "false");
        SystemProperties.set("persist.sys.scanstopimme", "true");
        Intent Barcodeintent = new Intent();
        Barcodeintent.setPackage("com.geomobile.oemscanservice");
        mContext.stopService(Barcodeintent);
    }

    //连续扫描
    private Runnable startTask = new Runnable() {
        @Override
        public void run() {
            startScan();
            handler.postDelayed(startTask, 3000);
        }
    };

    /**
     * 发送广播  调用系统扫描
     */
    private void startScan() {
        Intent intent = new Intent();
        intent.setAction(STOP_SCAN);
        sendBroadcast(intent);
        SystemProperties.set("persist.sys.scanstopimme", "true");
        Log.i(TAG, "stop");
        SystemClock.sleep(20);
        Log.i(TAG, "start");
        SystemProperties.set("persist.sys.scanstopimme", "false");
        intent.setAction(START_SCAN_ACTION);
        sendBroadcast(intent, null);
    }

    private void cancelRepeat() {
        handler.removeCallbacks(startTask);
        Intent intent = new Intent();
        intent.setAction("com.geomobile.se4500barcodestop");
        sendBroadcast(intent);
        SystemProperties.set("persist.sys.scanstopimme", "true");
    }
}

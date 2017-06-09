package com.speedata.kt40helper;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.speedata.kt40helper_.R;

public class SdcardTest extends MyActivity implements OnClickListener {
	private TextView mTotalView;
	private TextView mAvlView;
	// private Button mfailButton;
	// private Button mSuccessButton;
	private StorageManager mStorageManager = null;
	// private StorageVolume mStorageVolume;
	private String mState;

	private TextView tvBack;
	private TextView tvTitle;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.sdcard_test);
		mContext = this;
		tvBack = getBackTextView();
		tvTitle = getBackTitle();
		tvTitle.setText(getResources().getString(R.string.action_nornal_sd));
		tvBack.setOnClickListener(this);
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		long availableBlocks = stat.getAvailableBlocks();
		long totalSize = totalBlocks * blockSize;
		long availSize = availableBlocks * blockSize;

		if (mStorageManager == null) {
			mStorageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
		}
		mTotalView = (TextView) findViewById(R.id.total_size);
		// mTotalView.setText(formatSize(totalSize));
		mTotalView.setTextSize(28);
//		mTotalView.setText("机身内存:" + getRomTotalSize()
//				+ "\n机身可用内存" + getRomAvailableSize());
		mTotalView.setText("SD卡总容量：" + getSDTotalSize() + "\nSD卡剩余可用容量:"
				+ getSDAvailableSize() + "\n机身内存:" + getRomTotalSize()
				+ "\n机身可用内存" + getRomAvailableSize());
	}

	@Override
	protected void onPause() {
		unregisterReceiver(sdcardListener);
		super.onPause();
	}

	@Override
	protected void onResume() {
		registerSDCardListener();
		super.onResume();
	}

	private String formatSize(long size) {
		return Formatter.formatFileSize(this, size);
	}

	public void onClick(View v) {
		if (v == tvBack) {
			finish();
		}
	}

	private final BroadcastReceiver sdcardListener = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				Log.e("lijiaxing", "Environment.MEDIA_MOUNTED");
			}

		}
	};

	private void registerSDCardListener() {
		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_MEDIA_MOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		intentFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
		intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
		intentFilter.addDataScheme("file");
		registerReceiver(sdcardListener, intentFilter);
	}

	/**
	 * 获得SD卡总大小
	 * 
	 * @return
	 */
	private String getSDTotalSize() {
//		File path = Environment.getExternalStorageDirectory();
		File path = Environment.getRootDirectory();
//		Environment.getRootDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return Formatter.formatFileSize(mContext, blockSize * totalBlocks);
	}

	/**
	 * 获得sd卡剩余容量，即可用大小
	 * 
	 * @return
	 */
	private String getSDAvailableSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return Formatter.formatFileSize(mContext, blockSize * availableBlocks);
	}

	/**
	 * 获得机身内存总大小
	 * 
	 * @return
	 */
	private String getRomTotalSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return Formatter.formatFileSize(mContext, blockSize * totalBlocks);
	}

	/**
	 * 获得机身可用内存
	 * 
	 * @return
	 */
	private String getRomAvailableSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return Formatter.formatFileSize(mContext, blockSize * availableBlocks);
	}

}

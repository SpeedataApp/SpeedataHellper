package com.speedata.kt40helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.android.updateversion.UpdateVersion;
import com.sinpo.xnfc.NFCard;
import com.speedata.kt40helper_.R;
import com.speedata.utils.Contancts;

public class HomeActivity extends MyActivity implements OnClickListener {

	private RadioButton radioButtonNormal;
	private RadioButton radioButtonScan;
	private RadioButton radioButtonWifi;
	private RadioButton radioButtonBluetooth;
	private RadioButton radioButtonGPS;
	private RadioButton radioButtonNFC;
	private RadioButton radioButtonVoiceSet;
	private RadioButton radioButtonBrightSet;
	private TextView tvBack;
	private TextView tvTitle;
	private ImageView imgClick;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		android.os.SystemProperties.set("persist.sys.keyreport", "true");
		setContentView(R.layout.activity_home);
//		radioButtonNormal.setOnClickListener(this);
		radioButtonNormal = (RadioButton) findViewById(R.id.radio_normal_test);
		radioButtonScan = (RadioButton) findViewById(R.id.radio_scan_test);
		radioButtonWifi = (RadioButton) findViewById(R.id.radio_wifi);
		radioButtonBluetooth = (RadioButton) findViewById(R.id.radio_bluetooth);
		radioButtonGPS = (RadioButton) findViewById(R.id.radio_gps);
		radioButtonNFC = (RadioButton) findViewById(R.id.radio_home_nfc);
		radioButtonVoiceSet = (RadioButton) findViewById(R.id.radio_home_voice);
		radioButtonBrightSet = (RadioButton) findViewById(R.id.radio_home_bright);
		radioButtonNormal.setOnClickListener(this);
		radioButtonWifi.setOnClickListener(this);
		radioButtonScan.setOnClickListener(this);
		radioButtonBluetooth.setOnClickListener(this);
		radioButtonGPS.setOnClickListener(this);
		radioButtonNFC.setOnClickListener(this);
		radioButtonBrightSet.setOnClickListener(this);
		radioButtonVoiceSet.setOnClickListener(this);
		tvBack = getBackTextView();
		imgClick = getImgClick();
		tvBack.setOnClickListener(this);
		imgClick.setOnClickListener(this);
		tvBack.setVisibility(View.GONE);
		tvTitle = getBackTitle();
		
		mContext = this;
		
		UpdateVersion updateVersion = new UpdateVersion(this);
		try {
			tvTitle.setText(getString(R.string.home_title) + updateVersion.getVersionName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateVersion.startUpdate();
	}

	private ProgressDialog mProgressDialog;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == radioButtonNormal) {
			Intent intent = new Intent(this, NormalTestActivity.class);
			startActivity(intent);
		} else if (v == radioButtonWifi) {

			Intent intent = new Intent(this, WifiTest.class);
			startActivity(intent);

		} else if (v == radioButtonBluetooth) {
			Intent intent = new Intent(this, BluetoothTest.class);
			startActivity(intent);
			// Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
			// startActivity(intent);
		} else if (v == radioButtonScan) {
			
			Intent intent = new Intent(this, BarcodeTest.class);
			startActivity(intent);
		
		} else if (v == radioButtonGPS) {
			Intent intent = new Intent(this, GPSTest.class);
			startActivity(intent);
		} else if (v == radioButtonNFC) {
			Intent intent = new Intent(this, NFCard.class);
			startActivity(intent);
		} else if (v == radioButtonVoiceSet) {
			SetVoiceDialog dialog = new SetVoiceDialog(mContext);
			dialog.setTitle(getString(R.string.action_home_voice_set));
			dialog.show();
		} else if (v == radioButtonBrightSet) {
			SetBrightDialog brightDialog = new SetBrightDialog(mContext);
			brightDialog.setTitle(getString(R.string.action_home_bright_set));
			brightDialog.show();
		} else if (v == imgClick) {
			Intent intent = new Intent(this, AboutUs.class);
			startActivity(intent);
		}
	}

	private class SetVoiceDialog extends Dialog implements
			OnSeekBarChangeListener {

		Context mContext;
		private SeekBar barSpeaker;
		private SeekBar barMusic;
		private SeekBar barSong;
		int currentSpeaker;
		int currentMusic;
		int currentSong;
		AudioManager audioManager;

		public SetVoiceDialog(Context context) {
			super(context);
			mContext = context;
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_set_voice);
			barMusic = (SeekBar) findViewById(R.id.seekbar_music);
			barSpeaker = (SeekBar) findViewById(R.id.seekbar_speaker);
			barSong = (SeekBar) findViewById(R.id.seekbar_song);
			audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

			currentSpeaker = audioManager
					.getStreamVolume(AudioManager.STREAM_RING);
			currentMusic = audioManager
					.getStreamVolume(AudioManager.STREAM_ALARM);
			currentSong = audioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			barMusic.setProgress(currentMusic);
			barSpeaker.setProgress(currentSpeaker);
			barSong.setProgress(currentSong);
			System.out.println("----STREAM_ALARM" + currentMusic);
			System.out.println("----STREAM_RING" + currentSpeaker);
			System.out.println("----STREAM_MUSIC" + currentSong);
			int max = audioManager
					.getStreamMaxVolume(AudioManager.STREAM_ALARM);
			barMusic.setMax(max);
			max = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
			barSpeaker.setMax(max);
			max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			barSong.setMax(max);
			barMusic.setOnSeekBarChangeListener(this);
			barSpeaker.setOnSeekBarChangeListener(this);
			barSong.setOnSeekBarChangeListener(this);
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			currentSpeaker = audioManager
					.getStreamVolume(AudioManager.STREAM_RING);
			if (seekBar == barSpeaker) {
				int result = currentSpeaker - progress;
				if (result > 0) {
					for (int i = 0; i < result; i++) {
						audioManager.adjustStreamVolume(
								AudioManager.STREAM_RING,
								AudioManager.ADJUST_LOWER,
								AudioManager.FLAG_PLAY_SOUND);
						int current = audioManager
								.getStreamVolume(AudioManager.STREAM_RING);
						System.out.println("---progress_call:" + result
								+ " ---change_after=" + current);
					}
				} else {
					for (int i = 0; i > result; i--) {
						audioManager.adjustStreamVolume(
								AudioManager.STREAM_RING,
								AudioManager.ADJUST_RAISE,
								AudioManager.FLAG_PLAY_SOUND);
						int current = audioManager
								.getStreamVolume(AudioManager.STREAM_RING);
						int music = audioManager
								.getStreamVolume(AudioManager.STREAM_ALARM);
						System.out.println("---progress_call:" + progress
								+ " ---change_after=" + current + " music="
								+ music);

					}
				}
			} else if (seekBar == barMusic) {
				currentMusic = audioManager
						.getStreamVolume(AudioManager.STREAM_ALARM);
				int result = currentMusic - progress;
				System.out.println("---progress_music_result:" + result);
				if (result > 0) {
					for (int i = 0; i < result; i++) {
						audioManager.adjustStreamVolume(
								AudioManager.STREAM_ALARM,
								AudioManager.ADJUST_LOWER,
								AudioManager.FLAG_PLAY_SOUND);
						int current = audioManager
								.getStreamVolume(AudioManager.STREAM_ALARM);
						System.out.println("---progress_music:" + i
								+ " ---change_after=" + current);
					}
				} else {
					for (int i = 0; i > result; i--) {
						audioManager.adjustStreamVolume(
								AudioManager.STREAM_ALARM,
								AudioManager.ADJUST_RAISE,
								AudioManager.FLAG_PLAY_SOUND);
						int current = audioManager
								.getStreamVolume(AudioManager.STREAM_ALARM);
						System.out.println("---progress_music:" + progress
								+ " ---change_after=" + current);
					}

				}

			} else if (seekBar == barSong) {
				currentMusic = audioManager
						.getStreamVolume(AudioManager.STREAM_MUSIC);
				int result = currentMusic - progress;
				System.out.println("---progress_song_result:" + result);
				if (result > 0) {
					for (int i = 0; i < result; i++) {
						audioManager.adjustStreamVolume(
								AudioManager.STREAM_MUSIC,
								AudioManager.ADJUST_LOWER,
								AudioManager.FLAG_PLAY_SOUND);
						int current = audioManager
								.getStreamVolume(AudioManager.STREAM_MUSIC);
						System.out.println("---progress_song:" + i
								+ " ---change_after=" + current);
					}
				} else {
					for (int i = 0; i > result; i--) {
						audioManager.adjustStreamVolume(
								AudioManager.STREAM_MUSIC,
								AudioManager.ADJUST_RAISE,
								AudioManager.FLAG_PLAY_SOUND);
						int current = audioManager
								.getStreamVolume(AudioManager.STREAM_MUSIC);
						System.out.println("---progress_song:" + progress
								+ " ---change_after=" + current);
					}

				}

			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	}

	private class SetBrightDialog extends Dialog {

		private SeekBar bar;

		public SetBrightDialog(Context context) {
			super(context);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_set_bright);
			bar = (SeekBar) findViewById(R.id.seekbar_bright);
			bar.setMax(254);
			try {
				int bright = Settings.System.getInt(getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS);
				bar.setProgress(bright);
			} catch (SettingNotFoundException e) {
				e.printStackTrace();
			}
			bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					changeAppBrightness(mContext, progress);
				}
			});
		}

		// 根据亮度值修改当前window亮度
		public void changeAppBrightness(Context context, int brightness) {
			Window window = ((Activity) context).getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			if (brightness == -1) {
				lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
			} else {
				lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
			}
			window.setAttributes(lp);
			Settings.System.putInt(getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS, brightness);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		isExit();
		super.onDestroy();
	}

	private void isExit() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.action_exit_title)
				.setMessage(R.string.action_exit_content)
				.setPositiveButton(R.string.action_exit_sure,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
							}
						})
				.setNegativeButton(R.string.action_exit_cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// finish();
							}
						}).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isExit();
		}
		return false;
	}
}

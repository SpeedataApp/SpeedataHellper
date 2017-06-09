package com.speedata.kt40helper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.speedata.kt40helper_.R;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.

// ----------------------------------------------------------------------

public class CameraTest extends MyActivity implements OnClickListener {
	private final static int CAMERA_RESULT = 0;
	private ImageView mImageView;
	private Button enterCamera;
	// private Button failBtn;
	// private Button succBtn;
	private Button imgFlashlight;

	private TextView tvBack;
	private TextView tvTitle;
	private int cameraState = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_test);

		tvBack = getBackTextView();
		tvBack.setOnClickListener(this);
		tvTitle = getBackTitle();
		tvTitle.setText(getString(R.string.action_nornal_camera));
		mImageView = (ImageView) findViewById(R.id.imageview);
		enterCamera = (Button) findViewById(R.id.camera);
		imgFlashlight = (Button) findViewById(R.id.img_flashlight);
		enterCamera.setOnClickListener(this);
		imgFlashlight.setOnClickListener(this);
		imgFlashlight.setVisibility(View.GONE);
		// cameraState = initCamera();
		// failBtn = (Button) findViewById(R.id.fail);
		// failBtn.setOnClickListener(this);
		// succBtn = (Button) findViewById(R.id.success);
		// succBtn.setOnClickListener(this);
		// initCamera();
		// releaseCamera();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			Bitmap bitmap = (Bitmap) bundle.get("data");
			mImageView.setImageBitmap(bitmap);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private boolean flashlight_on = false;
	private Camera camera;

	private int initCamera() {
		// TODO Auto-generated method stub
		try {
			camera = Camera.open();
			System.out.println("initCamera");
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "手电筒准备失败", Toast.LENGTH_SHORT).show();
			return -1;
		}

	}

	public void onClick(View v) {
		if (v == enterCamera) {
			if (camera != null) {
				releaseCamera();
				cameraState = -1;

				imgFlashlight.setText(getResources().getString(
						R.string.camera_flash));
			}
			Intent i = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);// 启动intent
			startActivityForResult(i, CAMERA_RESULT);
		} else if (v == tvBack) {
			finish();
		} else if (v == imgFlashlight) {
			if (cameraState < 0) {
				cameraState = initCamera();
				if (cameraState < 0) {
					Toast.makeText(CameraTest.this, "init falsh init failed",
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
			if (flashlight_on) {
				// 关闭
				imgFlashlight.setText(getResources().getString(
						R.string.camera_flash));
				camera.startPreview();
				Parameters mParameters = camera.getParameters();
				mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				camera.setParameters(mParameters);
				flashlight_on = false;
			} else {
				// 打开
				flashlight_on = true;
				imgFlashlight.setText(getResources().getString(
						R.string.camera_flash_close));
				camera.startPreview();
				Parameters mParameters = camera.getParameters();
				mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				camera.setParameters(mParameters);
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (camera != null) {
			releaseCamera();
		}
	}

	private void releaseCamera() {
		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;
	}
}

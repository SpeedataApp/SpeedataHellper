package com.speedata.kt40helper;

import com.speedata.kt40helper_.R;

import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TestMicActivity extends MyActivity implements OnClickListener{
	
	private AudioManager mAudioManager = null;
	private TextView tvBack;
	private TextView tvTitle;
	private boolean running = false;
	private TextView stateView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_mic);
		
		stateView = (TextView) this.findViewById(R.id.view_state_mic);
		tvBack = getBackTextView();
		tvTitle = getBackTitle();
		tvTitle.setText(getResources().getString(R.string.action_test_mic));
		tvBack.setOnClickListener(this);
		this.mAudioManager = ((AudioManager)getSystemService("audio"));
	}
 
	@Override
	protected void onResume() {
		Log.v("here", "test");
		super.onResume();
	    this.running = true;
	    new Thread()  {
	      public void run()  {
	        try   {
	          Thread.sleep(1000L);
	           if (TestMicActivity.this.running)
	          {
	           TestMicActivity.this.mAudioManager.setParameters("SET_LOOPBACK_TYPE=25");
	          }
	          return;
	        }
	        catch (InterruptedException localInterruptedException)
	        {
	        }
	      }
	    }
	    .start();
	}
		

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.tv_back:
				finish();
				break;
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	    this.running = false;
	    this.mAudioManager.setParameters("SET_LOOPBACK_TYPE=0");
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.running = false;
	    this.mAudioManager.setParameters("SET_LOOPBACK_TYPE=0");
		
	}

	

}

package com.speedata.dialog;

import android.app.Dialog;
import android.content.Context;

import com.speedata.kt40helper_.R;

public class BarcodeSetDialog extends Dialog {

	public BarcodeSetDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}//implements
//		android.view.View.OnClickListener {
//	BarcodeTest activity;
//	boolean[] state;
//	boolean isSe4500;
//	SerialPortBarcode mSerialPortBarcode;
//
//	public BarcodeSetDialog(Context context, BarcodeTest activity, boolean[] state,
//			boolean isSe4500) {
//		super(context);
//		mContext = context;
//		this.activity = activity;
//		this.state = state;
//		this.isSe4500 = isSe4500;
//	}
//
//	public BarcodeSetDialog(Context context, BarcodeTest activity, boolean[] state,
//			boolean isSe4500, SerialPortBarcode mSerialPortBarcode) {
//		super(context);
//		mContext = context;
//		this.activity = activity;
//		this.state = state;
//		this.isSe4500 = isSe4500;
//		this.mSerialPortBarcode = mSerialPortBarcode;
//	}
//
//	ListViewCheckBoxAdapter listAdapter;
//	private ListView listView;
//	private Context mContext;
//	private Button bvSet;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dialog_set_barcode_parameters);
//		listView = (ListView) findViewById(R.id.lv_barcode_type);
//		bvSet = (Button) findViewById(R.id.bv_set);
//		bvSet.setOnClickListener(this);
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//			}
//		});
//		for (int i = 0; i < state.length; i++) {
//			System.out.println("result dialog" + state[i]);
//		}
//		if (isSe4500) {
//			listAdapter = new ListViewCheckBoxAdapter(mContext, new String[] {
//					// "EAN8", "EAN13", "UPCA", "UPCE", "UPCE1", "CODE39",
//					"UPCA", "UPCE", "UPCE1", "CODE39", "CODE128", "CODE93" },
//					state);
//		} else {
//			listAdapter = new ListViewCheckBoxAdapter(mContext, new String[] {
//					"EAN8", "EAN13", "UPCA", "UPCE", "UPCE1" }, state);
//		}
//
//		listView.setAdapter(listAdapter);
//		listView.requestFocus();
//	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		if (v == bvSet) {
//			int parmeterNum = 0;
//			boolean[] result = listAdapter.getState();
//			for (int i = 0; i < result.length; i++) {
//				System.out.println("result " + result[i]);
//			}
//			for (int i = 0; i < result.length; i++) {
//				if (isSe4500) {
//					parmeterNum = Contancts.getParmater(i);
//					int flog_set = 0;
//					ProgressDialog mProgressDialog = new ProgressDialog(
//							mContext);
//					mProgressDialog.setTitle(R.string.remind_setting);
//					mProgressDialog.show();
//					if (result[i]) {
//						flog_set = activity.doSetParam(parmeterNum, 1);
//						System.out.println("result set true " + i
//								+ "   parmeterNum =" + parmeterNum);
//						// if (flog_set != BarCodeReader.BCR_ERROR) {
//						// Toast.makeText(mContext,
//						// listAdapter.getItem(i) + " set success",
//						// Toast.LENGTH_SHORT).show();
//						// } else {
//						// Toast.makeText(mContext,
//						// listAdapter.getItem(i) + " set failed",
//						// Toast.LENGTH_SHORT).show();
//						// }
//					} else {
//						flog_set = activity.doSetParam(parmeterNum, 0);
//						System.out.println("result set false " + i
//								+ "   parmeterNum =" + parmeterNum);
//					}
//					mProgressDialog.cancel();
//					dismiss();
//				} else {
//					// 串口操作
//					// byte[] cmd = new byte[2];
//					// if (result[i]) {
//					// cmd[0] = Contancts.getSe955Parmater(i);
//					// cmd[1] = 0x01;
//					// mSerialPortBarcode.sendCmd(cmd);
//					// } else {
//					// cmd[0] = Contancts.getSe955Parmater(i);
//					// cmd[1] = 0x01;
//					// mSerialPortBarcode.sendCmd(cmd);
//					// }
//				}
//			}
//			// mSerialPortBarcode.sendCmd(new byte[]{});
//		}
//	}
}
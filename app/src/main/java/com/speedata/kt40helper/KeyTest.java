package com.speedata.kt40helper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.speedata.kt40helper_.R;

public class KeyTest extends MyActivity implements OnClickListener {
	Button bvKeyPower;
	Button bvKeyUP;
	Button bvKeyDown;
	Button bvKeyOK;
	Button bvKeyBack;
	Button bvKeyMenu;
	Button bvKeyDelete;
	Button bvKeyScan;
	Button bvKeyF1;
	Button bvKeyF2;
	Button bvKeyF3;
	Button bvKey1;
	Button bvKey2;
	Button bvKey3;
	Button bvKey4;
	Button bvKey5;
	Button bvKey6;
	Button bvKey7;
	Button bvKey8;
	Button bvKey9;
	Button bvKey0;
	Button bvKeyxing;
	Button bvKeyjing;

	TextView tvBack;
	TextView tvTitle;

	int back_count = 0;

	private EditText edvInput;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.key_test);
		tvBack = (TextView) findViewById(R.id.tv_back);
		tvBack.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText(getString(R.string.action_nornal_key));
		bvKeyBack = (Button) findViewById(R.id.bv_key_back);
		bvKeyMenu = (Button) findViewById(R.id.bv_key_menu);
		bvKeyDown = (Button) findViewById(R.id.bv_key_down);
		bvKeyUP = (Button) findViewById(R.id.bv_key_up);
		bvKeyF2 = (Button) findViewById(R.id.bv_key_f2);
		bvKeyF1 = (Button) findViewById(R.id.bv_key_f1);
		bvKeyF3 = (Button) findViewById(R.id.bv_key_f3);
		bvKeyDelete = (Button) findViewById(R.id.bv_key_delete);
		bvKey0 = (Button) findViewById(R.id.bv_key_0);
		bvKey1 = (Button) findViewById(R.id.bv_key_1);
		bvKey2 = (Button) findViewById(R.id.bv_key_2);
		bvKey3 = (Button) findViewById(R.id.bv_key_3);
		bvKey4 = (Button) findViewById(R.id.bv_key_4);
		bvKey5 = (Button) findViewById(R.id.bv_key_5);
		bvKey6 = (Button) findViewById(R.id.bv_key_6);
		bvKey7 = (Button) findViewById(R.id.bv_key_7);
		bvKey8 = (Button) findViewById(R.id.bv_key_8);
		bvKey9 = (Button) findViewById(R.id.bv_key_9);
		bvKeyxing = (Button) findViewById(R.id.bv_key_xing);
		bvKeyjing = (Button) findViewById(R.id.bv_key_jing);
		bvKeyOK = (Button) findViewById(R.id.bv_key_ok);
		edvInput=(EditText) findViewById(R.id.edv_key_input);
		bvKey0.setVisibility(View.GONE);
		bvKey1.setVisibility(View.GONE);
		bvKey2.setVisibility(View.GONE);
		bvKey3.setVisibility(View.GONE);
		bvKey4.setVisibility(View.GONE);
		bvKey5.setVisibility(View.GONE);
		bvKey6.setVisibility(View.GONE);
		bvKey7.setVisibility(View.GONE);
		bvKey8.setVisibility(View.GONE);
		bvKey9.setVisibility(View.GONE);
		bvKeyxing.setVisibility(View.GONE);
		bvKeyjing.setVisibility(View.GONE);
		// bvKeyPower = (Button) findViewById(R.id.bv_key_power);
		bvKeyScan = (Button) findViewById(R.id.bv_key_scan);
		
	}

	public void check_return() {
		if (bvKey0.isPressed() && bvKey1.isPressed() && bvKey2.isPressed()
				&& bvKey3.isPressed() && bvKey4.isPressed()
				&& bvKey5.isPressed() && bvKey6.isPressed()
				&& bvKey7.isPressed() && bvKey8.isPressed()
				&& bvKey9.isPressed() && bvKeyBack.isPressed()
				&& bvKeyDelete.isPressed() && bvKeyDown.isPressed()
				&& bvKeyF1.isPressed() && bvKeyF2.isPressed()
				&& bvKeyF3.isPressed() && bvKeyjing.isPressed()
				&& bvKeyMenu.isPressed() && bvKeyScan.isPressed()
				&& bvKeyUP.isPressed() && bvKeyOK.isPressed()
				&& bvKeyxing.isPressed()) {
			Log.v("here", "all pressed");
			new AlertDialog.Builder(this)
					.setTitle(R.string.key_test_back_title)
					.setMessage(R.string.key_test_back_content)
					.setPositiveButton(R.string.key_test_back_sure,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									finish();
								}
							})
					.setNegativeButton(R.string.key_test_back_cancel,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

								}
							}).show();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println("----keyCode:" + keyCode);
		if (29 <= keyCode && keyCode <= 51) {
			// F切换
			// bvKeyF1.setBackgroundColor(KeyTest.this.getResources()
			// .getColor(R.color.red));
			// bvKeyF1.setText("F1");
			bvKeyF1.setPressed(true);
		}
		switch (keyCode) {

		case KeyEvent.KEYCODE_F1:
			if (bvKeyF1.isPressed()) {
				bvKeyF1.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyF1.setText("F1");
				bvKeyF1.setPressed(false);
				break;
			} else {
				bvKeyF1.setText("F1 | " + KeyEvent.KEYCODE_F1);
				bvKeyF1.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyF1.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_F2:
			if (bvKeyF2.isPressed()) {
				bvKeyF2.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyF2.setText("F2");
				bvKeyF2.setPressed(false);
				break;
			} else {
				bvKeyF2.setText("F2 | " + KeyEvent.KEYCODE_F2);
				bvKeyF2.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyF2.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_F3:
			if (bvKeyF3.isPressed()) {
				bvKeyF3.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyF3.setText("F3");
				bvKeyF3.setPressed(false);
				break;
			} else {
				bvKeyF3.setText("F3 | " + KeyEvent.KEYCODE_F3);
				bvKeyF3.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyF3.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}

		case KeyEvent.KEYCODE_BACK:
			// if (back_count == 1) {
			// new AlertDialog.Builder(this)
			// .setTitle(R.string.key_test_back_title)
			// .setMessage(R.string.key_test_back_content)
			// .setPositiveButton(R.string.key_test_back_sure,
			// new DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog,
			// int which) {
			// // TODO Auto-generated method stub
			// finish();
			// }
			// })
			// .setNegativeButton(R.string.key_test_back_cancel,
			// new DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog,
			// int which) {
			// // TODO Auto-generated method stub
			//
			// }
			// }).show();
			// }
			if (bvKeyBack.isPressed()) {
				bvKeyBack.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyBack.setText("back");
				bvKeyBack.setPressed(false);
				break;
			} else {
				bvKeyBack.setText("back | " + KeyEvent.KEYCODE_BACK);
				bvKeyBack.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyBack.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}

		case KeyEvent.KEYCODE_SPACE:
			bvKey0.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));
			bvKey0.setText("space | " + KeyEvent.KEYCODE_SPACE);
			break;
		case KeyEvent.KEYCODE_0:
			if (bvKey0.isPressed()) {
				bvKey0.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.red));
				bvKey0.setText("0");
				bvKey0.setPressed(false);
				break;
			} else {
				bvKey0.setText("0 | " + KeyEvent.KEYCODE_0);
				bvKey0.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.green));
				bvKey0.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_1:
			if (bvKey1.isPressed()) {
				bvKey1.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.red));
				bvKey1.setText("1");
				bvKey1.setPressed(false);
				break;
			} else {
				bvKey1.setText("1 | " + KeyEvent.KEYCODE_1);
				bvKey1.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.green));
				bvKey1.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_COMMA:
			bvKey1.setText("， | " + KeyEvent.KEYCODE_COMMA);
			bvKey1.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));
			break;
		case KeyEvent.KEYCODE_2:
			if (bvKey2.isPressed()) {
				bvKey2.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.red));
				bvKey2.setText("2");
				bvKey2.setPressed(false);
				break;
			} else {
				bvKey2.setText("2 | " + KeyEvent.KEYCODE_2);
				bvKey2.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.green));
				bvKey2.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_B:
			bvKey2.setText("B | " + KeyEvent.KEYCODE_B);
			bvKey2.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));
			break;
		case KeyEvent.KEYCODE_A:
			bvKey2.setText("A | " + KeyEvent.KEYCODE_A);
			bvKey2.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.text_blue));
			break;
		case KeyEvent.KEYCODE_C:
			bvKey2.setText("C | " + KeyEvent.KEYCODE_C);
			bvKey2.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.deep_gray));
			break;

		case KeyEvent.KEYCODE_3:
			if (bvKey3.isPressed()) {
				bvKey3.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.red));
				bvKey3.setText("3");
				bvKey3.setPressed(false);
				break;
			} else {
				bvKey3.setText("3 | " + KeyEvent.KEYCODE_3);
				bvKey3.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.green));
				bvKey3.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_D:
			bvKey3.setText("D | " + KeyEvent.KEYCODE_D);
			bvKey3.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));
			break;
		case KeyEvent.KEYCODE_E:
			bvKey3.setText("E | " + KeyEvent.KEYCODE_E);
			bvKey3.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.text_blue));
			break;
		case KeyEvent.KEYCODE_F:
			bvKey3.setText("F | " + KeyEvent.KEYCODE_F);
			bvKey3.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.deep_gray));
			break;
		case KeyEvent.KEYCODE_4:
			if (bvKey4.isPressed()) {
				bvKey4.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.red));
				bvKey4.setText("4");
				bvKey4.setPressed(false);
				break;
			} else {
				bvKey4.setText("4 | " + KeyEvent.KEYCODE_4);
				bvKey4.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.green));
				bvKey4.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}

		case KeyEvent.KEYCODE_G:
			bvKey4.setText("G | " + KeyEvent.KEYCODE_G);
			bvKey4.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));
			break;
		case KeyEvent.KEYCODE_H:
			bvKey4.setText("H | " + KeyEvent.KEYCODE_H);
			bvKey4.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.text_blue));
		case KeyEvent.KEYCODE_I:
			bvKey4.setText("I | " + KeyEvent.KEYCODE_I);
			bvKey4.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.deep_gray));
			break;
		case KeyEvent.KEYCODE_5:
			if (bvKey5.isPressed()) {
				bvKey5.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.red));
				bvKey5.setText("5");
				bvKey5.setPressed(false);
				break;
			} else {
				bvKey5.setText("5 | " + KeyEvent.KEYCODE_5);
				bvKey5.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.green));
				bvKey5.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_J:
			bvKey5.setText("J | " + KeyEvent.KEYCODE_J);
			bvKey5.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));
			break;
		case KeyEvent.KEYCODE_K:
			bvKey5.setText("K | " + KeyEvent.KEYCODE_K);
			bvKey5.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.text_blue));
		case KeyEvent.KEYCODE_L:
			bvKey5.setText("L | " + KeyEvent.KEYCODE_L);
			bvKey5.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.deep_gray));
			break;
		case KeyEvent.KEYCODE_6:
			if (bvKey6.isPressed()) {
				bvKey6.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.red));
				bvKey6.setText("6");
				bvKey6.setPressed(false);
				break;
			} else {
				bvKey6.setText("6 | " + KeyEvent.KEYCODE_6);
				bvKey6.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.green));
				bvKey6.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_M:
			bvKey6.setText("M | " + KeyEvent.KEYCODE_M);
			bvKey6.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));
			break;
		case KeyEvent.KEYCODE_N:
			bvKey6.setText("N | " + KeyEvent.KEYCODE_N);
			bvKey6.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.text_blue));
		case KeyEvent.KEYCODE_O:
			bvKey6.setText("O | " + KeyEvent.KEYCODE_O);
			bvKey6.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.deep_gray));
			break;
		case KeyEvent.KEYCODE_7:
			if (bvKey7.isPressed()) {
				bvKey7.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.red));
				bvKey7.setText("7");
				bvKey7.setPressed(false);
				break;
			} else {
				bvKey7.setText("7 | " + KeyEvent.KEYCODE_7);
				bvKey7.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.green));
				bvKey7.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_P:
			bvKey7.setText("P | " + KeyEvent.KEYCODE_P);
			bvKey7.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));
			break;
		case KeyEvent.KEYCODE_Q:
			bvKey7.setText("Q | " + KeyEvent.KEYCODE_Q);
			bvKey7.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.text_blue));
		case KeyEvent.KEYCODE_R:
			bvKey7.setText("R | " + KeyEvent.KEYCODE_R);
			bvKey7.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.deep_gray));
			break;
		case KeyEvent.KEYCODE_S:
			bvKey7.setText("S | " + KeyEvent.KEYCODE_S);
			bvKey7.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));
			break;
		case KeyEvent.KEYCODE_8:
			if (bvKey8.isPressed()) {
				bvKey8.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.red));
				bvKey8.setText("8");
				bvKey8.setPressed(false);
				break;
			} else {
				bvKey8.setText("8 | " + KeyEvent.KEYCODE_8);
				bvKey8.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.green));
				bvKey8.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_T:
			bvKey8.setText("T | " + KeyEvent.KEYCODE_T);
			bvKey8.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));
			break;
		case KeyEvent.KEYCODE_U:
			bvKey8.setText("U | " + KeyEvent.KEYCODE_U);
			bvKey8.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.text_blue));
		case KeyEvent.KEYCODE_V:
			bvKey8.setText("V | " + KeyEvent.KEYCODE_V);
			bvKey8.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.deep_gray));
			break;
		case KeyEvent.KEYCODE_9:
			if (bvKey9.isPressed()) {
				bvKey9.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.red));
				bvKey9.setText("9");
				bvKey9.setPressed(false);
				break;
			} else {
				bvKey9.setText("9 | " + KeyEvent.KEYCODE_9);
				bvKey9.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.green));
				bvKey9.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_W:
			bvKey9.setText("W | " + KeyEvent.KEYCODE_W);
			bvKey9.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));
			break;
		case KeyEvent.KEYCODE_X:
			bvKey9.setText("X | " + KeyEvent.KEYCODE_X);
			bvKey9.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.text_blue));
		case KeyEvent.KEYCODE_Y:
			bvKey9.setText("Y | " + KeyEvent.KEYCODE_Y);
			bvKey9.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.deep_gray));
			break;
		case KeyEvent.KEYCODE_Z:
			bvKey9.setText("Z | " + KeyEvent.KEYCODE_Z);
			bvKey9.setBackgroundColor(KeyTest.this.getResources().getColor(
					R.color.green));

			// case KeyEvent.KEYCODE_DPAD_CENTER:
			// case KeyEvent.KEYCODE_ENTER:
			// System.out.println("-----------------------ok"
			// + KeyEvent.KEYCODE_DPAD_CENTER + "   "
			// + KeyEvent.KEYCODE_ENTER);
			// if (bvKeyOK.isPressed()) {
			// bvKeyOK.setBackgroundColor(KeyTest.this.getResources()
			// .getColor(R.color.red));
			// bvKeyOK.setText("ok");
			// bvKeyOK.setPressed(false);
			// break;
			// } else {
			// bvKeyOK.setText("ok | " + KeyEvent.KEYCODE_ENTER);
			// bvKeyOK.setBackgroundColor(KeyTest.this.getResources()
			// .getColor(R.color.green));
			// bvKeyOK.setPressed(true);
			// Log.v("here", "pressed");
			// check_return();
			// break;
			// }

		case KeyEvent.KEYCODE_DPAD_DOWN:
			if (bvKeyDown.isPressed()) {
				bvKeyDown.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyDown.setText("down");
				bvKeyDown.setPressed(false);
				break;
			} else {
				bvKeyDown.setText("down | " + KeyEvent.KEYCODE_DPAD_DOWN);
				bvKeyDown.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyDown.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}

		case KeyEvent.KEYCODE_DPAD_UP:
			if (bvKeyUP.isPressed()) {
				bvKeyUP.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyUP.setText("up");
				bvKeyUP.setPressed(false);
				break;
			} else {
				bvKeyUP.setText("up | " + KeyEvent.KEYCODE_DPAD_UP);
				bvKeyUP.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyUP.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}

		case KeyEvent.KEYCODE_DEL:
//			if (bvKeyF1.isPressed()) {
//				break;
//			}
			if (bvKeyDelete.isPressed()) {
				bvKeyDelete.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyDelete.setText("del");
				bvKeyDelete.setPressed(false);
				break;
			} else {
				bvKeyDelete.setText("del| " + KeyEvent.KEYCODE_DEL);
				bvKeyDelete.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyDelete.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}

		case KeyEvent.KEYCODE_POWER:
			if (bvKeyPower.isPressed()) {
				bvKeyPower.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyPower.setText("power");
				bvKeyPower.setPressed(false);
				break;
			} else {
				bvKeyPower.setText("power | " + KeyEvent.KEYCODE_POWER);
				bvKey9.setBackgroundColor(KeyTest.this.getResources().getColor(
						R.color.green));
				bvKeyPower.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}

		case KeyEvent.KEYCODE_MENU:
			if (bvKeyMenu.isPressed()) {
				bvKeyMenu.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyMenu.setText("menu");
				bvKeyMenu.setPressed(false);
				break;
			} else {
				bvKeyMenu.setText("menu | " + KeyEvent.KEYCODE_MENU);
				bvKeyMenu.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyMenu.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}

		case KeyEvent.KEYCODE_F4:
			if (bvKeyScan.isPressed()) {
				bvKeyScan.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyScan.setText("scan");
				bvKeyScan.setPressed(false);
				break;
			} else {
				bvKeyScan.setText("scan | " + KeyEvent.KEYCODE_F4);
				bvKeyScan.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyScan.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}
		case KeyEvent.KEYCODE_MINUS:
			if (bvKeyjing.isPressed()) {
				bvKeyjing.setText("- | " + KeyEvent.KEYCODE_MINUS);
				bvKeyjing.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyjing.setPressed(false);
			} else {
				bvKeyjing.setText("- | " + KeyEvent.KEYCODE_MINUS);
				bvKeyjing.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyjing.setPressed(true);
			}
			break;
		case KeyEvent.KEYCODE_PERIOD:
			if (bvKeyxing.isPressed()) {
				bvKeyxing.setText("* ");
				bvKeyxing.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyxing.setPressed(false);
			} else {
				bvKeyxing.setPressed(true);
				bvKeyxing.setText(". | " + KeyEvent.KEYCODE_PERIOD);
				bvKeyxing.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyxing.setPressed(true);
			}

			break;
		case KeyEvent.KEYCODE_SHIFT_LEFT:
			if (bvKeyxing.isPressed()) {
				bvKeyxing.setText("* ");
				bvKeyxing.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyxing.setPressed(false);
			} else {
				bvKeyxing.setPressed(true);
				bvKeyxing.setText("* | " + KeyEvent.KEYCODE_SHIFT_LEFT);
				bvKeyxing.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyxing.setPressed(true);
			}
			break;
		case 17:
			if (bvKeyxing.isPressed()) {
				bvKeyxing.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyxing.setText("*");
				bvKeyxing.setPressed(false);
				break;
			} else {
				bvKeyxing.setText("* | " + 17);
				bvKeyxing.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyxing.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}

		case 18:
			if (bvKeyjing.isPressed()) {
				bvKeyjing.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyjing.setText("#");
				bvKeyjing.setPressed(false);
				break;
			} else {
				bvKeyjing.setText("# | " + 18);
				bvKeyjing.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyjing.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}

		default:
			break;
		}
		return false;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println("onKeyUp----keyCode");
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_CENTER:
		case KeyEvent.KEYCODE_ENTER:
			System.out.println("-----------------------ok"
					+ KeyEvent.KEYCODE_DPAD_CENTER + "   "
					+ KeyEvent.KEYCODE_ENTER);
			if (bvKeyOK.isPressed()) {
				bvKeyOK.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.red));
				bvKeyOK.setText("ok");
				bvKeyOK.setPressed(false);
				break;
			} else {
				bvKeyOK.setText("ok | " + KeyEvent.KEYCODE_ENTER);
				bvKeyOK.setBackgroundColor(KeyTest.this.getResources()
						.getColor(R.color.green));
				bvKeyOK.setPressed(true);
				Log.v("here", "pressed");
				check_return();
				break;
			}

		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == tvBack) {
			finish();
		}
	}
}
package android.serialport;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.widget.Toast;

public class SerialPortBarcode {
	private static final String LOG_TAG = "SerialPortBarcode";
	SerialPortBarcodeInterface barcodeInterface;
	String SerialPath, PowerPath, data;
	private SerialPort mSerialPort;
	private DeviceControl mDeviceControl;
	Context mContext;
	int fd = -1, brd, readCount = 0;
	boolean single = true, debug = false;// debug疲劳测试写文件

	public SerialPortBarcode(SerialPortBarcodeInterface barcodeInterface,
			String SerialPath, int brd, String PowerPath, Context mContext) {
		super();
		this.barcodeInterface = barcodeInterface;
		this.SerialPath = SerialPath;
		this.PowerPath = PowerPath;
		this.mContext = mContext;
		this.brd = brd;
		// initSE955();
	}

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String initSE955() {
		String result = "";
		mSerialPort = new SerialPort();
		int temp = mSerialPort.OpenSerial(SerialPath, brd);
		mDeviceControl = new DeviceControl(PowerPath);
		mDeviceControl.PowerOnDevice();
		if (temp < 0) {
			Toast.makeText(mContext, "open " + SerialPath + " failed",
					Toast.LENGTH_SHORT).show();
			result = "init failed";
		} else {
			result = "init success";
		}
		fd = mSerialPort.getFd();
		return result;
	}

	private Timer timer = new Timer();
	private Timer closetimer = new Timer();

	public void singleRead() {
		mDeviceControl.TriggerOnDevice();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		// if (timer == null) {
		// timer = new Timer();
		// }
		timer = new Timer();
		readCount = 0;
		timer.schedule(new ReadTask(), 100, 300);
		if (closetimer != null) {
			closetimer.cancel();
			closetimer = null;
		}
		closetimer = new Timer();
		// if (closetimer == null) {
		// closetimer = new Timer();
		// }
		closetimer.schedule(new CloseTask(), 3000);
	}

	private class CloseTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			mDeviceControl.TriggerOffDevice();
			if (timer != null) {
				timer.cancel();
				timer = null;
			}

			// System.out.println("close trrigel");
			barcodeInterface.onSerialDecodeFinish("");
		}

	}

	private class ReadTask extends TimerTask {
		@Override
		public void run() {
			String readata = mSerialPort.ReadSerialString(fd, 1024);
			if (readata != null) {
				// System.out.println("close getdata");
				if (timer != null) {
					timer.cancel();
					timer = null;
				}
				if (closetimer != null) {
					closetimer.cancel();
					closetimer = null;
				}

				readCount = 0;
				barcodeInterface.onSerialDecodeFinish(readata);
			}
			// else {
			// readCount++;
			// // System.out.println("readCount" + readCount);
			// if (readCount > 10) {
			// readCount = 0;
			//
			// readCount = 0;
			// }
			// }
		}
	}

	public void CloseSE955() {
		mDeviceControl.PowerOffDevice();
		mSerialPort.CloseSerial(fd);
	}

	public void sendCmd(String cmd) {
		mSerialPort.WriteSerialString(fd, cmd, cmd.length());
		System.out.println("send_cmd=" + cmd);
	}

	public void sendCmd(byte[] cmd) {
		String ss = "1000010";

		byte[] temp = { 0x06, (byte) 0xc6, 0x00, 0x00, 0x01, 0x00 };
		byte[] send = new byte[8];
		byte[] sum = getCheckBit(CheckSum(temp, 6));
		System.arraycopy(temp, 0, send, 0, temp.length);
		System.arraycopy(sum, 0, send, temp.length, sum.length);
		mSerialPort.WriteSerialByte(fd, send);
		System.out.println("send_cmd=" + cmd);
	}

	private int CheckSum(byte[] data, int len) {
		int sum = 0;
		if (data == null || len <= 0) {
			return 0;
		}
		while (len-- > 0) {
			sum += (data[len] & 0xff);
		}
		return ((~sum) + 1);
	}

	private byte[] getCheckBit(int sum) {
		// 0-h 1-l
		byte[] result = new byte[2];
		result[0] = (byte) ((sum >> 8) & 0xff);
		result[1] = (byte) ((sum) & 0xff);
		System.out.println("MSB:" + result[0] + "LSB:" + result[1]);
		return result;
	}
}

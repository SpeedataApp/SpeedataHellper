package cn.geomobile.tda8029;

import android.util.Log;

public class serial_native {
	
	private int fd;
	private int delay = 200;
	private static final String TAG = "serial_native";
	
	public int OpenComPort(String device) 
	{
		fd = open_serial(device);
		if (fd < 0) {
			Log.e(TAG, "native open returns null");
			return -1;
		}
		return 0;
	}
	
	public void CloseComPort()
	{
		close_serial(fd);
	}
	
	public byte[] ReadPort(int count)
	{
		return try_read(fd, count, delay);
	}
	
	public int WritePort(byte[] buf)
	{
		return try_write(fd, buf);
	}
	
	void ClearBuffer()
	{
		drop_data(fd);
	}
	
	private native int open_serial(String port);						//open serial port
	private native void close_serial(int fd);							//close serial port
	private native int try_write(int fd, byte[] buf);					//try to write byte array to serialport, may be failed
	private native byte[] try_read(int fd, int count, int delay);		//try to read number of count bytes, then return a byte array, which may be shorter than count
	private native void drop_data(int fd);								//clear the serialport read/write buffer
	
	static {
		System.loadLibrary("serial_native");
	}
}
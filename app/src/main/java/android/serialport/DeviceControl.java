package android.serialport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeviceControl {
	private BufferedWriter CtrlFile;

	public DeviceControl(String path) {
		File DeviceName = new File(path);
		try {
			CtrlFile = new BufferedWriter(new FileWriter(DeviceName, false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // open
			// file
	}

	public void PowerOnDevice() // poweron barcode device
	{
		try {
			CtrlFile.write("on");
			CtrlFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void PowerOffDevice() // poweroff barcode device
	{
		try {
			CtrlFile.write("off");
			CtrlFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public void PowerOnDevice() //poweron lf device
	// {
	// try {
	// CtrlFile.write("-wdout64 1");
	// CtrlFile.flush();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	//
	// public void PowerOffDevice() //poweroff lf device
	// {
	// try {
	// CtrlFile.write("-wdout64 0");
	// CtrlFile.flush();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	public void TriggerOnDevice() // make barcode begin to
									// scan
	{
		try {
			CtrlFile.write("trig");
			CtrlFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void TriggerOffDevice() // make barcode stop scan
	{
		try {
			CtrlFile.write("trigoff");
			CtrlFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void DeviceClose() throws IOException // close file
	{
		CtrlFile.close();
	}
}
package cn.geomobile.tda8029;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeviceControl {
	private BufferedWriter CtrlFile;

	public DeviceControl(String path) throws IOException {
		File DeviceName = new File(path);
		CtrlFile = new BufferedWriter(new FileWriter(DeviceName, false)); // open
																			// file
	}

	public void PowerOnDevice() // poweron psam device
	{
		try {
			CtrlFile.write("-wdout48 1");
			CtrlFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void PowerOffDevice() // poweroff psam device
	{

		try {
			CtrlFile.write("-wdout48 0");
			CtrlFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void PsamResetDevice() throws IOException // reset psam device
	{
		CtrlFile.write("-wdout49 1");
		CtrlFile.flush();
		CtrlFile.write("-wdout49 0");
		CtrlFile.flush();
	}

	public void DeviceClose() throws IOException // close file
	{
		CtrlFile.close();
	}
}
package com.speedata.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.speedata.kt40helper_.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public class Contancts {
	private static final String FILENAME_PROC_VERSION = "/proc/version";
	private static final String LOG_TAG = "Contancts";
	public static final String VERSION = "V1.0.8";

	public static byte getSe955Parmater(int len) {
		byte parmeter = 0;
		String cmd = "";
		switch (len) {
		case 0:
			// ean8
			cmd = "000004";
			parmeter = 0x04;
			break;
		case 1:
			// ean13
			cmd = "000003";
			parmeter = 0x03;
			break;
		case 2:
			// upca
			cmd = "000001";
			parmeter = 0x01;
			break;
		case 3:
			// upce
			cmd = "000002";
			parmeter = 0x02;
			break;
		case 4:
			// upce1
			parmeter = 0x05;
			cmd = "000005";
			break;
		// case 5:
		// // code39
		// parmeterNum = 0;
		// break;
		// case 6:
		// // code128
		// parmeterNum = 8;
		// break;
		// case 7:
		// // code 93
		// parmeterNum = 9;
		// break;
		// case 8:
		//
		// break;
		// case 9:
		//
		// break;

		default:
			break;
		}
		return parmeter;
	}

	public static int getParmater(int len) {
		int parmeterNum = 0;
		switch (len) {
//		case 0:
//			// ean8
//			parmeterNum = 4;
//			break;
//		case 1:
//			// ean13
//			parmeterNum = 3;
//			break;
		case 0:
			// upca
			parmeterNum = 1;
			break;
		case 1:
			// upce
			parmeterNum = 2;
			break;
		case 2:
			// upce1
			parmeterNum = 12;
			break;
		case 3:
			// code39
			parmeterNum = 0;
			break;
		case 4:
			// code128
			parmeterNum = 8;
			break;
		case 5:
			// code 93
			parmeterNum = 9;
			break;
		case 8:

			break;
		case 9:

			break;

		default:
			break;
		}
		return parmeterNum;
	}

	private String getFormattedKernelVersion() {
		String procVersionStr;

		try {
			procVersionStr = readLine(FILENAME_PROC_VERSION);

			final String PROC_VERSION_REGEX = "\\w+\\s+" + /* ignore: Linux */
			"\\w+\\s+" + /* ignore: version */
			"([^\\s]+)\\s+" + /* group 1: 2.6.22-omap1 */
			"\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+" + /*
														 * group 2:
														 * (xxxxxx@xxxxx
														 * .constant)
														 */
			"\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+" + /* ignore: (gcc ..) */
			"([^\\s]+)\\s+" + /* group 3: #26 */
			"(?:PREEMPT\\s+)?" + /* ignore: PREEMPT (optional) */
			"(.+)"; /* group 4: date */

			Pattern p = Pattern.compile(PROC_VERSION_REGEX);
			Matcher m = p.matcher(procVersionStr);

			if (!m.matches()) {
				Log.e(LOG_TAG, "Regex did not match on /proc/version: "
						+ procVersionStr);
				return "Unavailable";
			} else if (m.groupCount() < 4) {
				Log.e(LOG_TAG, "Regex match on /proc/version only returned "
						+ m.groupCount() + " groups");
				return "Unavailable";
			} else {
				return (new StringBuilder(m.group(1)).append("\n")
						.append(m.group(2)).append(" ").append(m.group(3))
						.append("\n").append(m.group(4))).toString();
			}
		} catch (IOException e) {
			Log.e(LOG_TAG,
					"IO Exception when getting kernel version for Device Info screen",
					e);

			return "Unavailable";
		}
	}

	private String getBasedVersion() {
		String basedVersion = "";
		try {

			Class cl = Class.forName("android.os.SystemProperties");

			Object invoker = cl.newInstance();

			Method m = cl.getMethod("get", new Class[] { String.class,
					String.class });

			Object result = m.invoke(invoker, new Object[] {
					"gsm.version.baseband", "no message" });

			System.out.println("基带版本: " + (String) result);
			basedVersion = (String) result;

		} catch (Exception e) {

		}
		return basedVersion;
	}

	public static int getSystemRelease() {
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.RELEASE);
		} catch (NumberFormatException e) {
			// Log.e(e.toString());
		}
		return version;
	}

	/**
	 * Reads a line from the specified file.
	 * 
	 * @param filename
	 *            the file to read from
	 * @return the first line, if any.
	 * @throws IOException
	 *             if the file couldn't be read
	 */
	private String readLine(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename),
				256);
		try {
			return reader.readLine();
		} finally {
			reader.close();
		}
	}

}

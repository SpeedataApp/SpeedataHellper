package com.speedata.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class GetFormatDate {
	public static String getFormatDate() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		// int hour = Integer.valueOf(dateFormat.format(now));
		// "yyyy/MM/dd HH:mm:ss"
		String result = dateFormat.format(now);
		return result;
	}
	public static String getFormatDate(String format) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		// int hour = Integer.valueOf(dateFormat.format(now));
		// "yyyy/MM/dd HH:mm:ss"
		String result = dateFormat.format(now);
		return result;
	}
}

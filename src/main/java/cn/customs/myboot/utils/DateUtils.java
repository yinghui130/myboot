package cn.customs.myboot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class DateUtils {

	public static String getDateString(String format) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(d);
	}

	public static String getUUDIString()
	{
		UUID uuid = UUID.randomUUID();
		String a = uuid.toString();
		a = a.replaceAll("-", "");
		a = a.toUpperCase();
		return a;
	}
}

package com.wiser.library.util;

import android.annotation.SuppressLint;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Wiser
 * 
 *         日期
 */
@SuppressLint("SimpleDateFormat")
public class WISERDate {

	private static final TimeZone	timezone	= TimeZone.getTimeZone("GMT+8:00");

	public static final int			DATE_XG		= 1;								// yyyy/mm/dd

	public static final int			DATE_HG		= 2;								// yyyy-mm-dd

	public static final int			DATE_HZ		= 3;								// yyyy年mm月dd日

	/**
	 * 获取当前时间戳
	 *
	 * @return 时间戳
	 */
	public static Long getCurrentTimestamp() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取系统时间
	 *
	 * @param type
	 * @return
	 */
	public static String getCurrentDateStr(int type, boolean isShort) {
		SimpleDateFormat sdf;
		switch (type) {
			case DATE_XG:// yyyy/mm/dd
				if (isShort) sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
				break;
			case DATE_HG:// yyyy-mm-dd
				if (isShort) sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				break;
			case DATE_HZ:// yyyy年mm月dd日
				if (isShort) sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒", Locale.CHINA);
				break;
			default:
				if (isShort) sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				break;
		}
		sdf.setTimeZone(timezone);
		return sdf.format(new Date());
	}

	/**
	 * 获取现在事件 返回日期Date
	 *
	 * @return
	 */
	public static Date getCurrentDate(int type, boolean isShort) {
		Date currentTime = new Date();
		SimpleDateFormat sdf;
		switch (type) {
			case DATE_XG:// yyyy/mm/dd
				if (isShort) sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.CHINA);
				break;
			case DATE_HG:// yyyy-mm-dd
				if (isShort) sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
				break;
			case DATE_HZ:// yyyy年mm月dd日
				if (isShort) sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒", Locale.CHINA);
				break;
			default:
				if (isShort) sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
				break;
		}
		String dateString = sdf.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		sdf.setTimeZone(timezone);
		return sdf.parse(dateString, pos);
	}

	/**
	 * 获取时间格式转化
	 *
	 * @param dateStr
	 * @param pattern1
	 *            需要转化的模型
	 * @param pattern2
	 *            转化之后的模型
	 * @return pattern2
	 */
	public static String getDateCovert(String dateStr, String pattern1, String pattern2) {
		Date date;
		try {
			date = new SimpleDateFormat(pattern1).parse(dateStr);
			return new SimpleDateFormat(pattern2).format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 日期转换去掉时间
	 *
	 * @param dateStr
	 *            yyyy-MM-dd HH:mm:ss or yyyy/MM/dd HH:mm:ss
	 * @param type
	 * @return yyyy-MM-dd
	 */
	public static String getDateRemoveTime(String dateStr, int type) {
		Date date;
		try {
			switch (type) {
				case DATE_XG:// yyyy/mm/dd
					date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(dateStr);
					return new SimpleDateFormat("yyyy/MM/dd").format(date);
				case DATE_HG:// yyyy-mm-dd
					date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
					return new SimpleDateFormat("yyyy-MM-dd").format(date);
				case DATE_HZ:// yyyy年mm月dd日
					date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").parse(dateStr);
					return new SimpleDateFormat("yyyy年MM月dd日").format(date);
				default:
					date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
					return new SimpleDateFormat("yyyy-MM-dd").format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 日期转换成月日
	 *
	 * @param dateStr
	 *            yyyy-MM-dd hh:mm:ss
	 * @return yyyy-MM
	 */
	public static String getYearAndMonthDate(String dateStr, int type) {
		Date date;
		try {
			switch (type) {
				case DATE_XG:// yyyy/mm/dd
					date = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(dateStr);
					break;
				case DATE_HG:// yyyy-mm-dd
					date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dateStr);
					break;
				case DATE_HZ:// yyyy年mm月dd日
					date = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒").parse(dateStr);
					break;
				default:
					date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dateStr);
					break;
			}
			return new SimpleDateFormat("MM月dd日").format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到星期
	 *
	 * @param DateStr
	 * @return
	 */
	public static String getWeek(String DateStr, int type) {
		SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy-MM-dd");// formatYMD表示的是yyyy-MM-dd格式
		SimpleDateFormat formatD = new SimpleDateFormat("E");// "E"表示"day in week"
		Date d;
		String weekDay = "";
		try {
			d = formatYMD.parse(DateStr);// 将String 转换为符合格式的日期
			weekDay = formatD.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weekDay;
	}

	/**
	 * 获取两个日期之间的间隔天数
	 *
	 * @param startDate
	 *            yyyy-mm-dd
	 * @param endDate
	 *            yyyy-mm-dd
	 * @return
	 */
	public static int daysBetweenForDate(String startDate, String endDate, int type, boolean isShort) {
		if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
			return 0;
		}
		SimpleDateFormat sdf;
		switch (type) {
			case DATE_XG:// yyyy/mm/dd
				if (isShort) sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.CHINA);
				break;
			case DATE_HG:// yyyy-mm-dd
				if (isShort) sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
				break;
			case DATE_HZ:// yyyy年mm月dd日
				if (isShort) sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒", Locale.CHINA);
				break;
			default:
				if (isShort) sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
				break;
		}
		Date start = null;
		Date end = null;
		try {
			end = sdf.parse(endDate);
			start = sdf.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (start == null || end == null) return 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		long time1 = calendar.getTimeInMillis();

		calendar.setTime(end);
		long time2 = calendar.getTimeInMillis();

		long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(betweenDays));
	}

	/**
	 * 根据long类型转时间类型字符串
	 *
	 * @param mill
	 * @param type
	 * @return
	 */
	public static String getDateStrForLong(long mill, int type, boolean isShort) {
		Date date = new Date(mill);
		String dateStr = "";
		try {
			SimpleDateFormat sdf;
			switch (type) {
				case DATE_XG:// yyyy/mm/dd
					if (isShort) sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
					else sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
					break;
				case DATE_HG:// yyyy-mm-dd
					if (isShort) sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
					else sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
					break;
				case DATE_HZ:// yyyy年mm月dd日
					if (isShort) sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
					else sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒", Locale.CHINA);
					break;
				default:
					if (isShort) sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
					else sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
					break;
			}
			sdf.setTimeZone(timezone);
			// 进行格式化
			dateStr = sdf.format(date);
			return dateStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * 将字符串转为时间戳
	 *
	 * @param time
	 * @return
	 */
	public static long getLongForDateStr(String time, int type, boolean isShort) {
		SimpleDateFormat sdf;
		switch (type) {
			case DATE_XG:// yyyy/mm/dd
				if (isShort) sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
				break;
			case DATE_HG:// yyyy-mm-dd
				if (isShort) sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				break;
			case DATE_HZ:// yyyy年mm月dd日
				if (isShort) sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒", Locale.CHINA);
				break;
			default:
				if (isShort) sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				else sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				break;
		}
		sdf.setTimeZone(timezone);
		Date date = new Date();
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

}

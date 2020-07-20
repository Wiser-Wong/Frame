package com.wiser.library.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import android.annotation.SuppressLint;

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
	 * 根据当前日期获得星期
	 * 
	 * @param date
	 *            具体日期
	 * @param type
	 *            日期格式类型
	 * @param flag
	 *            (true 是星期几，false是周几)
	 * @return
	 */
	public static String getWeek(String date, int type, boolean flag) {
		String week = "";
		SimpleDateFormat format;
		switch (type) {
			case DATE_XG:// yyyy/mm/dd
				format = new SimpleDateFormat("yyyy/MM/dd");
				break;
			case DATE_HG:// yyyy-mm-dd
				format = new SimpleDateFormat("yyyy-MM-dd");
				break;
			case DATE_HZ:// yyyy年mm月dd日
				format = new SimpleDateFormat("yyyy年MM月dd日");
				break;
			default:
				format = new SimpleDateFormat("yyyy-MM-dd");
				break;
		}
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int wek = c.get(Calendar.DAY_OF_WEEK);
		String s;
		if (flag) {
			s = "星期";
		} else {
			s = "周";
		}
		if (wek == 1) {
			week += s + "日";
		}
		if (wek == 2) {
			week += s + "一";
		}
		if (wek == 3) {
			week += s + "二";
		}
		if (wek == 4) {
			week += s + "三";
		}
		if (wek == 5) {
			week += s + "四";
		}
		if (wek == 6) {
			week += s + "五";
		}
		if (wek == 7) {
			week += s + "六";
		}
		return week;
	}

	/**
	 * 获取星期几
	 * 
	 * @param flag
	 *            (true 是星期几，false是周几)
	 * @return
	 */
	public static String getCurrentWeek(boolean flag) {
		Calendar cal = Calendar.getInstance();
		int i = cal.get(Calendar.DAY_OF_WEEK);
		String s;
		if (flag) {
			s = "星期";
		} else {
			s = "周";
		}
		switch (i) {
			case 1:
				return s + "日";
			case 2:
				return s + "一";
			case 3:
				return s + "二";
			case 4:
				return s + "三";
			case 5:
				return s + "四";
			case 6:
				return s + "五";
			case 7:
				return s + "六";
			default:
				return "";
		}
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
	 * 获取两个日期之间的间隔天数
	 *
	 * @param start
	 *            long
	 * @param end
	 *            long
	 * @return
	 */
	public static int daysBetweenForDate(long start, int end) {
		long betweenDays = (end - start) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(betweenDays));
	}

	/**
	 * 获取两个日期之间的间隔天数
	 *
	 * @param limit
	 *            long 间隔的长度
	 * @return
	 */
	public static int daysBetweenForDate(long limit) {
		long betweenDays = limit / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(betweenDays));
	}

	/**
	 * 根据long类型转时间类型字符串
	 *
	 * @param mill
	 * @param type
	 * @param isShort
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
	 * 根据long类型获取时间字符串
	 *
	 * @param mill long时间
	 * @param pattern 模板
	 * @return
	 */
	public static String getTimes(long mill,String pattern) {
		Date date = new Date(mill);
		String dateStr = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
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
	 * 根据long类型转时分秒类型字符串
	 *
	 * @param mill
	 * @return
	 */
	public static String getHourMinuteSecondStrForLong(long mill) {
		Date date = new Date(mill);
		String dateStr = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
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
	 * 根据long类型转分秒类型字符串
	 *
	 * @param mill
	 * @return
	 */
	public static String getMinuteSecondStrForLong(long mill) {
		Date date = new Date(mill);
		String dateStr = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.CHINA);
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

	/**
	 * 日期比较
	 * 
	 * @param firstTime
	 * @param secondTime
	 * @return true firstTime<secondTime false firstTime>secondTime
	 */
	public static boolean compareTime(String firstTime, String secondTime, int type, boolean isShort) {
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
		try {
			Date sd1 = sdf.parse(firstTime);
			Date sd2 = sdf.parse(secondTime);
			if (sd1.after(sd2)) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 得到现在年
	 *
	 * @return
	 */
	public static String getCurrentYear() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(timezone);
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(0, 4);
		return hour;
	}

	/**
	 * 得到现在月
	 *
	 * @return
	 */
	public static String getCurrentMonth() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(timezone);
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(5, 7);
		return hour;
	}

	/**
	 * 得到现在日
	 *
	 * @return
	 */
	public static String getCurrentDay() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(timezone);
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(8, 10);
		return hour;
	}

	/**
	 * 得到现在小时
	 * 
	 * @return
	 */
	public static String getCurrentHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(timezone);
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 *
	 * @return
	 */
	public static String getCurrentMinute() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(timezone);
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 得到现在秒
	 *
	 * @return
	 */
	public static String getCurrentSecond() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(timezone);
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(17, 19);
		return min;
	}

}

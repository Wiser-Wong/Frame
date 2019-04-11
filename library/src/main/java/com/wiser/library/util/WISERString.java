package com.wiser.library.util;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERString {

	/**
	 * 获取小于10的数字转换成00格式
	 *
	 * @param count
	 * @return
	 */
	public static String convertInt(int count) {
		if (count < 10) {
			return "0" + count;
		}
		return count + "";
	}

	/**
	 * 截取字符串
	 *
	 * @param data
	 *            字符串
	 * @param isBefore
	 *            是截取指定字符串之前还是之后
	 * @param sign
	 *            指定字符串
	 * @return
	 */
	public static String cutSignString(String data, String sign, boolean isBefore) {
		int index = data.indexOf(sign);
		if (index == -1) return "";
		if (isBefore) {
			return data.substring(0, index);
		}
		return data.substring(index + 1, data.length());
	}

	/**
	 * 拼接上传多个字符串工具,以逗号分割
	 *
	 * @param list
	 * @return
	 */
	public static String appendAllStr(List<String> list) {
		if (list == null) return "";
		StringBuilder data = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				data.append(list.get(0));
				continue;
			}
			data.append(",").append(list.get(i));
		}
		return data.toString();
	}

	/**
	 * 去除字符串所有空格
	 * 
	 * @param s
	 * @return
	 */
	public static String trimAllSpace(String s) {
		String resultString;
		if (s == null) {
			resultString = "";
		} else {
			Pattern nullString = Pattern.compile(" ", Pattern.CASE_INSENSITIVE);
			resultString = nullString.matcher(s).replaceAll("");
		}
		return resultString;
	}

	/**
	 * 把手机号变为如139****1234格式
	 *
	 * @param phoneNum
	 * @return
	 */
	public static String shieldPhoneNum(String phoneNum) {
		if (!TextUtils.isEmpty(phoneNum) && phoneNum.length() == 11) {
			return (phoneNum.substring(0, 3) + "****" + phoneNum.substring(7));
		}
		return "";
	}

	/**
	 * 根据身份证判断是 性别
	 *
	 * @param idNUmber
	 * @return 男 女 默认返回男
	 */
	public static String returnSex(String idNUmber) {// 根据最后一位数字判断男女
		String lastNumber = "1";
		if (WISERCheck.isID(idNUmber)) {
			lastNumber = idNUmber.substring(idNUmber.length() - 2, idNUmber.length() - 1);
		}
		int lastNumberTwo = 1;
		if (WISERCheck.isNumber(lastNumber)) {
			lastNumberTwo = Integer.parseInt(lastNumber);
		}
		if (0 == lastNumberTwo % 2) {
			return "女";
		} else {
			return "男";
		}
	}

	/**
	 * 根据身份证号码获取出生年月日
	 * 
	 * @param idNumber
	 * @return 1989-09-05
	 */
	public static String returnBirthdayByID(String idNumber) {
		String birthday = "";
		if (WISERCheck.isID(idNumber)) {
			if (idNumber.length() == 18) {
				birthday = idNumber.substring(6, 10) + "-" + idNumber.substring(10, 12) + "-" + idNumber.substring(12, 14);
			} else {
				if (idNumber.length() == 15) {
					birthday = "19" + idNumber.substring(6, 8) + "-" + idNumber.substring(8, 10) + "-" + idNumber.substring(10, 12);
				}
			}
		}
		return birthday;
	}

	/**
	 * 分割时分秒hh:mm:ss
	 *
	 * @param time
	 * @param type
	 * @return
	 */
	public static String splitColon(String time, int type) {
		if (WISERCheck.isEmpty(time)) return "00";
		String[] times;
		String returnTime = "00";
		if (time.contains(":")) {
			times = time.split(":");
			if (times.length > 2) {
				switch (type) {
					case 0:
						returnTime = times[0];
						break;
					case 1:
						returnTime = times[1];
						break;
					case 2:
						returnTime = times[2];
						break;
				}
			} else if (times.length == 2) {
				switch (type) {
					case 0:
						returnTime = times[0];
						break;
					case 1:
						returnTime = times[1];
						break;
				}
			} else returnTime = "00";
		} else returnTime = time;
		return returnTime;
	}

	/**
	 * 如果最后一位是 ， 逗号，则清除掉
	 * 
	 * @param str
	 * @return
	 */
	public static String clearComma(String str) {
		String result = "";
		if (!WISERCheck.isEmpty(str)) {
			String lastOne = str.substring(str.length() - 1);
			if (lastOne.equals(",") || lastOne.equals("，")) {
				result = str.substring(0, str.length() - 1);
			} else {
				result = str;
			}
		}
		return result;
	}

	/**
	 * 清除小数点
	 *
	 * @param content
	 * @return
	 */
	public static String deleteDot(String content) {
		if (WISERCheck.isEmpty(content)) return null;
		if (!content.contains(".")) return null;
		return content.replaceAll("\\.", "");
	}

	/**
	 * 祛除首位 0
	 * 
	 * @param string
	 * @return
	 */
	public static String deleteFirstZero(String string) {
		if (WISERCheck.isEmpty(string)) return null;
		if (!string.contains("0")) return string;
		if (string.length() <= 1) return string;
		if (string.substring(0, 1).equals("0")) { // 只判断首位是0
			return string.substring(1, string.length());
		} else {
			return string;
		}
	}

	/**
	 * 分割 -
	 *
	 * @param content
	 * @param type
	 * @param type
	 * @return
	 */
	public static String splitDate(String content, int type) {
		if (WISERCheck.isEmpty(content)) return "";
		String[] contents;
		String returnContent = "";
		if (content.contains("-")) {
			contents = content.split("-");
			if (contents.length > 2) {
				switch (type) {
					case 0:
						returnContent = contents[0];
						break;
					case 1:
						returnContent = contents[1];
						break;
					case 2:
						returnContent = contents[2];
						break;
				}
			} else if (contents.length == 2) {
				switch (type) {
					case 0:
						returnContent = contents[0];
						break;
					case 1:
						returnContent = contents[1];
						break;
				}
			} else if (content.length() == 1) {
				switch (type) {
					case 0:
						returnContent = contents[0];
						break;
				}
			} else {
				returnContent = "";
			}
		}
		return returnContent;
	}

	/**
	 * 保存两位小数
	 *
	 * @param str
	 * @return
	 */
	public static String saveDoubleNumber(String str) {
		if (WISERCheck.isEmpty(str)) return "0.00";
		double d;
		if (WISERCheck.isDouble(str)) {
			d = Double.parseDouble(str);
		} else {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return String.valueOf(df.format(WISERCalculate.saveDoubleNum(d)));
	}

	// 返回Float类型
	public static float returnFloat(String str) {
		if (WISERCheck.isEmpty(str)) return 0f;
		float ss;
		try {
			ss = Float.parseFloat(str);
		} catch (Exception e) {
			return 0f;
		}
		return ss;
	}

	// 返回int类型
	public static int returnInteger(String str) {
		if (WISERCheck.isEmpty(str)) return 0;
		int ss = 0;
		try {
			ss = Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
		return ss;
	}

	// 返回Double类型
	public static double returnDouble(String str) {
		if (WISERCheck.isEmpty(str)) return 0.00;
		double ss;
		String newStr = saveDoubleNumber(str);
		try {
			ss = Double.parseDouble(newStr);
		} catch (Exception e) {
			return 0.00;
		}
		return ss;
	}

	// 返回Long类型
	public static long returnLong(String str) {
		if (WISERCheck.isEmpty(str)) return 0;
		String newStr = saveDoubleNumber(str);
		long ss;
		try {
			ss = Long.parseLong(newStr);
		} catch (Exception e) {
			return 0;
		}
		return ss;
	}

	// 将分钟转成0h0m格式
	public static String getTimeConvert(String time) {
		if (WISERCheck.isEmpty(time)) return "";
		int hour = returnInteger(time) / 60;
		int min = returnInteger(time) % 60;
		if (hour == 0 && min == 0) {
			return "";
		} else if (hour > 0 && min == 0) {
			return hour + "h";
		} else if (hour == 0 && min > 0) {
			return min + "m";
		} else if (hour > 0 && min > 0) {
			return hour + "h" + min + "m";
		} else return "";
	}

}

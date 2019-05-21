package com.wiser.library.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERCalculate {

	// 四舍五入
	public static double saveDoubleNum(double f) {
		BigDecimal b = new BigDecimal(f);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 获取两位小数
	 *
	 * @param dstr
	 *            数据
	 * @return
	 */
	public static double getDouble(double dstr) {
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.valueOf(df.format(dstr));
	}

}

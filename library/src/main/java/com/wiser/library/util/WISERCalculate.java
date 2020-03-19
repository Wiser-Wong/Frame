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

	/**
	 * 加
	 * @param d1 第一个double数
	 * @param d2 第二个double数
	 * @param point 小数点后几位
	 * @return
	 */
	public static double add(double d1, double d2,int point){
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		return bd1.add(bd2).setScale(point, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 减
	 * @param d1 第一个double数
	 * @param d2 第二个double数
	 * @param point 小数点后几位
	 * @return
	 */
	public static double subtract(double d1, double d2,int point){
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		return bd1.subtract(bd2).setScale(point, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 乘
	 * @param d1 第一个double数
	 * @param d2 第二个double数
	 * @param point 小数点后几位
	 * @return
	 */
	public static double multiply(double d1, double d2,int point){
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		return bd1.multiply(bd2).setScale(point, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 除
	 * @param d1 第一个double数
	 * @param d2 第二个double数
	 * @param point 小数点后几位
	 * @return
	 */
	public static double divide(double d1, double d2,int point){
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		return bd1.divide(bd2).setScale(point, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}

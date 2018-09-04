package com.wiser.library.util;

import java.math.BigDecimal;

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

}

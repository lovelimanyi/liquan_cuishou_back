package com.info.web.util;

import java.math.BigDecimal;
import java.util.Date;

public class CompareUtils {
	/**
	 * 大于0
	 * 
	 * @param dec1
	 * @return dec1>0
	 */
	public static boolean greaterThanZero(BigDecimal dec1) {
		if (dec1 == null) {
			return false;
		}
		return greaterThan(dec1, BigDecimal.ZERO);
	}

	/**
	 * 大于等于0
	 */
	public static boolean greaterThanAndEqualsZero(BigDecimal dec1) {
		if (dec1 == null) {
			return false;
		}
		return greaterEquals(dec1, BigDecimal.ZERO);
	}

	/**
	 * 大于等于
	 * 
	 * @param date1
	 * @param date2
	 * @return dec1 >= dec1
	 */
	public static boolean greaterEquals(BigDecimal dec1, BigDecimal dec2) {
		if (dec1.compareTo(dec2) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 不等于
	 * 
	 * @param date1
	 * @param date2
	 * @return dec1 != dec1
	 */
	public static boolean notEquals(BigDecimal dec1, BigDecimal dec2) {
		if (dec1.compareTo(dec2) != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 不等于0
	 * 
	 * @param date1
	 * @param date2
	 * @return dec1 != 0
	 */
	public static boolean notEqualsZero(BigDecimal dec1) {
		if (dec1 == null) {
			return false;
		}
		return notEquals(dec1, BigDecimal.ZERO);
	}

	/**
	 * 等于
	 * 
	 * @param dec1
	 * @param dec2
	 * @return dec1 == dec1
	 */
	public static boolean equals(BigDecimal dec1, BigDecimal dec2) {
		if (dec1.compareTo(dec2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 大于
	 * 
	 * @param dec1
	 * @param dec2
	 * @return dec1 > dec2
	 */
	public static boolean greaterThan(BigDecimal dec1, BigDecimal dec2) {
		if (dec1.compareTo(dec2) == 1) {
			return true;
		} else {
			return false;
		}
	}
}

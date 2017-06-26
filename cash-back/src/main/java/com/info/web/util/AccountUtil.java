package com.info.web.util;

public class AccountUtil {

	public static String getEnsureAmount(String ensureAmount, String ensureRate) {
		double amount = Double.parseDouble(ensureAmount);
		double rate = Double.parseDouble(ensureRate);
		return String.valueOf(amount * rate);
	}

}

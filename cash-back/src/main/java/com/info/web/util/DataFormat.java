package com.info.web.util;

import java.text.DecimalFormat;

public class DataFormat {
	/**
	 * 把金额按照位数nums拆分添加","号
	 * 
	 * @param amount
	 * @param nums
	 * @return
	 */
	public static String getAmount(String amount, int nums) {
		// boolean bool = false;
		if (null == amount) {
			return "0";
		}
		if (amount.length() <= nums) {
			if (!amount.contains(".")) {
				return amount + ".00";
			}
			return amount;
		} else {
			String xamount = ".00";

			if (amount.contains(".")) {
				// bool = true;
				String reAmount = amount;
				amount = amount.substring(0, amount.indexOf("."));
				xamount = reAmount.substring(reAmount.indexOf("."), reAmount
						.length());
				if (amount.length() <= nums) {
					return amount + xamount;
				}
			}
			StringBuffer sb = new StringBuffer();
			int rem = amount.length() % nums;
			int length = amount.length() / nums;
			if (rem > 0) {
				String subAmount = amount.substring(0, rem);
				sb.append(subAmount).append(",");
				amount = amount.substring(rem, amount.length());
			}
			for (int i = 0; i < length; i++) {
				String subStr = amount.substring(0, nums);
				if (i == length - 1) {
					sb.append(subStr);
				} else {
					sb.append(subStr).append(",");
				}
				amount = amount.substring(nums, amount.length());
			}
			// if(bool){
			// return sb.toString()+".00";
			// }
			sb.append(xamount);
			return sb.toString();
		}
	}

	public static String getAmount(double amountd, int nums) {
		String amount = String.valueOf(amountd);

		// boolean bool = false;
		if (null == amount) {
			return "0";
		}
		if (amount.length() <= nums) {
			if (!amount.contains(".")) {
				return amount + ".00";
			}
			return amount;
		} else {
			String xamount = ".00";
			if (amount.contains(".")) {
				// bool = true;
				String reAmount = amount;
				amount = amount.substring(0, amount.indexOf("."));
				xamount = reAmount.substring(reAmount.indexOf("."), reAmount
						.length());
				if (amount.length() <= nums) {
					return amount + xamount;
				}
			}
			StringBuffer sb = new StringBuffer();
			int rem = amount.length() % nums;
			int length = amount.length() / nums;
			if (rem > 0) {
				String subAmount = amount.substring(0, rem);
				sb.append(subAmount).append(",");
				amount = amount.substring(rem, amount.length());
			}
			for (int i = 0; i < length; i++) {
				String subStr = amount.substring(0, nums);
				if (i == length - 1) {
					sb.append(subStr);
				} else {
					sb.append(subStr).append(",");
				}
				amount = amount.substring(nums, amount.length());
			}
			// if(bool){
			// return sb.toString()+".00";
			// }
			sb.append(xamount);
			return sb.toString();
		}
	}

	/**
	 * 保留两位小数
	 * 
	 * @return
	 */
	public static String getDataNumber(double number) {
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(number);
	}

	/**
	 * 保留两位小数
	 * 
	 * @return
	 */
	public static double getDataNumber(String number) {
		return Double.parseDouble(new DecimalFormat("#.00").format(Double
				.parseDouble(number)));
	}

}

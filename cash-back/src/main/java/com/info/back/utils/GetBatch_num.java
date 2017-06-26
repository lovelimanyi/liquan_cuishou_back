package com.info.back.utils;

import java.util.Random;

public class GetBatch_num {

	public String getBatch_num(String nonce_str,String time_stamp) {
		Random r = new Random();
		int i=r.nextInt(99999);
		String Batch_num=nonce_str+time_stamp+String.valueOf(i);
		return Batch_num;
	}
}

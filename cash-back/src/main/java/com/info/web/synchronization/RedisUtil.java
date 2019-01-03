package com.info.web.synchronization;

import com.info.web.util.JedisDataClient;

public class RedisUtil {
	
	/**
	 * 删除redis中的key
	 */
	public static void delRedisKey(String key){
		try{
			JedisDataClient.del(key);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void delRedisKey2(String key){
		try{
			JedisDataClient.batchDel(key);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

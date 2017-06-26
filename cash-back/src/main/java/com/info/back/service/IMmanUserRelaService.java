package com.info.back.service;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.MmanUserRela;
import com.info.web.synchronization.SendRenewalManage;
import com.info.web.util.PageConfig;

public interface IMmanUserRelaService {
	
	public int saveNotNull(MmanUserRela mmanUserRela);
	/**
	 * 鏌ヨ閫氳褰�
	 * @param params
	 * @return
	 */
	public PageConfig<MmanUserRela> findPage(HashMap<String, Object> params);
	/**
	 * 鏌ヨ鎸囧畾閫炬湡澶╂暟鐨勫垪琛╨ist
	 * @param params
	 * @return
	 */
	public List<MmanUserRela> getList(HashMap<String, Object> params);
	
	/**
	 * 鏍规嵁鐢ㄦ埛id鏌ヨ鐢ㄦ埛瀵瑰簲鐨勮仈绯讳汉
	 * @param userId
	 * @return
	 */
	public List<MmanUserRela> getContactPhones(String userId);
	
	
	
	/**
	 *    分页查询
	 * @param params
	 * @return mazi
	 */
	public PageConfig<MmanUserRela> findAllPage(HashMap<String, Object> params);
	/**
	 *    根据userRelaId查询联系人
	 * @param params
	 * @return
	 */
	MmanUserRela getUserRealByUserId(String userRelaId);
}

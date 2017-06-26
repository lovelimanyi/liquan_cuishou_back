package com.info.back.service;

import com.info.back.result.JsonResult;
import com.info.web.pojo.ProblemFeedback;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @version
 * 
 */
public interface IProblemFeedbackService {
	/**
	 * 查询所有符合条件的问题反馈
	 * @param params
	 * @return
	 */
	public PageConfig<ProblemFeedback> getPage(HashMap<String, Object> params);

	/**
	 * 更新一条问题反馈的状态
	 * @param params
	 */
	public JsonResult updateStatus(HashMap<String, Object> params);

	/**
	 * 添加一条问题反馈
	 * @param
	 */
	public JsonResult saveProblemFeedback(HashMap<String,Object> params);
}

package com.info.back.service;

public interface ITaskJob {
	/**
	 * 爬虫每小时爬取一次目标网址的第一页数据；
	 */
	public void spiderToDo();

	/**
	 * 更新逾期未使用的红包为已过期
	 */
	public void redRecordOver();

}

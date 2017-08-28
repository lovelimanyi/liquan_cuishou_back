package com.info.back.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IPaginationDao;
import com.info.back.dao.ISysDictDao;
import com.info.back.result.JsonResult;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;
import com.info.web.pojo.SysDict;
import com.info.web.util.PageConfig;


@Service
public class SysDictService implements ISysDictService {
	@Autowired
	private IPaginationDao paginationDao;
	
	@Autowired
	private ISysDictDao sysDictDao;

	@Override
	public PageConfig<SysDict> findPage(HashMap<String, Object> params) {
		
		params.put(Constant.NAME_SPACE, "SysDict");
		PageConfig<SysDict> pageConfig = new PageConfig<SysDict>();
		pageConfig = paginationDao.findPage("findAll","findAllCount",params,null);
		
		return pageConfig;
	}

	@Override
	public JsonResult saveSysDict(Map<String, String> params) {
		
		JsonResult result=new JsonResult("-1","添加数据字典失败");
		SysDict sysDict = new SysDict();
		if(params.get("parentId")==null || params.get("parentId")==""){
			sysDict.setParentId("0");
		}else{
			sysDict.setParentId(params.get("parentId"));
		}
		if(params.get("delFlag")==null){
			sysDict.setDelFlag('0');
		}else{
			sysDict.setDelFlag(params.get("delFlag").charAt(0));
		}
		sysDict.setId(IdGen.uuid());
		sysDict.setValue(params.get("value"));
		sysDict.setLabel(params.get("label"));
		sysDict.setType(params.get("type"));
		sysDict.setDescription(params.get("description"));
		sysDict.setSort(new BigDecimal(params.get("sort")));
		sysDict.setCreateBy(params.get("createBy"));
		sysDict.setCreateDate(new Date());
		sysDict.setRemarks(params.get("remarks"));
		if(sysDictDao.insert(sysDict)>0){
			result.setCode("0");
			result.setMsg("添加数据字典成功");
		}
		return result;
	}

	@Override
	public SysDict get(String id) {
		return sysDictDao.get(id);
	}

	@Override
	public JsonResult updateSysDict(Map<String, String> params) {
		JsonResult result=new JsonResult("-1","修改数据字典失败");
		SysDict sysDict = new SysDict();
		
		sysDict.setId(params.get("id"));
		sysDict.setValue(params.get("value"));
		sysDict.setLabel(params.get("label"));
		sysDict.setType(params.get("type"));
		sysDict.setDescription(params.get("description"));
		sysDict.setSort(new BigDecimal(params.get("sort")));
		sysDict.setUpdateBy(params.get("updateBy"));
		sysDict.setUpdateDate(new Date());
		sysDict.setRemarks(params.get("remarks"));
		if(sysDictDao.update(sysDict)>0){
			result.setCode("0");
			result.setMsg("修改数据字典成功");
		}
		return result;
	}

	@Override
	public JsonResult deleteSysDict(String id) {
		JsonResult result=new JsonResult("-1","删除数据字典失败");
		if(sysDictDao.delete(id)>0){
			result.setCode("0");
			result.setMsg("删除数据字典成功");
		}
		return result;
	}

	@Override
	public List<SysDict> getStatus(String type) {
		return sysDictDao.getStatus(type);
	}
	@Override
	public List<SysDict> findDictByType(String type) {
		return sysDictDao.findDictByType(type);
	}

	@Override
	public List<SysDict> getOtherStatus(HashMap<String, Object> params) {
		params.put("type","xjx_collection_advise");
		params.put("refuseLable","拒绝");
		return sysDictDao.getOtherStatus(params);
	}

}

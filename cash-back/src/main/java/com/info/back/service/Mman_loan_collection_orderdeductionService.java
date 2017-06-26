package com.info.back.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IAuditCenterDao;
import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.back.dao.IMmanUserInfoDao;
import com.info.back.dao.IMmanUserLoanDao;
import com.info.back.dao.IMman_loan_collection_orderdeductionDao;
import com.info.back.dao.IPaginationDao;
import com.info.back.result.JsonResult;
import com.info.back.utils.IdGen;
import com.info.constant.Constant;
import com.info.web.pojo.AuditCenter;
import com.info.web.pojo.CountCollectionManage;
import com.info.web.pojo.MmanLoanCollectionOrder;
import com.info.web.pojo.MmanUserInfo;
import com.info.web.pojo.MmanUserLoan;
import com.info.web.pojo.Mman_loan_collection_orderdeduction;
import com.info.web.pojo.SysDict;
import com.info.web.util.CompareUtils;
import com.info.web.util.PageConfig;
@Service
public class Mman_loan_collection_orderdeductionService implements IMman_loan_collection_orderdeductionService {

	@Autowired
	private IMman_loan_collection_orderdeductionDao collection_orderdeductionDao;
	@Autowired
	private IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;
	@Autowired
    private IMmanUserLoanDao iMmanUserLoanDao;
	@Autowired
    private IMmanUserLoanService iMmanUserLoanService;
	@Autowired
	private IMmanUserInfoDao mmanUserInfoDao;
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	private ISysDictService sysDictService;
    @Autowired
    private IAuditCenterDao auditCenterDao;

	public JsonResult saveorderdeduction(HashMap<String, Object> params) {		
		JsonResult result=new JsonResult("-1","申请减免失败！");
		Date now = new Date();
		BigDecimal bigDecimal=new BigDecimal(0);	
		Mman_loan_collection_orderdeduction collection_orderdeduction=new Mman_loan_collection_orderdeduction();
		MmanLoanCollectionOrder collectionOrder=mmanLoanCollectionOrderDao.getOrderById(params.get("id").toString());
		System.out.println("查询》》》》"+params.get("deductionmoney"));
		String deductionmoney= (String) params.get("deductionmoney");
		MmanUserLoan loan = new MmanUserLoan();
		MmanUserInfo userInfo=null;
		loan=iMmanUserLoanDao.get(collectionOrder.getLoanId());
		System.out.println(loan.getLoanPenalty());
			//BigDecimal aa=loan.getLoanPenalty();
		userInfo = mmanUserInfoDao.get(collectionOrder.getUserId());
		
		if(userInfo!=null&&collectionOrder!=null&&loan!=null){
			
			  if(CompareUtils.greaterEquals(loan.getLoanPenalty(),bigDecimal)){
				//原始滞纳金更新			
					//bigDecimal= loan.getLoanPenalty().subtract(new BigDecimal(Integer.valueOf((String) params.get("deductionmoney"))));					
					//if(bigDecimal!=loan.getLoanPenalty()){
					//System.out.println("减免后的价格>>>>"+bigDecimal);
					//loan.setLoanPenalty(bigDecimal);//更新罚息	
					//iMmanUserLoanDao.updatePaymoney(loan);
					//System.out.println("滞纳金减免后"+loan.getLoanPenalty());
					collection_orderdeduction.setDeductionmoney(new BigDecimal(Integer.valueOf((String) params.get("deductionmoney"))));//减免金额
					//System.out.println("减免金额"+new BigDecimal(Integer.valueOf((String) params.get("deductionmoney"))));
					collection_orderdeduction.setDeductionremark((String) params.get("deductionremark"));//备注
					//System.out.println("减免备注"+params.get("deductionremark"));
					collection_orderdeduction.setCreatetime(now);//创建时间
					//System.out.println("时间"+now);
					collection_orderdeduction.setId(IdGen.uuid());
					//System.out.println("ID="+IdGen.uuid());
					collection_orderdeduction.setLoanrealname(userInfo.getRealname());
					//System.out.println("用户名"+userInfo.getRealname());
					collection_orderdeduction.setLoanuserphone(userInfo.getUserPhone());
					//System.out.println("电话"+userInfo.getUserPhone());
					collection_orderdeduction.setReturnmoney(loan.getLoanMoney());	
					//System.out.println("本金"+loan.getLoanMoney());
					int count= collection_orderdeductionDao.insertSelective(collection_orderdeduction);
					 				 				 
			        if(count>0){
			        	result.setMsg("申请减免成功");
			        	result.setCode("0");
			        }									
				}else {	
					result.setData("减免金额不能大于"+loan.getLoanPenalty());
			}				
	    }else{	
		result.setMsg("用户信息不能为空！");
	 }
		System.out.println(result.SUCCESS);
		return result;
			
   }


	 @Override
	 public PageConfig<Mman_loan_collection_orderdeduction> findPage(HashMap<String, Object> params) {
	        params.put(Constant.NAME_SPACE, "Mman_loan_collection_orderdeduction");
	        PageConfig<Mman_loan_collection_orderdeduction> pageConfig = new PageConfig<Mman_loan_collection_orderdeduction>();
	        pageConfig = paginationDao.findPage("findAll", "findAllCount", params, null);
	        return pageConfig;
	    }


	@Override
	public List<Mman_loan_collection_orderdeduction> findAllList(String id) {
		return collection_orderdeductionDao.findAllList(id) ;
	}
}

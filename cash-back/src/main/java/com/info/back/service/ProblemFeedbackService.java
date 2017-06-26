package com.info.back.service;

import com.info.back.dao.IMmanLoanCollectionOrderDao;
import com.info.back.dao.IPaginationDao;
import com.info.back.dao.IProblemFeedbackDao;
import com.info.back.result.JsonResult;
import com.info.constant.Constant;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.ProblemFeedback;
import com.info.web.util.PageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
@Service
public class ProblemFeedbackService implements IProblemFeedbackService {
    @Autowired
    private IProblemFeedbackDao problemFeedbackDao;

    @Autowired
    private IPaginationDao paginationDao;

    @Autowired
    private IMmanLoanCollectionOrderDao mmanLoanCollectionOrderDao;

    @Override
    public PageConfig<ProblemFeedback> getPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "ProblemFeedback");
        return paginationDao.findPage("findAll","findAllCount",params,null);
    }

    @Override
    public JsonResult updateStatus(HashMap<String, Object> params) {
        JsonResult result = new JsonResult("1","更新失败！");
        String status = problemFeedbackDao.getStatusById(String.valueOf(params.get("id")));
        if("1".equals(status)){
            result.setCode("2");
            result.setMsg("该问题已解决！");
        }else if (problemFeedbackDao.updateStatus(params) > 0){
            result.setCode("0");
            result.setMsg("更新成功！");
        }
        return result;
    }

    @Override
    public JsonResult saveProblemFeedback(HashMap<String,Object> params){
        JsonResult result = new JsonResult("1","添加问题反馈失败！");

        BackUser backUser = (BackUser)params.get("backUser");
        ProblemFeedback problem = new ProblemFeedback();
        problem.setCollectionCompanyId(backUser.getCompanyId());
        problem.setCreateDate(new Date());
        problem.setCreateUsername(backUser.getUserName());
        problem.setCreateUserRoleId(backUser.getRoleId());
        String content = params.get("content").toString();
        if(content.length() >= 510){
            content = content.substring(0,509);
        }
        problem.setDetails(content); // 只取前510个字符(255个汉字)
        String type = String.valueOf(params.get("type"));
        if(type == null || "".equals(type) || "null".equals(type)){
            result.setCode("2");
            result.setMsg("请选择反馈问题的类型！");
        }else {
            problem.setType(type);
            problem.setCreateUserId(backUser.getUuid());
            problem.setStatus("0");
            Object userPhone = params.get("loanUserPhone");
            Object loanEndTime = params.get("loanEndTime");
            if (userPhone != null && userPhone != "" && loanEndTime != null && loanEndTime != "") {
                problem.setLoanUserPhone(userPhone == null ? null : userPhone.toString());
                params.put("userPhone", userPhone);
                params.put("loanEndTime", loanEndTime);
                String LoanId = mmanLoanCollectionOrderDao.getLatestLoanByUserPhoneAndLoanEndTime(params);
                problem.setLoanId(LoanId);
            }
            if (problemFeedbackDao.insert(problem) > 0) {
                result.setCode("0");
                result.setMsg("添加问题反馈成功！");
            }
        }
        return result;
    }
}

package com.info.back.dao;

import com.info.web.pojo.OrderDispatchRule;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类描述：菜单dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 */
@Repository
public interface IOrderDispatchRuleDao {

    List<OrderDispatchRule> listAllInfo();
}

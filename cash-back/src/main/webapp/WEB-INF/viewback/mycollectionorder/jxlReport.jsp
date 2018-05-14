






<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<style type="">
	@charset "utf-8";
	body,h1,h2,h3,h4,h5,h6,hr,p,blockquote,dl,dt,dd,ul,ol,li,pre,form,fieldset,legend,button,input,textarea,th,td{margin:0;padding:0;}
	body,button,input,select,textarea{font-family: "微软雅黑";,"MicroSoft YaHei","Arial Narrow","HELVETICA"; font-size:14px; padding:0; margin:0; color:#666;}
	table{border-collapse:collapse;border-spacing:0;}
	ul,li{ margin:0px; padding:0px; list-style:none; }
	img{border:none; display:block;}
	h1,h2,h3,h4,h5,h6,p{ margin:0; padding:0;}
	.clear{ clear:both; margin:0; padding:0;}
	a{text-decoration:none; color:#666;}
	html{-webkit-text-size-adjust:none;} /* 2016-7-13 修改css */
	input,textarea{border:none; outline:medium;}
	textarea{overflow-y:auto;}
	.clear{clear:both; overflow:hidden;height:1px;margin-top:-1px;font-size:0;}
	
	/* 用户资信报告 */
	.credit-report{
		width: 800px;
		background-color: #fff;
		margin: 0 auto;
		padding: 38px 66px;
	}
	.credit-report i{
		font-style: normal;
	}
	.credit-header{
		padding-bottom: 6px;
		border-bottom: 2px solid #434343;
	}
	.credit-title{
		text-align: center;
		padding: 26px 0 20px;
	}
	.credit-title h1{
		font-size: 18px;
		color: #1d1d1d;
		line-height: 18px;
		margin-bottom: 4px;
	}
	.credit-title p{
		font-size: 12px;
		color: #5c5c5c;
	}
	
	.info-list{
	
	}
	.info-list dl{
		overflow: hidden;
	}
	.info-list dl dd{
		width: 33.33%;
		float: left;
		font-size: 12px;
		color: #4e4e4e;
		margin-bottom: 10px;
	}
	.table-box{
		margin-top: 12px;
	}
	.table-title{
		height: 26px;
		background-color: #f8bd54;
		text-align: center;
		font-size: 14px;
		color: #666;
		line-height: 26px;
		font-weight: bold;
	}
	.table-box p{
		font-size: 14px;
		color: #464646;
		margin: 7px 0 2px;
	}
	.table-box table{
		width: 100%;
		text-align: left;
		table-layout: fixed;
	} 
	.table-box-ts table{
	     table-layout: auto;
	}
	.table-box table thead tr{
		height: 21px;
		border-top: 2px solid #666;
		border-bottom: 1px solid #888;
		line-height: 21px;
		font-size: 12px;
	}
	.table-box table tbody tr td{
		height: 19px;
		border-bottom: 1px solid #888;
		line-height: 19px;
		font-size: 12px;
		overflow: hidden;
		word-wrap:break-word;
		word-break:break-all;
	}
</style>
<div class="pageContent">
		<table class="" style="width: 100%;" layoutH="10" nowrapTD="false">
			<tbody>
					<tr target="id" rel="${order.id }" onclick="sel(this);">
						<td>
<div class="pageContent">
 <div class="wrapper wrapper-content animated fadeInRight">
  	  <div class="row">
  	   <div class="col-lg-12">
			 <div class="credit-title">
			     <h1>互联网用户资信报告</h1>
			     <p>此报告内容是根据${name }授权，整理汇总互联网数据、交叉验证产生</p>
			 </div>
			 <div class="info-list clearfix">
			     <dl>
			         <dd><span>报告编号：</span><i>${report.rpt_id }</i></dd>
			         <dd><span>报告生成时间：</span><i>${report.update_time}</i></dd>
			     </dl>
			     <dl>
			         <dd><span>姓名：</span><i>${name }</i></dd>
			         <dd><span>性别：</span><i>${gender }</i></dd>
			         <dd><span>年龄：</span><i>${age }岁</i></dd>
			     </dl>
			     <dl>
			         <dd><span>身份证号：</span><i>${idNumber}</i></dd>
			     </dl>
			 </div>
			  <div class="table-box">
			     <div class="table-title">用户黑名单信息</div>
			     <p>用户黑名单信息</p>
			     <table>
			      <thead>
			          <tr>
			              <th>检查项目</th>
			              <th>结果</th>
			          </tr>
			      </thead>
			       <tbody>
			           <tr>
			               <td>用户号码联系黑中介分数</td>
			               <td>${check_black_info.phone_gray_score}</td>
			           </tr>
			           <tr>
			               <td>直接联系人中黑名单人数 </td>
			               <td>${check_black_info.contacts_class1_blacklist_cnt}</td>
			           </tr>
			           <tr>
			               <td>间接联系人中黑名单人数	</td>
			               <td>${check_black_info.contacts_class2_blacklist_cnt}</td>
			           </tr>
			           <tr>
			               <td>直接联系人人数</td>
			               <td>${check_black_info.contacts_class1_cnt}</td>
			           </tr>
			           <tr>
			               <td>引起间接黑名单人数	</td>
			               <td>${check_black_info.contacts_router_cnt}</td>
			           </tr>
			           <tr>
			               <td>直接联系人中引起间接黑名单占比</td>
			               <td>${check_black_info.contacts_router_ratio}</td>
			           </tr>
			       </tbody>
			     </table>
			 </div>
			 <div class="table-box">
			     <div class="table-title">电商月消费</div>
			     <p>电商月消费</p>
			     <table>
			      <thead>
			          <tr>
			              <th>消费次数</th>
			              <th>消费金额</th>
			              <th>月份</th>
			              <th>消费品类</th>
			          </tr>
			      </thead>
			       <tbody>
			       		<c:forEach items="${ebusiness_expense}" var="ebusinessExpense">
				           <tr>
				               <td>${ebusinessExpense.all_count }</td>
				               <td>${ebusinessExpense.all_amount }</td>
				               <td>${ebusinessExpense.trans_mth }</td>
				               <td>${ebusinessExpense.category }</td>
				           </tr>
			       		</c:forEach>
			       </tbody>
			     </table>
			 </div>
			  <div class="table-box">
			     <div class="table-title">电商地址分析</div>
			     <p>电商地址分析</p>
			     <table style="text-align: center;">
			      <thead>
			          <tr>
			              <th style="width: 10%">收货地址</th>
			              <th style="width: 10%">地址类型</th>
			              <th style="width: 10%">开始送货时间</th>
			              <th style="width: 10%">结束送货时间</th>
			              <th style="width: 10%">总送货金额</th>
			              <th style="width: 10%">总送货次数</th>
			              <th style="width: 10%">收货人名称</th>
			              <th style="width: 10%">收货人号码</th>
			              <th style="width: 10%">收货次数</th>
			              <th style="width: 10%">收获金额</th>
			          </tr>
			      </thead>
			       <tbody>
			       		<c:forEach items="${deliver_address}" var="deliverAddress">
				           <tr>
				           		<c:choose>
				           			<c:when test="${not empty deliverAddress.receiver and fn:length(deliverAddress.receiver)>0 }">
					           			<c:set var="rows" value="${fn:length(deliverAddress.receiver)}"></c:set>
				           			</c:when>
				           			<c:otherwise>
					           			<c:set var="rows" value="1"></c:set>
				           			</c:otherwise>
				           		</c:choose>
				               <td rowspan="${rows }">${deliverAddress.address }</td>
				               <td rowspan="${rows }">${deliverAddress.predict_addr_type }</td>
				               <td rowspan="${rows }">${deliverAddress.begin_date }</td>
				               <td rowspan="${rows }">${deliverAddress.end_date }</td>
				               <td rowspan="${rows }">${deliverAddress.total_amount }</td>
				               <td rowspan="${rows }">${deliverAddress.total_count }</td>
				              <td>${deliverAddress.receiver[0].name }</td>
				               <td>${deliverAddress.receiver[0].phone_num_list }</td>
				               <td>${deliverAddress.receiver[0].count }</td>
				               <td>${deliverAddress.receiver[0].amount }</td>
				           </tr>
			               	<c:forEach items="${deliverAddress.receiver }" var="receiver" varStatus="count">
			               	<c:if test="${count.index > 0}">
				                <tr>
					               <td>${receiver.name }</td>
					               <td>${receiver.phone_num_list }</td>
					               <td>${receiver.count }</td>
					               <td>${receiver.amount }</td>
				                </tr>
			               	</c:if>
			               	</c:forEach>
			       		</c:forEach>
			       </tbody>
			     </table>
			 </div>
			 <div class="table-box">
			     <div class="table-title">运营商信息</div>
			     <p>被告被查询情况</p>
			     <table>
			      <thead>
			          <tr>
			              <th>检查项目</th>
			              <th>结果</th>
			              <th>依据</th>
			          </tr>
			      </thead>
			       <tbody>
			       		<c:forEach items="${behavior_check}" var="behaviorCheck">
				           <tr>
				               <td>${behaviorCheck.check_point_cn }</td>
				               <td>${behaviorCheck.result }</td>
				               <td>${behaviorCheck.evidence }</td>
				           </tr>
			       		</c:forEach>
			       </tbody>
			     </table>
			 </div>
			  <div class="table-box table-box-ts">
			     <div class="table-title">联系人信息</div>
			     <table>
			      <thead>
			          <tr>
			              <th>联系人</th>
			              <th>最早联系时间</th>
			              <th>最晚联系时间</th>
			              <th>联系电话</th>
			              <th>归属地</th>
			              <th>总计电话通数</th>
			              <th>通话时长</th>
			              <th>短信条数</th>
			              <th>申请人拨打次数</th>
			              <th>拨打给申请人次数</th>
			          </tr>
			      </thead>
			       <tbody>
			       		<c:forEach items="${collection_contact}" var="collectionContact">
				           <tr>
				               <td>${collectionContact.contact_name }</td>
				               <td>${collectionContact.begin_date }</td>
				               <td>${collectionContact.end_date }</td>
				               <c:forEach items="${collectionContact.contact_details}" var="ContactDetails" varStatus="status">
				               		<c:if test="${status.index==0}">
				               			<td>${ContactDetails.phone_num }</td>
						                <td>${ContactDetails.phone_num_loc }</td>
						                <td>${ContactDetails.call_cnt }</td>
						                <td>${ContactDetails.call_len }</td>
						                <td>${ContactDetails.sms_cnt }</td>
						                <td>${ContactDetails.call_out_cnt }</td>
						                <td>${ContactDetails.call_in_cnt }</td>
				               		</c:if>
				               </c:forEach>
				           </tr>
			       		</c:forEach>
			       </tbody>
			     </table>
			 </div>
			   <div class="table-box table-box-ts">
			     <div class="table-title">通话数据分析</div>
			     <table>
			      <thead>
			          <tr>
			              <th>号码</th>
			              <th>互联网标识</th>
			              <th>归属地</th>
						  <th>联系时间（分钟）</th>
			              <th>主叫时间（分钟）</th>
			              <th>被叫时间（分钟）</th>
						  <th>联系次数</th>
						  <th>主叫次数</th>
						  <th>被叫次数</th>
			              <th>最近一月</th>
			          </tr>
			      </thead>
			       <tbody>
			       		<c:forEach items="${contact_list}" var="contactList">
				           <tr>
				               <td>${contactList.phone_num }</td>
				               <td>${contactList.contact_name }</td>
			
				               <td>${contactList.phone_num_loc }</td>	               
				               <td>${contactList.call_len }</td>				  
				               <td>${contactList.call_out_len }</td>				   
				               <td>${contactList.call_in_len }</td>
							   <td>${contactList.call_cnt }</td>
				               <td>${contactList.call_out_cnt }</td>
				               <td>${contactList.call_in_cnt }</td>
			
				               <td>${contactList.contact_1m }</td>
				           </tr>
			       		</c:forEach>
			       </tbody>
			     </table>
			 </div>
			  <div class="table-box table-box-ts">
			     <div class="table-title">联系人所在地区</div>
			     <table>
			      <thead>
			          <tr>
			              <th>地区</th>
			              <th>号码数量</th>
			              <th>电话呼入次数</th>
			              <th>电话呼入时间（分）</th>
			              <th>平均呼入时间（分）</th>
			              <th>电话呼出次数</th>
			              <th>电话呼出时间（分）</th>
			              <th>平均呼出时间（分）</th>
			          </tr>
			      </thead>
			       <tbody>
			     	 <c:forEach items="${contact_region}" var="contactRegion">
			           <tr>
			               <td>${contactRegion.region_loc }</td>
			               <td>${contactRegion.region_uniq_num_cnt }</td>
			               <td>${contactRegion.region_call_in_cnt }</td>
			               <td>${contactRegion.region_call_in_time }</td>
			               <td>${contactRegion.region_avg_call_in_time }</td>
			               <td>${contactRegion.region_call_out_cnt }</td>
			               <td>${contactRegion.region_call_out_time }</td>
			               <td>${contactRegion.region_avg_call_out_time }</td>
			           </tr>
			     	 </c:forEach>
			       </tbody>
			     </table>
			 </div>
			 <div class="table-box table-box-ts">
			 	 <div class="table-title">月使用情况</div>
			     <table>
			      <thead>
			          <tr>
			              <th>运营商</th>
			              <th>号码</th>
			              <th>归属地</th>
			              <th>月份</th>
			              <th>主叫时间（分）</th>
			              <th>被叫时间（分）</th>
			              <th>短信数量</th>
			              <th>数据流量（MB）</th>
			          </tr>
			      </thead>
			       <tbody>
			       		<c:forEach items="${cell_behavior}" var="cellBehavior">
			       			<c:forEach items="${cellBehavior.behavior}" var="behavior">
					           <tr>
					               <td>${behavior.cell_operator_zh }</td>
					               <td>${behavior.cell_phone_num }</td>
					               <td>${behavior.cell_loc }</td>
					               <td>${behavior.cell_mth }</td>
					               <td>${behavior.call_out_time }</td>
					               <td>${behavior.call_in_time }</td>
					               <td>${behavior.sms_cnt }</td>
					               <td>${behavior.net_flow }</td>
					           </tr>
			       			</c:forEach>
			       		</c:forEach>
			       </tbody>
			     </table>
			 </div>
			 <div class="table-box table-box-ts">
			     <div class="table-title">服务企业类型</div>
			     <table>
			      <thead>
			          <tr>
			              <th width="150">服务企业类型</th>
			              <th width="150">企业名称</th>
			              <th width="150">总互动次数</th>
			              <th>月互动次数</th>
			          </tr>
			      </thead>
			       <tbody>
				       	<c:forEach items="${main_service}" var="mainService">
				           <tr>
				               <td>${mainService.company_type }</td>
				               <td>${mainService.company_name }</td>
				               <td>${mainService.total_service_cnt }</td>
				               <td>${mainService.service_details }</td>
				           </tr>
				       	</c:forEach>
			       </tbody>
			     </table>
			 </div>
			  <div class="table-box">
			     <div class="table-title">出行数据分析</div>
			     <table>
			      <thead>
			          <tr>
			              <th>时间段</th>
			              <th>出发时间</th>
			              <th>回程时间</th>
			              <th>出发地</th>
			              <th>目的地</th>
			          </tr>
			      </thead>
			       <tbody>
			       	<c:forEach items="${trip_info}" var="tripInfo">
			           <tr>
			               <td>${tripInfo.trip_type }</td>
			               <td>${tripInfo.trip_start_time }</td>
			               <td>${tripInfo.trip_end_time }</td>
			               <td>${tripInfo.trip_leave }</td>
			               <td>${tripInfo.trip_dest }</td>
			           </tr>
			       	</c:forEach>
			       </tbody>
			     </table>
			 </div>
			  <div class="table-box">
			  	 <div class="table-title">报告被查询情况</div>
			     <p>报告被查询情况</p>
			     <table>
			      <thead>
			      	 <th>检查项目</th>
			         <th>结果</th>
			      </thead>
			       <tbody>
			           <tr>
			             	 <td>查询过该用户的相关企业数量</td>
						       <c:if test="${not empty check_search_info.searched_org_cnt }">
								   <td>${check_search_info.searched_org_cnt}</td>
							   </c:if>
							   <c:if test="${empty check_search_info.searched_org_cnt }">
								   <td></td>
							   </c:if>
			           </tr>
			           <tr>
						  	<td>查询过该用户的相关企业类型</td>
						  	<c:if test="${not empty check_search_info.searched_org_type }">
							  	<td>${check_search_info.searched_org_type}</td>
						  	</c:if>
						  	<c:if test="${empty check_search_info.searched_org_type }">
							  	<td></td>
						  	</c:if>
			           </tr>
			           <tr>
							<td>身份证组合过的其他姓名</td>
							<c:if test="${not empty check_search_info.idcard_with_other_names }">
							  	<td>${check_search_info.idcard_with_other_names}</td>
						  	</c:if>
						  	<c:if test="${empty check_search_info.idcard_with_other_names }">
							  	<td></td>
						  	</c:if>
			           </tr>
			           <tr>
							<td>身份证组合过其他电话</td>
							<c:if test="${not empty check_search_info.idcard_with_other_phones }">
							  	<td>${check_search_info.idcard_with_other_phones}</td>
						  	</c:if>
						  	<c:if test="${empty check_search_info.idcard_with_other_phones }">
							  	<td></td>
						  	</c:if>
			           </tr>
			           <tr>
							<td>电话号码组合过其他姓名</td>
							<c:if test="${not empty check_search_info.phone_with_other_names }">
							  	<td>${check_search_info.phone_with_other_names}</td>
						  	</c:if>
						  	<c:if test="${empty check_search_info.phone_with_other_names }">
							  	<td></td>
						  	</c:if>
			           </tr>
			           <tr>
							<td>电话号码组合过其他身份证</td>
							<c:if test="${not empty check_search_info.phone_with_other_idcards }">
							  	<td>${check_search_info.phone_with_other_idcards}</td>
						  	</c:if>
						  	<c:if test="${empty check_search_info.phone_with_other_idcards }">
							  	<td></td>
						  	</c:if>
			           </tr>
			           <tr>
							<td>电话号码注册过的相关企业数量</td>
							<c:if test="${not empty check_search_info.register_org_cnt }">
							  	<td>${check_search_info.register_org_cnt}</td>
						  	</c:if>
						  	<c:if test="${empty check_search_info.register_org_cnt }">
							  	<td></td>
						  	</c:if>
			           </tr>
			           <tr>
							<td>电话号码注册过的相关企业类型</td>
							<c:if test="${not empty check_search_info.register_org_type }">
							  	<td>${check_search_info.register_org_type}</td>
						  	</c:if>
						  	<c:if test="${empty check_search_info.register_org_type }">
							  	<td></td>
						  	</c:if>
			           </tr>
			           <tr>
							<td>电话号码出现过的公开网站</td>
							<c:if test="${not empty check_search_info.arised_open_web }">
							  	<td>${check_search_info.arised_open_web}</td>
						  	</c:if>
						  	<c:if test="${empty check_search_info.arised_open_web }">
							  	<td></td>
						  	</c:if>
			           </tr>
			       </tbody>
			     </table>
			 </div>
      	</div>
    </div>
</div>
</div>
					    </td>
					</tr>
			</tbody>
		</table>
		<c:if test="${not empty message}">
		<script type="text/javascript">
            alertMsg.info("${message}");
		</script>
	</c:if>
	</div>

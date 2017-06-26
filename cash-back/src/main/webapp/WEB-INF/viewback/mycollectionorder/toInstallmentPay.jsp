<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>分期还款</title>
	</head>
	<body>
		<div class="pageContent">

			<div style="height: 150px; overflow: auto;" class="pageFormContent" layoutH="20">
				<fieldset name="message" style="padding-bottom: 30px;">
					<legend>订单分期</legend>

					<form id="frm" onsubmit="return navTabSearch(this);" method="post" enctype="multipart/form-data" action="collectionOrder/installmentPay?myId=${params.myId}"
						  class="pageForm required-validate">
						<input type="hidden" name="parentId" value="${params.parentId}" />
						<input type="hidden" name="id" id="id" value="${params.id }">
						<input type="hidden" name="type" value="2">

							<table class="searchContent">
								<tr>
									<td>
										<dt style="width: 80px;">
											<label>
												分期类型:
											</label>
										</dt>
										<dd>
											<select name="installmentpay" id="installmentpay" >
												<c:forEach var="group" items="${installmentList }">
													<option value="${group.key }" <c:if test="${group.key eq params.installmentpay}">selected = "selected"</c:if>>
															${group.value}
													</option>
												</c:forEach>
											</select>
										</dd>

										<dd style="width: 110px;">
										<div class="buttonActive">
											<div class="buttonContent">
												<button type="submit">
													分期计算
												</button>
											</div>
										</div>
										<%--<c:if test="${installmentPayRecordList == null}">--%>
											<div class="buttonActive">
												<div class="buttonContent">
													<button type="button" onclick="linkage()" class="delete" target="ajaxTodo" rel="jbsxBox" title="确认要删除该用户吗？">
														创建
													</button>
													<%--<a href="#" onclick="linkage()" class="delete" title="分期业务办理是否确认？">创建</a>--%>
												</div>
											</div>
										<%--</c:if>--%>
										</dd>
									</td>
								</tr>
								<tr>
									<td>
										<dt style="width: 80px;">
											<label>
												本金金额:
											</label>
										</dt>
										<dd>
											<label>
												${ownMoney}
											</label>
										</dd>
										<dt style="width: 80px;">
											<label>
												滞纳金金额:
											</label>
										</dt>
										<dd>
											<label>
												${overdueMoney}
											</label>
										</dd>
									</td>
								</tr>
							</table>
							<div style="text-align: center; padding: 8px 0; border-bottom: 1px solid #b3b3b3;"></div>
							<table class="table" style="width: 100%;" nowrapTD="false">
								<thead>
								<tr>
									<th align="center" width="50">
										类型
									</th>
									<th align="center" width="50">
										还款时间
									</th>
									<th align="center" width="50">
										还款总计
									</th>
									<th align="center" width="50">
										本金金额
									</th>
									<th align="center" width="50">
										滞纳金金额
									</th>
									<th align="center" width="50">
										服务费
									</th>
								</tr>
								</thead>
								<tbody id="installmentForm">
								<c:forEach var="installmentInfo" items="${installmentPayInfoVoList}" varStatus="status">
									<tr target="id" rel="">
										<td align="center" width="50">
												${installmentInfo.installmentType}
										</td>
										<td align="center" width="50">
											<fmt:formatDate value="${installmentInfo.repayTime}" pattern="yyyy-MM-dd"/>
										</td>
										<td align="center" width="50">
												${installmentInfo.totalRepay}
										</td>
										<td align="center" width="50">
												${installmentInfo.stagesOwnMoney}
										</td>
										<td align="center" width="50">
												${installmentInfo.stagesOverdueMoney}
										</td>
										<td align="center" width="50">
												${installmentInfo.serviceCharge}
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>

					</form>
				</fieldset>
				<fieldset name="backlog" style="padding-bottom: 30px;">
					<legend>分期记录</legend>
					<form id="frm2" onsubmit="return navTabSearch(this);" method="post" enctype="multipart/form-data" action="collectionOrder/installmentPay?myId=${params.myId}"
						  class="pageForm required-validate">
						<table class="searchContent">
							<tr>
								<td>
									<dt style="width: 80px;">
										<label>
											借款人姓名:
										</label>
									</dt>
									<dd>
										<label id = "loanUserName">
											${loanUserName}
										</label>
									</dd>
									<dt style="width: 80px;">
										<label>
											借款人电话:
										</label>
									</dt>
									<dd>
										<label id = "loanUserPhone">
											${loanUserPhone}
										</label>
									</dd>
								</td>
							</tr>
						</table>
						<div style="text-align: center; padding: 8px 0; border-bottom: 1px solid #b3b3b3;"></div>
						<table class="table" style="width: 100%;" nowrapTD="false">
							<thead>
							<tr>
								<th align="center" width="50">
									还款批次
								</th>
								<th align="center" width="50">
									应还时间
								</th>
								<th align="center" width="50">
									应还金额
								</th>
								<th align="center" width="50">
									还款状态
								</th>
								<th align="center" width="50">
									操作
								</th>
							</tr>
							</thead>
							<tbody id="installmentForm2">
							<c:forEach var="installmentPayRecord" items="${installmentPayRecordList}" varStatus="status">
								<tr target="id" rel="">
									<td align="center" width="50">
										${installmentPayRecord.repayBatches}
									</td>
									<td align="center" width="50">
										<fmt:formatDate value="${installmentPayRecord.repayTime}" pattern="yyyy-MM-dd"/>
									</td>
									<td align="center" width="50">
										${installmentPayRecord.repayMoney}
									</td>
									<td align="center" width="50">
										<c:if test="${installmentPayRecord.repayStatus == 0}">
											还款成功
										</c:if>
										<c:if test="${installmentPayRecord.repayStatus == 1}">
											逾期未还
										</c:if>
									</td>
									<td align="center" width="50">
										<c:if test="${installmentPayRecord.operationStatus == 1}">
											代扣
										</c:if>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>

					</form>
				</fieldset>

			</div>

		</div>
	</body>
</html>

<script type="text/javascript">
	function linkage(){
		var msg = "分期业务办理是否确认？";
		if (confirm(msg)==true){
			var installmentId = $("#installmentpay option:selected").val();
			var id = '${params.id }';
			var partnerId = '${params.parentId}';
			var url='<%=path%>/back/collectionOrder/insertInstallmentPayRecord?installmentpay='+installmentId+'&id='+id+'&parentId='+partnerId;

			$.getJSON(url, function(data) {
				console.info("data=========== "+data);
				if(data){
					$("#loanUserName").html(data.loanUserName);
					$("#loanUserPhone").html(data.loanUserPhone);
					$("#installmentForm2").empty();
					$.each(data.installmentPayRecordList,function(n,item){
						var repayStatus = '';
						if(item.repayStatus == '0'){
							repayStatus = '还款成功';
						}else if(item.repayStatus == '1'){
							repayStatus = '逾期未还';
						}
						var operationStatus = '';
						if(item.operationStatus == '0'){
							operationStatus = '代扣';
						}

						$("#installmentForm2").append("<tr target='id' rel=''>");
						$("#installmentForm2").append("<td align='center' width='50'>"+item.repayBatches+"</td>");
						$("#installmentForm2").append("<td align='center' width='50'>"+item.dateNew+"</td>");
						$("#installmentForm2").append("<td align='center' width='50'>"+item.repayMoney+"</td>");
						$("#installmentForm2").append("<td align='center' width='50'>"+repayStatus+"</td>");
						$("#installmentForm2").append("<td align='center' width='50'>"+operationStatus+"</td>");
						$("#installmentForm2").append("</tr>");
					});
				}else{
					$("#installmentForm2").empty();
					$("#installmentForm2").append("<option value=''>没有数据</option>");
				}
			});
//			return true;
		}else{
			return false;
		}
	}
</script>
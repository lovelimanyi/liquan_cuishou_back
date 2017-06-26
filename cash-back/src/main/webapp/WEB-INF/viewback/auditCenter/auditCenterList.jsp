<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="auditCenter/getAuditCenterPage?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>借  款 编 号: <input type="text" id="loanId" name="loanId" value="${params.loanId}"/></td>
					<td>申请人: <input type="text" id="userName" name="userName" value="${params.userName}"/></td>
					<td>
						状态:
						<select name="status" id="status">
							<option value="">全部</option>
							<option value='0'  <c:if test="${params.status eq '0'}">selected="selected"</c:if>>审核中</option>
							<option value='2' <c:if test="${params.status eq '2'}">selected="selected"</c:if>>审核通过</option>
							<option value='3' <c:if test="${params.status eq '3'}">selected="selected"</c:if>>拒绝</option>
							<option value='4' <c:if test="${params.status eq '4'}">selected="selected"</c:if>>失效</option>
							<option value='5' <c:if test="${params.status eq '5'}">selected="selected"</c:if>>通过不计入绩效</option>
						</select>
					</td>
					<td>
						审核类型:
						<select name="type" id="type">
							<option value="">全部</option>
							<c:forEach var="company" items="${levellist }">
								<option value="${company.value }" <c:if test="${company.value eq params.type}">selected = "selected"</c:if>>
										${company.label}
								</option>
							</c:forEach>
						</select>
					</td>
					<c:if test="${userGropLeval ne '10022'}">
						<c:choose>
							<c:when test="${not empty params.CompanyPermissionsList}">
								<td>催 收 公 司:
									<select name="companyId" id="companyId">
										<option value="">全部</option>
										<c:forEach var="company" items="${ListMmanLoanCollectionCompany }">
											<c:forEach var="companyViw" items="${params.CompanyPermissionsList}">
												<c:if test="${companyViw.companyId eq company.id}">
													<option value="${company.id }" <c:if test="${company.id eq params.companyId}">selected = "selected"</c:if>>
															${company.title}
													</option>
												</c:if>
											</c:forEach>
										</c:forEach>
									</select>
								</td>
							</c:when>
							<c:otherwise>
								<td>催 收 公 司:
									<select name="companyId" id="companyId">
										<option value="">全部</option>
										<c:forEach var="company" items="${ListMmanLoanCollectionCompany }">
											<option value="${company.id }" <c:if test="${company.id eq params.companyId}">selected = "selected"</c:if>>
													${company.title}
											</option>
										</c:forEach>
									</select>
								</td>
							</c:otherwise>
						</c:choose>
					</c:if>
					<td>
						创建时间
						<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									查询
								</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include>
			<table class="table" style="width: 100%;" layoutH="112"
				nowrapTD="false">
				<thead>
					<tr>
						<th align="center" width="20">
							<input type="checkbox" id="checkAlls" onclick="checkAll(this);"/>
						</th>
						<th align="center" width="50">
							借款编号
						</th>
						<th align="center" width="80">
							公司名称
						</th>
						<th align="center" width="30">
							申请人
						</th>
						<th align="center" width="30">
							申请类型
						</th>
						<th align="center" width="50">
							添加时间
						</th>
						<th align="center" width="30">
							状态
						</th>
						<th align="center" width="30">
							审核时间
						</th>
						<th align="center" width="30">
							减免金额
						</th>
						<th align="center" width="30">
							备注
						</th>
						<th align="center" width="30">
							催收建议
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="audit" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${audit.id }">
							<td>
								<input type="checkbox" name="checkItem" value="${audit.id}"/>
							</td>
							<td>
								${audit.loanId}
							</td>
							<td>
								${audit.companyName}
							</td>
							<td>
								${audit.userName}
							</td>
							<td>
								${typeMap[audit.type]}
							</td>
							<td>
								<fmt:formatDate value="${audit.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<c:choose>
									<c:when test="${audit.status eq '2'}">通过</c:when>
									<c:when test="${audit.status eq '3'}">拒绝</c:when>
									<c:when  test="${audit.status eq '4'}"> <span style="color:red;">失效</c:when>
									<c:when  test="${audit.status eq '5'}">通过不计入考核</c:when>
									<c:otherwise>审核中</c:otherwise>
								</c:choose>
							</td>
							<td>
								<fmt:formatDate value="${audit.audittime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${audit.reductionMoney}
							</td>
							<td>
								${audit.remark}
							</td>
							<td>
								<c:choose>
									<c:when test="${audit.type eq '2'}">
										<c:if test="${audit.note eq '1'}"> 通过</c:if>
										<c:if test="${audit.note eq '2'}"> 拒绝</c:if>
										<c:if test="${audit.note eq '3'}">无建议</c:if>
									</c:when>
									<c:otherwise>--</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>

<script type="text/javascript">
	function checkAll(obj){
		var bol = $(obj).is(':checked');
		$("input[name='checkItem']").attr("checked",bol);
	}
	function sel(obj){
		var bol = true;
		var check = $(obj).find("input[name='checkItem']");
		if($(check).is(':checked')){
			bol = false;
		}
		$(check).attr("checked",bol);
	}
	function getOrderIds(obj){
		var href=$(obj).attr("href");
		if(href.indexOf('&ids') > -1){
			href = href.substring(0,href.indexOf('&ids'));
		}
		var hasDifferentGroup='0';
		var selectedGroup = "";
		$("input[name='checkItem']:checked").each(function(){
			var group=$(this).attr("group");
			if(group!= undefined && group != ''){
				if(selectedGroup == ''){
					selectedGroup = group;//第一次赋值
				}else if(selectedGroup != group){// 之后和第一次的值比较，有不同就GG
					hasDifferentGroup ='1';
				}
			}
		})
		if(hasDifferentGroup){
			var ids="";
			$("input[name='checkItem']:checked").each(function(){
				ids = ids +"," +$(this).val();
			});
			var toHref=href+"&ids="+ids.substring(1)+"&groupStatus="+hasDifferentGroup;
			$(obj).attr("href",toHref);
		}else{
			return;
		}
	}
</script>
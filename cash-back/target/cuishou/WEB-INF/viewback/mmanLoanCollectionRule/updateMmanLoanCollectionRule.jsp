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
		<title>Insert user</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" enctype="multipart/form-data"
				action="collectionRule/updateMmanLoanCollectionRulePage"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="id" id="id" value="${collectionRule.id }">
				<div class="pageFormContent" layoutH="50" style="overflow: auto;">
					<dl>
						<dt style="width: 80px;">
							<label>
								公司名称:
							</label>
						</dt>
						<dd>
							<select name="companyId">
									<c:forEach var="company" items="${companyList}" varStatus="status">
										<option value="${company.id}" <c:if test="${collectionRule.companyId eq company.id}">selected="selected"</c:if>>${company.title}</option>
									</c:forEach>
							</select>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								用户组:
							</label>
						</dt>
						<dd>
							<select name="collectionGroup">
								<c:forEach items="${groupNameMap}" var="map">
								  	<option value="${map.key}" <c:if test="${collectionRule.collectionGroup eq map.key}">selected="selected"</c:if> >${map.value}</option>  
							    </c:forEach>  
						</select>
						</dd>
					</dl>
					<div class="divider"></div>
					
					<dl>
						<dt style="width: 80px;">
							<label>
								每天上限:
							</label>
						</dt>
						<dd>
							<input name="everyLimit" value="${collectionRule.everyLimit}" maxlength="11" class="required" type="text"
									alt="请输入每天上限" size="30" />
						</dd>
					</dl>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										保存
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
	
</script>
</html>
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
			<form id="frm" method="post" enctype="multipart/form-data" action="company/updateDateCompany" onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="id" value="${company.id}">
				<div class="pageFormContent" layoutH="50" style="overflow: auto;">
					<dl>
						<dt style="width: 80px;">
							<label>
								公司名称:
							</label>
						</dt>
						<dd>
							<input name="title" value="${company.title}" minlength="1" maxlength="30" class="required" type="text" alt="请输入公司名称" size="30" />
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								地区:
							</label>
						</dt>
						<dd>
							<input name="region" value="${company.region}" minlength="1" maxlength="30" class="required" type="text" alt="请输入地区" size="30" />
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt style="width: 80px;">
							<label>
								是否启用:
							</label>
						</dt>
						<dd>
							<c:if test="${company.status eq '1'}"></c:if>
							<label style="width: 300px;">
								<select name="status">
									<option value="1" <c:if test="${company.status eq '1'}">selected="selected"</c:if>>启用</option>
									<option value="0" <c:if test="${company.status eq '0'}">selected="selected"</c:if>>禁用</option>
								</select>
							</label>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								是否自营:
							</label>
						</dt>
						<dd>
							<input name="selfBusiness"  type="radio"  <c:if test="${company.selfBusiness eq '1'}">checked="checked"</c:if> 
								value="1" />
							是
							<input name="selfBusiness"  type="radio" value="0" <c:if test="${company.selfBusiness eq '0'}">checked="checked"</c:if>  />
							否
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
</html>
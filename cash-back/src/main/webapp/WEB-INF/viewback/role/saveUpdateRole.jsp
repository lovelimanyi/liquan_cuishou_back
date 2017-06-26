<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>添加权限</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="role/saveUpdateRole"
				onsubmit="return validateCallback(this, navTabAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<c:if test="${ empty backRole}">
					<input type="hidden" name="type" value="${params.type }">
				</c:if>
				<input type="hidden" name="id" value="${params.id }">
				<div class="pageFormContent" layoutH="56">
					<p>
						<label>
							角色名称:
						</label>
						<input name="roleName" class="required" minlength="2"
							value="${backRole.roleName }" maxlength="10" type="text"
							alt="请输入角色名称" size="30" />
					</p>
					<div class="divider"></div>
					<p>
						<label>
							上级角色:
						</label>
						<input name="district.id" value="${superBackRole.id }" type="hidden" />
						<input class="textInput readonly" name="district.districtName"
							type="text" readonly="readonly" value="${superBackRole.roleName }" />
						<a class="btnLook" href="role/saveUpdateRole?type=showRoleList" width="280"
							height="470" lookupgroup="district">上级角色</a>
					</p>
					<div class="divider"></div>
					<label>
						角色描述:
					</label>
					<textarea name="roleSummary" rows="10" cols="60">${backRole.roleSummary }</textarea>
					<br />
					<div class="divider"></div>
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

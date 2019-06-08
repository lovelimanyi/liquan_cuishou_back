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
			<form method="post" action="module/saveUpdateModule" 
				onsubmit="return validateCallback(this, navTabAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<c:if test="${ empty backModule}">
					<input type="hidden" name="type" value="${params.type }">
				</c:if>
				<input type="hidden" name="id" value="${params.id }">
				<div class="pageFormContent" layoutH="56">
					<p>
						<label>菜单名称:</label>
						<input name="moduleName" class="required" minlength="2" maxlength="10" type="text" alt="请输入菜单名称" 
						value="${backModule.moduleName }" size="30"  />
					</p>
					<div class="divider"></div>
					<p>
						<label>菜单路径:</label>
						<input name="moduleUrl" style="width: 400px;"   type="text" alt="请输入菜单路径" size="40"  value="${backModule.moduleUrl }"/>
					</p>
					<div class="divider"></div>
					<p>
						<label>排序:</label>
						<input name="moduleSequence"  type="text" alt="请输入排序值" size="20" value="${backModule.moduleSequence }" />
					</p>
					<p>
						<label>样式:</label>
						<input name="moduleStyle" style="width: 400px;"  type="text" alt="请输入该菜单的打开方式、宽高等样式"  value='${backModule.moduleStyle }' />
					</p>
					<div class="divider"></div>
					<p><label>上级菜单:</label>
						<input value="${parentModule.id }" type="hidden" name="district.id"/>
						<input class="textInput readonly" value="${parentModule.moduleName }" name="district.districtName" type="text" readonly="readonly" />
						<a class="btnLook" href="module/showRightList" width="280" height="470" lookupgroup="district">上级菜单</a>	
					</p>
					<div class="divider"></div>
					<p>
						<label>菜单描述:</label>
						<textarea  name="moduleDescribe" rows="10" cols="60" maxlength="50">${backModule.moduleDescribe }</textarea>
					</p>
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

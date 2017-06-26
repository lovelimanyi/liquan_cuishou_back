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
				action="fengKong/saveUpdateFengKong"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="id" id="id" value="${fengKong.id }">
				<div class="pageFormContent" layoutH="50" style="overflow: auto;">
					<dl>
						<dt style="width: 80px;">
							<label>
								风控标签名:
							</label>
						</dt>
						<dd>
							<input name="fkLabel" value="${fengKong.fkLabel}" maxlength="50" class="required" type="text"
									alt="请输入风控标签名" size="30" />
						</dd>
					</dl>
					<c:if test="${ not empty fengKong.status }">
						<dl>
							<dt style="width: 80px;">
								<label>
									状态:
								</label>
							</dt>
							<dd>
								<select name="status">
									<option value="0" <c:if test="${fengKong.status eq '0'}">selected="selected"</c:if>>可用</option>
									<option value="1" <c:if test="${fengKong.status eq '1'}">selected="selected"</c:if>>禁用</option>
								</select>
							</dd>
						</dl>
					</c:if>
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
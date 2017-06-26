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
				action="templateSms/saveUpdateTemplateSms"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="id" id="id" value="${templateSms.id }">
				<div class="pageFormContent" layoutH="50" style="overflow: auto;">
					<dl>
						<dt style="width: 80px;">
							<label>
								名称:
							</label>
						</dt>
						<dd>
							<input name="name" value="${templateSms.name}" maxlength="50" class="required" type="text"
									alt="请输入名称" size="30" />
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								短信类型:
							</label>
						</dt>
						<dd>
							<select name="msgType">
								<option value="3" <c:if test="${templateSms.msgType eq '3'}">selected="selected"</c:if>>S1组</option>
								<option value="4" <c:if test="${templateSms.msgType eq '4'}">selected="selected"</c:if>>S2组</option>
								<option value="5" <c:if test="${templateSms.msgType eq '5'}">selected="selected"</c:if>>M组</option>
							</select>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								状态:
							</label>
						</dt>
						<dd>
							<select name="status">
								<option value="1" <c:if test="${templateSms.status eq '1'}">selected="selected"</c:if>>可用</option>
								<option value="2" <c:if test="${templateSms.status eq '2'}">selected="selected"</c:if>>禁用</option>
							</select>
						</dd>
					</dl>
					<div class="divider"></div>
						<dt style="width: 80px;">
							<label>
								短信内容:
							</label>
						</dt>
					<p>	
						<textarea  name="contenttext" rows="5" cols="72" maxlength="135" class="required">${templateSms.contenttext }</textarea>
						<br/><font color="red">提示：短信最多135个字（含标点符号）</font>
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
	<script type="text/javascript">
	
</script>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>短信群发</title>
</head>
<body>
	<div class="pageContent">
<!--这里会自动关闭当前窗口		<form id="issueForm" name="issueForm" method="post"	action="msg/sendGroupZt?type=update&parentId=${parentId }" onsubmit="return validateCallback(this, navTabAjaxDone);"-->
		<form id="issueForm" name="issueForm" method="post"	action="msg/sendGroupZt?type=update&parentId=${parentId }" onsubmit="return validateCallback(this);"
			class="pageForm required-validate">
			<div class="pageFormContent"  layoutH="55">
				<fieldset>
					<legend>信息群发</legend>
					<dl>
						<dt>用户总数:</dt>
						<dd class="unit">
						<label>${max }</label>
						</dd>
					</dl>
					<dl>
						<dt>单次群发上限:</dt>
						<dd class="unit">
						<label>程序自动每2000条群发一次</label>
						</dd>
					</dl>
					<dl title="从填写的行开始查询用户">
						<dt>开始行:</dt>
						<dd class="unit">
							<input name="rowBegin" id="rowBegin" class="required digits" min="1" max="${max }"
								type="text" />
						</dd>
					</dl>
					<dl class="nowrap" title="查询出多少个用户进行群发操作">
						<dt>群发数:</dt>
						<dd class="unit">
							<input name="rowEnd" id="rowEnd" class="required digits" max="100000"
								type="text" />
						</dd>
					<dl class="nowrap">
						<dt>请选择签名:</dt>
						<dd class="unit">
							<select name="sign">
								<c:forEach items="${SMS_ZT_LIST}" var="sign">
									<c:if test="${fn:contains(sign.sysKey,'zt_sign') }">
										<option value="${sign.sysValueBig }"
										<c:if test="${sign.sysValueBig eq params.sign }">selected="selected"</c:if>
										>${sign.sysValueAuto }</option>
									</c:if>
								</c:forEach>
							</select>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>群发内容:</dt>
						<dd class="unit">
							<textarea rows="10" cols="80" name="smsContent"></textarea>
						</dd>
					</dl>
					</fieldset>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">发送</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>

		</form>
	</div>
<c:if test="${not empty message}">
		<script type="text/javascript">
			alertMsg.error("${message}");
		</script>
</c:if>
</body>
<style type="text/css">
#bbd dl {
	width: 33%;
}
#bbd dl dt {
	width: 100%;
}
#bbd .nowrap{
	width: 100%!important;
}
</style>
</html>
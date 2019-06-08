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
		<title>修改/添加银行账号</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="notice/${params.url }"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="myId"  value="${params.myId}" />
				<input type="hidden" name="id" value="${backNotice.id }">
				<div class="pageFormContent" layoutH="56">
					<dl>
						<dt>
							名称：
						</dt>
						<dd>
							<input name="noticeTitle" class="required" value="${backNotice.noticeTitle }" type="text" alt="请输入名称" size="20" />
						</dd>
					</dl>
					<dl>
						<dt>
							编码：
						</dt>
						<dd>
							<input name="noticeCode" class="required" value="${backNotice.noticeCode }" type="text" alt="请输入编码" size="20" />
						</dd>
					</dl>
					<div class="divider"></div>
						<dl>
							<dt>
								状态：
							</dt>
							<dd>
								 <c:forEach items="${ALL_NOTICE_STATUS }" var="type" varStatus="count">
									<input name="noticeStatus"  type="radio" 
									<c:if test="${not empty backNotice.id and type.key eq backNotice.noticeStatus }">
										checked="checked"
										</c:if> 
									<c:if test="${empty backNotice.id and count.index==0 }">checked="checked"</c:if> 
									value="${type.key }" />${type.value }
								 </c:forEach>
							</dd>
						</dl>
						<dl>
							<dt>
								站内信：
							</dt>
							<dd>
								 <select name="message">
								 	 <c:forEach items="${ALL_MESSAGE }" var="type" varStatus="count">
										<option value="${type.key }"
										<c:if test="${not empty backNotice.id and type.key eq backNotice.message }">
										selected="selected"
										</c:if>
										>${type.value }</option>
								 	 </c:forEach>
								</select>
							</dd>
						</dl>
					<div class="divider"></div>
						<dl>
							<dt>
								邮箱：
							</dt>
							<dd>
								 <select name="email">
								 	 <c:forEach items="${ALL_EMAIL }" var="type" varStatus="count">
										<option value="${type.key }"
										<c:if test="${not empty backNotice.id and type.key eq backNotice.email }">
										selected="selected"
										</c:if>
										>${type.value }</option>
								 	 </c:forEach>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>
								短信：
							</dt>
							<dd>
								 <select name="phone">
								 	 <c:forEach items="${ALL_PHONE }" var="type" varStatus="count">
										<option value="${type.key }"
										<c:if test="${not empty backNotice.id and type.key eq backNotice.phone }">
										selected="selected"
										</c:if>
										>${type.value }</option>
								 	 </c:forEach>
								</select>
							</dd>
						</dl>
					<div class="divider"></div>
						<dl>
							<dt>
								排序：
							</dt>
							<dd>
								<input name="noticeSequence" class="digits" type="text"  value="${backNotice.noticeSequence}" />
							</dd>
						</dl>
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

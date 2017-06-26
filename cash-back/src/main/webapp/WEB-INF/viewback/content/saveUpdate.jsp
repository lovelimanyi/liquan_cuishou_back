<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>修改/添加内容</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="content/saveUpdate?parentId=${params.parentId }"
			onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" name="id" value="${content.id}">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>所属栏目：</label>
					<select name="channelType">
						<c:forEach items="${CHANNEL}" var="channel">
							<option value="${channel.key }" 
							<c:if test="${content.channelType eq channel.key}">selected="selected"</c:if>>${channel.value }</option>
						</c:forEach>
					</select>
				</p>
				<div class="divider"></div>
				<p>
					<label>内容标题：</label><input name="contentTitle" class="required"
						type="text" alt="请输入内容标题" size="30" value="${content.contentTitle }" maxlength="50"/>
				</p>
				<div class="divider"></div>
				<p style="height: 100px;">
					<label>内容摘要：</label>
					<textarea name="contentSummary" cols="50" rows="5" class="required" maxlength="200">${content.contentSummary }</textarea>
				</p>
				<div class="divider"></div>
				<p>
					<label>排序：</label> <input name="orderNum" class="required digits " min="0" type="text" value="${content.orderNum}"/>
				</p>
				<div class="divider"></div>
				<p style="height: 340px; width: 950px;">
					<label>内容：</label>
					<textarea name="contentTxt" class="editor"
						upImgUrl="upload/editorImg" cols="110" rows="15" tools="Cut,Copy,Paste,Pastetext,|,Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,SelectAll,Removeformat,|,Align,List,Outdent,Indent,|,Link,Unlink,Anchor,Img,Flash,Media,Hr,Table,|,Source,Preview,Print,Fullscreen">${content.contentTxt }</textarea>
				</p>
			</div>

			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
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

</body>
</html>

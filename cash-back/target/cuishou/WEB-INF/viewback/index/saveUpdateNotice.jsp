<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/common/back";
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
			<form method="post" method="post" enctype="multipart/form-data"
			action="indexManage/${params.url }?parentId=${params.parentId}" onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<c:if test="${not empty notice}">
					<input type="hidden" name="id" value="${params.id}" />
				</c:if>
				<div class="pageFormContent" layoutH="56">
					<div class="divider"></div>
					<p>
						<label>
							公告标题：
						</label>
						<input type="text" name="titleName" class="required" value="${notice.titleName }" maxlength="30" style="width: 50%" />
					</p>
					<div class="divider"></div>
					<p>
						<label>
							更多链接：
						</label>
						<input type="text" name="moreUrl" class="required isUrl" value="${notice.moreUrl }" maxlength="30" style="width: 50%" />
					</p>
					<div class="divider"></div>
					<p>
						<label>
							公告内容：
						</label>
						<textarea rows="10" cols="40" class="required" name="content">${notice.content }</textarea>
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

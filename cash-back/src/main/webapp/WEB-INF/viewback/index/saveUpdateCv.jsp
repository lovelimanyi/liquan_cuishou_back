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
			<form method="post" enctype="multipart/form-data"
			action="indexManage/saveUpdateCv" onsubmit="return validateCallback(this, navTabAjaxDone);"
				class="pageForm required-validate">
				<div class="pageFormContent" layoutH="56">
					<div class="divider"></div>
					<p>
						<label>
							简介标题：
						</label>
						<input type="text" name="cvName" class="required" value="${cv.cvName }" maxlength="30" style="width: 50%" />
					</p>
					<div class="divider"></div>
					<p>
						<label>
							更多链接：
						</label>
						<input type="text" name="moreUrl" class="required isUrl" value="${cv.moreUrl }" maxlength="30" style="width: 50%" />
					</p>
					<div class="divider"></div>
					<p>
						<label>
							简介内容：
						</label>
						<textarea rows="20" cols="95" class="required editor"  name="content">${cv.content }</textarea>
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

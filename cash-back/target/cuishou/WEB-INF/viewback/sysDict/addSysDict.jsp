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
			<form id="frm" method="post" enctype="multipart/form-data" action="sysDict/saveSysDict" onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="theParentId" value="${params.parentId}" />
				<div class="pageFormContent" layoutH="50" style="overflow: auto;">
					<dl>
						<dt style="width: 80px;">
							<label>
								数据值:
							</label>
						</dt>
						<dd>
							<input name="value" value="" minlength="1" maxlength="100" class="required" type="text" alt="请输入数据值" size="30" />
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								标签名:
							</label>
						</dt>
						<dd>
							<input name="label" value="" minlength="1" maxlength="100" class="required" type="text" alt="请输入标签名" size="30" />
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt style="width: 80px;">
							<label>
								类型:
							</label>
						</dt>
						<dd>
							<input name="type" value="" minlength="1" maxlength="100" class="required" type="text" alt="请输入类型" size="30" />
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								描述:
							</label>
						</dt>
						<dd>
							<input name="description" value="" minlength="1" maxlength="100" class="required" type="text" alt="请输入描述" size="30" />
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt style="width: 80px;">
							<label>
								排序:
							</label>
						</dt>
						<dd>
							<input name="sort" value="" minlength="1" maxlength="100" class="required" type="text" alt="请输入排序" size="30" />
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								备注信息:
							</label>
						</dt>
						<dd>
							<input name="remarks" value="" minlength="1" maxlength="100" type="text" alt="请输入备注信息" size="30" />
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
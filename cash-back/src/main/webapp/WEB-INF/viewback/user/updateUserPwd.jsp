<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>

        <script type="text/javascript">
            if('${sessionScope.BACK_USER.userPassword}' == 'E10ADC3949BA59ABBE56E057F20F883E') {
                $(function () {
                    $(".dialogHeader_c .close").remove();
                    $("#cancel").remove();
                });
            }
        </script>

	</head>
	<body>
		<div class="pageContent" id="test">
			<form id="frm" method="post" action="user/updateUserPassword"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<div class="pageFormContent" layoutH="56">
				<input type="hidden" value="${id }" name="id" />
					<!-- <p>
						<label>
							旧密码:
						</label>
						<input name="oldPassword" class="required" minlength="6"
							maxlength="20" type="password" size="30" />
					</p>
					<div class="divider"></div>-->
					<p>
						<label>
							新密码:
						</label>
						<input name="newPassword" id="cp_newPassword"
							class="required alphanumeric textInput valid" minlength="6"
							maxlength="16" type="password" size="30" />
					</p>
					<div class="divider"></div>
					<p>
						<label>
							重复输入新密码:
						</label>
						<input name="moduleName" equalto="#cp_newPassword"
							class="required alphanumeric textInput valid" type="password"
							size="30" />
					</p>
						<%--<p> <font style="color: #cd0a0a">温馨提示：</font><font style="color: #00be00">只有超级管理员和指定账号有权限修改密码,如需修改密码,请联系相关人员,谢谢!</font> </p>--%>
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
						<li id="cancel">
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
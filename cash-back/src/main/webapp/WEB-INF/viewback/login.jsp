<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath()+"";
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>现金侠催收管理系统</title>
	<link rel='icon' href='<%=path %>/admin-favicon.ico' type=‘image/x-ico’ />
	<script type="text/javascript" src="<%=path %>/common/back/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/DD_belatedPNG.js"></script>
	<!--<script type="text/javascript" src="<%=path %>/common/js/desencrypt.js"></script>-->
	<!--<script type="text/javascript" src="<%=path %>/common/js/tripledes.js"></script>-->
	<!--<script type="text/javascript" src="<%=path %>/common/js/mode-ecb.js"></script>-->

	<script>
        DD_belatedPNG.fix('.container,.sub,background');
	</script>

	<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			font-size: 12px;
			background:#0f579f url(<%=path %>/common/back/images/bg.jpg) top center no-repeat;
			color:#fff;
		}

		.input {
			width: 150px;
			height: 17px;
			border-top: 1px solid #404040;
			border-left: 1px solid #404040;
			border-right: 1px solid #D4D0C8;
			border-bottom: 1px solid #D4D0C8;
		}
	</style>
</head>
<body>
<div class="container">
	<form id="jvForm" name="jvForm" action="login" method="post" onsubmit="return login();">
		<table width="750" border="0" align="center" cellpadding="0"
			   cellspacing="0">
			<tr>
				<td height="200">&nbsp;</td>
			</tr>
			<tr>
				<td><table width="100%" border="0" cellspacing="0"
						   cellpadding="0">
					<tr>
						<td width="423" height="280" valign="top" ><table
								width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td style="height:280px;"><img src="<%=path %>/common/back/images/llogo.png" /></td>
							</tr>
						</table></td>
						<td width="40" align="center" valign="bottom"></td>
						<td valign="top" style="width:362px;heigth:340px; background-image:url('<%=path %>/common/back/images/dl_bg.png');" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="80" align="center" ><img src="<%=path %>/common/back/images/ltitle.png" /></td>
							</tr>
							<tr>
								<td>

									<table width="100%" border="0" align="center" cellpadding="0"
										   cellspacing="5">
										<tr>
											<td width="91" height="40" align="right"><strong>用户名：</strong></td>
											<td width="211"><input type="text" id="userAccount" name="userAccount"  maxlength="100" class="input" />
											</td>
										</tr>
										<tr>
											<td height="40" align="right"><strong>密&nbsp;&nbsp;码：</strong></td>
											<td><input name="userPassword" type="password" id="userPassword" maxlength="32"  class="input" /></td>
										</tr>
										<tr>
											<td height="40" align="right"><strong>验证码：</strong></td>
											<td><input maxlength="4" style="width:60px; margin-bottom: 10px;" name="captcha" type="text" id="captcha" class="input"  />
												<img style="margin-top: -5px;" src="<%=path %>/captcha.svl" onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" />
												<%-- <span style="width:100px; color:red; display:none;">${msg}</span> --%>
											</td>
										</tr>
										<tr>
											<td height="40" align="right"><strong>短信验证码：</strong></td>
											<td>
												<input name="smsCode" type="text" id="smsCode" maxlength="32"  class="input" style="width: 100px;" />
												<a href="javascript:;" id="sendCode" onclick="sendCode();"
												   style="display:inline-block; background: #fff; color: #ff0000; width: 74px; font-size: 12px; font-size: 12px; line-height: 30px; text-align: center;" >
													获取验证码</a>
											</td>
										</tr>
										<tr>
											<td height="80" colspan="2" align="center" class="sub">
												<input type="image" src="<%=path %>/common/back/images/login.png" name="submit" />&nbsp; &nbsp;&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
						</table></td>
					</tr>
				</table></td>
			</tr>
		</table>
	</form>
</div>
</body>
<script>

    function login(){
        if (jvForm.userAccount.value==""){
            alert("用户名不能为空！");
            return false;
        }else if (jvForm.userPassword.value==""){
            alert("密码不能为空！");
            return false;
        }else if (jvForm.captcha.value==""){
            alert("验证码不能为空！");
            return false;
        }else if (jvForm.smsCode.value==""){
            alert("短信验证码不能为空！");
            return false;
        }
        <!--    jvForm.password.value=encryptByDES(jvForm.password.value, jvForm.publickey.value);-->
    }
    var wait = 60;
    function time(htmlId, click) {
        if (wait == 0) {
            $("#" + htmlId).attr("onclick", click);
            $("#" + htmlId).text("获取验证码");
            wait = 60;
        } else {
            $("#" + htmlId).text(wait + "秒后重试");
            $("#" + htmlId).removeAttr("onclick");
            wait--;
            setTimeout(function() {
                time(htmlId, click);
            }, 1000);
        }
    }
    function sendCode() {
        if (jvForm.userAccount.value==""){
            alert("用户名不能为空！");
            return false;
        }else if (jvForm.userPassword.value==""){
            alert("密码不能为空！");
            return false;
        }else if (jvForm.captcha.value==""){
            alert("验证码不能为空！");
            return false;
        }else{
            $.ajax({
                type : "post",
                data:{
                    "userAccount":$("[name='userAccount']").val(),
                    "userPassword":$("[name='userPassword']").val(),
                    "RCaptchaKey":$("[name='RCaptchaKey']").val(),
                    "captcha":$("[name='captcha']").val()
                },
                url : "sendSmsBack",
                success : function(ret) {
                    if(ret.code == '200'){
                        time('sendCode','sendCode()');
                        alert(ret.msg);
                    }else{
                        $("#imgCap").trigger("click");
                        alert(ret.msg);
                    }
                },
                error:function(ret){
                    $("#imgCap").trigger("click");
                }
            });
        }
    }

    var msg="${message}";
    if(msg){
        alert(msg);
    }
</script>
</html>

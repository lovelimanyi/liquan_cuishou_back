<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加用户账号白名单</title>

    <script type="text/javascript">
        $($("#userAccount dd input").blur(function () {
            var account = $("#userAccount dd input").val();
            if (account != '') {
                $.ajax({
                    type: "POST",
                    url: "user/getUserByAccount",
                    dataType: 'json',
                    data: {"userAccount": account},
                    success: function (data) {
                        $("#userName dd input").val(data.userName);
                        $("#companyId").val(data.companyId);
                    }
                });
            }

        }));
    </script>

</head>
<body>
<div class="pageContent">
    <form id="frm" method="post" action="userAccountWhiteList/saveWhiteList"
          onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <input type="hidden" name="parentId" value="${params.parentId}"/>
        <input type="hidden" name="companyId" id="companyId"/>
        <div class="pageFormContent" layoutH="65">
            <dl id="userAccount">
                <dt style="width: 80px;">
                    <label>
                        账号:
                    </label>
                </dt>
                <dd>
                    <input name="userAccount" value="" minlength="1" maxlength="30" class="required" type="text" alt="请输入登录账号"/>
                </dd>
            </dl>
            <dl id="userName">
                <dt style="width: 80px;">
                    <label>
                        姓名:
                    </label>
                </dt>
                <dd>
                    <input name="userName" value="" minlength="1" maxlength="30" class="required" type="text" readonly="readonly"/>
                </dd>
            </dl>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit" id="submit">
                                添加
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
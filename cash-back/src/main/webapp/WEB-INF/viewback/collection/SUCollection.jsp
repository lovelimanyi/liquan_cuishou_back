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
    <title>Insert user</title>
</head>
<body>
<div class="pageContent">
    <form id="frm" method="post" enctype="multipart/form-data"
          action="collection/saveUpdateCollection"
          onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <input type="hidden" name="parentId" value="${params.parentId}"/>
        <input type="hidden" name="id" id="id" value="${collection.id }">
        <div class="pageFormContent" layoutH="50" style="overflow: auto;">
            <dl>
                <dt style="width: 80px;">
                    <label>
                        用户名:
                    </label>
                </dt>
                <dd>
                    <c:if test="${empty collection.id }">
                        <input name="userAccount" value="${collection.userAccount}" minlength="6" maxlength="16" class="required" type="text"
                               alt="请输入用户名" size="30"/>
                    </c:if>
                    <c:if test="${not empty collection.id }"> ${collection.userAccount } </c:if>
                </dd>
            </dl>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        默认密码:
                    </label>
                </dt>
                <dd>
                    <label style="width: 300px;">
                        <c:choose>
                            <c:when test="${not empty collection.userPassword}">
                                <input name="userPassword" value="${collection.userPassword}" class="required" type="hidden" alt="请输入用户真实姓名" size="30"/>
                            </c:when>
                            <c:otherwise>
                                <input name="userPassword" value="123456" class="required" type="hidden" alt="请输入用户真实姓名" size="30"/>
                            </c:otherwise>
                        </c:choose>
                        123456，登录后请尽快修改密码。
                    </label>
                </dd>
            </dl>
            <div class="divider"></div>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        真实姓名:
                    </label>
                </dt>
                <dd id="userName">
                    <input name="userName" value="${collection.userName}"
                           class="required" type="text" alt="请输入用户真实姓名" size="30"/>
                </dd>
            </dl>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        性别:
                    </label>
                </dt>
                <dd>
                    <input name="userSex" type="radio" checked="checked"
                           value="男"/>
                    男
                    <input name="userSex" type="radio" value="女"/>
                    女
                </dd>
                <c:if test="${not empty collection}">
                    <script type="text/javascript">
                        $("input[name='userSex'][value='${collection.userSex}']").attr("checked", true);
                    </script>
                </c:if>
            </dl>
            <div class="divider"></div>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        地址:
                    </label>
                </dt>
                <dd>
                    <input name="userAddress" value="${collection.userAddress }"
                           type="text" alt="请输入地址" size="30" value=""/>
                </dd>
            </dl>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        电话:
                    </label>
                </dt>
                <dd>
                    <input name="userTelephone" value="${collection.userTelephone }" type="text"
                           class="phone" alt="请输入电话" size="30"/>
                </dd>
            </dl>
            <div class="divider"></div>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        手机:
                    </label>
                </dt>
                <dd>
                    <input name="userMobile" value="${collection.userMobile }" type="text"
                           class="required phone" alt="请输入手机" size="30"/>
                </dd>
            </dl>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        邮箱:
                    </label>
                </dt>
                <dd>
                    <input name="userEmail" value="${collection.userEmail }" type="text"
                           class="required email" alt="请输入邮箱" size="30"/>
                </dd>
            </dl>
            <div class="divider"></div>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        QQ:
                    </label>
                </dt>
                <dd>
                    <input name="userQq" value="${collection.userQq }" class="digits"
                           type="text" alt="请输入qq" size="30"/>
                </dd>
            </dl>
            <div class="divider"></div>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        催收组:
                    </label>
                </dt>
                <dd>
                    <select name="groupLevel">
                        <c:forEach items="${groupNameMap}" var="map">
                            <option value="${map.key}"
                                    <c:if test="${collection.groupLevel eq map.key}">selected="selected"</c:if> >${map.value}</option>
                        </c:forEach>
                    </select>
                </dd>
            </dl>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        催收公司:
                    </label>
                </dt>
                <dd>
                    <select name="companyId">
                        <c:forEach var="company" items="${companyList}" varStatus="status">
                            <option value="${company.id}" <c:if test="${collection.companyId eq company.id}">selected="selected"</c:if>>${company.title}</option>
                        </c:forEach>
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
                    <select name="userStatus">
                        <option value="1" <c:if test="${collection.userStatus eq 1}">selected="selected"</c:if>>启用</option>
                        <option value="0" <c:if test="${collection.userStatus eq 0}">selected="selected"</c:if>>禁用</option>
                    </select>
                </dd>
            </dl>
            <div class="divider"></div>
            <p>
                <label>备注:</label>
                <textarea name="remark" rows="5" cols="60" maxlength="50">${collection.remark }</textarea>
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

    $("#userName input").blur(function () {
        var username = $("#userName input").val();
        if(username != ''){
            $.ajax({
                url: "/back/collection/checkCollections",
                data: {"userName":username},
                dataType: 'json',
                type: "POST",
                scriptCharset: "utf-8",
                success: function (data) {
                    debugger;
                    if ('200' == data.code) {
                        alertMsg.error("系统中已存在该催收员，请核实！");
                    }
                }
            });
        }
    });


</script>
</html>
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
    <title>短信验证码查询</title>
</head>
<body>
<div class="pageContent">
    <form id="frm" method="post" enctype="multipart/form-data" action="#" class="pageForm required-validate">
        <input type="hidden" name="parentId" value="${params.parentId}"/>
        <input type="hidden" name="id" id="id" value="${params.id }">
        <div class="pageFormContent" layoutH="50">
            <c:if test="${not empty msg}">
                <span style="color: #cd0a0a; margin-top: 20px;margin-left: 50px; ">${msg}</span>
            </c:if>
            <c:if test="${not empty code}">
                <span style="margin-top: 20px;margin-left: 50px;">手机号：<span style="font-size: 18px">${phoneNumber}</span> 本次验证码为 <span style="color: #cd0a0a;font-size:
                30px;">${code}</span></span>
            </c:if>
        </div>
        <div class="formBar">
            <ul>
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
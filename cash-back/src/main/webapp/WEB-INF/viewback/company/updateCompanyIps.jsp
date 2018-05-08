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
    <form id="frm" method="post" action="companyIpAddress/saveCompanyIpAddress?myId=${params.parentId}"
          onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <input type="hidden" name="parentId" value="${params.parentId}"/>
        <input type="hidden" name="id" value="${companyIpAddress.id}"/>
        <div class="pageFormContent" layoutH="65">
            <dl>
                <dt style="width: 80px;">
                    <label>
                        公司名称:
                    </label>
                </dt>
                <dd>
                    <select name="companyId">
                        <option value="">全部</option>
                        <c:forEach var="company" items="${companyList}">
                            <option value="${company.id}" <c:if test="${company.id eq companyIpAddress.companyId}">selected="selected"</c:if>>${company.title}</option>
                        </c:forEach>
                    </select>
                </dd>
            </dl>
            <dl>
                <dt style="width: 80px;">
                    <label id="ipAddress">
                        ip地址:
                    </label>
                </dt>
                <dd>
                    <input name="ipAddress" value="${companyIpAddress.ipAddress}" minlength="1" maxlength="30" class="required" type="text" alt="请输入ip地址" size="30"/>
                </dd>
            </dl>
            <div class="formBar">
                <ul>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit" id="submit">
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
        </div>
    </form>
</div>
</body>
</html>
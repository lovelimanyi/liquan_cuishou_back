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
    <title>催收代扣</title>
</head>
<body>
<div class="pageContent">
    <div class="inDiv" id="rong360" style="margin-top: 60px;margin-left: 50px">
        <a href="${rong360Url}" style="color: red">点击下载聚信立报告</a>
    </div>
    <c:if test="${not empty message}">
        <script type="text/javascript">
            $("#rong360").hide();
            alertMsg.error("${message}");
        </script>
    </c:if>
</div>
</body>
</html>
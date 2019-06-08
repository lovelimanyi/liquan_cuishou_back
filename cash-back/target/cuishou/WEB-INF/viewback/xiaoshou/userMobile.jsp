<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=7"/>
    <title>用户手机号</title>

</head>
<body>

<div class="pageContent">
    <form id="frm" method="post" enctype="multipart/form-data" action="" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <input type="hidden" name="parentId" value="${params.myId}"/>
        <div class="pageFormContent" layoutH="20" style="overflow: auto;">
            <label>用户手机号：${mobile}</label>
        </div>
        <%--<div>--%>
            <%--${mobile}--%>
        <%--</div>--%>
    </form>
</div>

</body>
</html>
<script type="text/javascript">

    $(function () {
        $.pdialog.resizeDialog({style: {width: 300, height: 300}}, $.pdialog.getCurrent(), "");
    });

</script>

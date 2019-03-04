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
    <title>修改用户意向</title>

</head>
<body>

<div class="pageContent">
    <form id="frm" method="post" enctype="multipart/form-data" action="xiaoShou/updateUserIntention?orderFrom=ymgj&myId=${params.myId}" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <input type="hidden" name="parentId" value="${params.myId}"/>
        <input type="hidden" name="id" id="id" value="${params.id}"/>
        <div class="pageFormContent" layoutH="20" style="overflow: auto;">
            <table>
                <tr>
                    <td></td>
                    <td></td>
                    <td>修改用户意向</td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                        <td>用户意向:
                            <br/>
                            <label><input name="userIntention" type="radio" value='1' <c:if test="${params.userIntention eq 1}">checked="checked"</c:if>/>有意向 </label><br/>
                            <label><input name="userIntention" type="radio" value='2' <c:if test="${params.userIntention eq 2}">checked="checked"</c:if>/>无意向 </label><br/>
                            <label><input name="userIntention" type="radio" value='3' <c:if test="${params.userIntention eq 3}">checked="checked"</c:if>/>未接通 </label><br/>
                        </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="button">
                            <div class="buttonContent">
                                <button type="button" class="close">
                                    取消
                                </button>
                            </div>
                        </div>
                    </td>
                    <td></td>
                    <td>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit" id="ok" onclick="submit">
                                    确定
                                </button>
                            </div>
                        </div>
                    </td>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>

</body>
</html>
<script type="text/javascript">

    $(function () {
        $.pdialog.resizeDialog({style: {width: 320, height: 200}}, $.pdialog.getCurrent(), "");
    });

</script>

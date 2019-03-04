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
    <title>添加备注</title>

</head>
<body>

<div class="pageContent">
    <form id="frm" method="post" enctype="multipart/form-data" action="xiaoShou/addRemark?orderFrom=ymgj&myId=${params.myId}" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <input type="hidden" name="parentId" value="${params.myId}"/>
        <input type="hidden" name="id" id="id" value="${params.id}"/>
        <div class="pageFormContent" layoutH="20" style="overflow: auto;">
            <table>
                <tr>
                    <td></td>
                    <td></td>
                    <td>添加备注</td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="5">
                        <textarea rows="5" cols="40" id="remark" name="remark" value="${params.remark}">${params.remark}</textarea>
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

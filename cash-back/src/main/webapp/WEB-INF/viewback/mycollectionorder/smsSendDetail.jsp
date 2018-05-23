<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <style type="text/css">
        #refreshMsg {
            margin-top: 85px;
            margin-left: 200px;
            width: 80px;
            height: 25px;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=7"/>
    <title>发送短信</title>
</head>
<body>
<div class="pageContent" id="dialog">
    <form id="frm" method="post" enctype="multipart/form-data"
          action="collectionOrder/sendMsg"
          onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <input type="hidden" name="parentId" value="${params.parentId}"/>
        <input type="hidden" name="id" id="id" value="${params.id }">
        <input type="hidden" name="orderId" id="orderId" value="${orderId}">
        <input type="hidden" name="msgId" id="msgId" value="${msgId}">
        <input type="hidden" name="phoneNumber" id="phoneNumber" value="${phoneNumber}">
        <div class="pageFormContent" layoutH="56">
            <%--<dl>
                <dt style="width: 80px;">
                    <label>
                        手机号:
                    </label>
                </dt>
                <dd>
                    <input name="phoneNumber" value="${userPhone}" maxlength="30" type="text" size="30" class="phone"/>
                </dd>
            </dl>--%>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        短信内容:
                    </label>
                </dt>
                <dd>
                    <textarea id="msgContent" name="msgContent" style="width: 450px;height: 60px;">${msgContent}</textArea>
                </dd>

            </dl>
            <c:if test="${not empty refreshMsg}">
                <button id="refreshMsg" name="refreshMsg" type="button">更换短信</button>
            </c:if>
            <%--<dl>
                <dt style="width: 120px;">
                    <label>
                        该订单已发短信数:
                    </label>
                </dt>
                <dd>
                    ${msgCount}
                </dd>
            </dl>--%>
            <%-- <dl>
                <dt style="width: 50px;">
                    <label>
                        短信详情:
                    </label>
                </dt>
                <dd>
                    <textarea id="msgContent" name="msgContent" readonly="readonly" value="${msgId }" style="width: 450px;height: 60px;"></textArea>
                </dd>
            </dl> --%>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="button" id="send" onclick="doCheck();">
                                发送
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
<script type="text/javascript">
    function doCheck() {
        alertMsg.confirm("您确认要发送短信吗?", {
            okCall: function () {
                $("#frm").submit();
            }
        });
    }

    $("#refreshMsg").click(function () {
        $.ajax({
            url: "collectionOrder/refreshMsg",
            type: "GET",
            data: {"id": $("#id").val()},
            dataType: "json",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success: function (data) {
                console.log(data);
                $("#msgContent").val(data.msgContent);
                $("#msgId").val(data.msgId);
            },
            error: function () {
                $("#msgContent").val("系统错误！");
            }
        })
    })
</script>
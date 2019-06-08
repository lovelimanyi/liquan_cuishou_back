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

        #msgContent {
            width: 400px;
            height: 100px;
            margin-left: 50px;
            margin-top: 50px;
        }

        #msgNotice {
            margin-left: 150px;
            margin-top: 200px;
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
            <dl>
                <dt style="width: 80px;">
                    <label>
                        短信模板:
                    </label>
                </dt>
                <dd>
                    <select name="msgTemplate" id="msgTemplate">
                        <option value="">请选择模板</option>
                        <c:forEach var="msg" items="${msgs}" varStatus="status">
                            <option value="${msg.id}" <%--<c:if test="${collection.companyId eq msg.id}">selected="selected"</c:if>--%>>${msg.name}</option>
                        </c:forEach>
                    </select>
                </dd>
                <dd>
                    <textarea id="msgContent" name="msgContent" readonly="readonly">${msgContent}</textArea>
                </dd>
            </dl>
            <div id="msgNotice">
                <span>每日可发送次数：${msgCountLimit} 次,今日剩余次数：<span style="color: #cd0a0a;">${remainMsgCount}</span> 次</span>
            </div>

            <%--<c:if test="${not empty refreshMsg}">--%>
            <%--<button id="refreshMsg" name="refreshMsg" type="button">更换短信</button>--%>
            <%--</c:if>--%>
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
        var msgTemplate = $("#msgTemplate").val();
        if (msgTemplate == "") {
            alertMsg.warn("请先选择短信模板");
            return;
        }
        alertMsg.confirm("您确认要发送短信吗?", {
            okCall: function () {
                $("#frm").submit();
            }
        });
    }

    $("#msgTemplate").change(function () {
        var msgTemplate = $("#msgTemplate").val();
        if (msgTemplate == "") {
            $("#msgContent").val("请选择短信模板！");
            return;
        }
        $.ajax({
            url: "collectionOrder/refreshMsg",
            type: "GET",
            data: {
                "id": $("#id").val(),
                "msgId": msgTemplate
            },
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
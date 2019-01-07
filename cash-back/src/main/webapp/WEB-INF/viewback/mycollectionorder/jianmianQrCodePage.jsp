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
    <title>减免还款二维码</title>

</head>
<body>

<div class="pageContent">
    <form id="frm" method="post" enctype="multipart/form-data" action="" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <input type="hidden" name="parentId" value="${params.myId}"/>
        <input type="hidden" name="id" id="id" value="${params.id }">
        <input type="hidden" name="loanId" id="loanId" value="${params.loanId}">
        <input type="hidden" name="payId" id="payId" value="${payId}">
        <div class="pageFormContent" layoutH="50" style="overflow: auto;">
            <dl>
                <dt style="width: 80px;">
                    <label>
                        还款金额:
                    </label>
                </dt>
                <dd>
                    <input type="text" id="payMoney" name="payMoney" class="required number" onfocus="hideErCode();">
                </dd>
            </dl>
            <div class="divider"></div>
            <dl id="erCode">
                <img height="300" width="300" id="qrCode" alt="还款金额不正确">
            </dl>
        </div>

        <div class="formBar">
            <ul>
                <div class="buttonActive">
                    <div class="buttonContent">
                        <button type="button" id="ok" onclick="doSubmit();">
                            确认
                        </button>
                    </div>
                </div>
            </ul>
        </div>
    </form>
</div>

</body>
</html>
<script type="text/javascript">

    $(function () {
        $.pdialog.resizeDialog({style: {width: 800, height: 370}}, $.pdialog.getCurrent(), "");

        $("#erCode").hide();
    });
    function hideErCode(){
        $("#erCode").hide();
    }
    function doSubmit() {
        debugger;
        var payMoney = $("#payMoney").val();

        var patrn = /^(-)?\d+(\.\d+)?$/;
        if (patrn.exec(payMoney) == null || payMoney == "") {
            alertMsg.warn("请输入数字");
            return false
        }
        var minMoney = ${minMoney};
        var remainMoney = ${remainMoney};
        if (payMoney > 3000) {
            alertMsg.warn("输入金额不能大于3000");
            return false;
        }else if (payMoney < minMoney){
            alertMsg.warn("输入金额不能小于应还本金");
            return false;
        }else if(payMoney > remainMoney) {
            alertMsg.warn("金额不能大于剩余应还金额");
            return false;
        }else {
            $("#qrCode").attr('src','collectionOrder/getQrCode?payId=${payId}'+'&payMoney='+payMoney);
            $("#erCode").show();
        }
    }
</script>

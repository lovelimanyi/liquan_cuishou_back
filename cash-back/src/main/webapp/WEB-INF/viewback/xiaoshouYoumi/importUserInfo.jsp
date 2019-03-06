<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
    #top span {
        text-align: center;
        float: left;
        font-size: 12px;
        font-family: 微软雅黑;
        overflow: auto;
    }

    #bottom span {
        text-align: center;
        width: 390px;
        float: left;
        line-height: 20px;
        font-size: 12px;
        font-family: 微软雅黑;
        overflow: auto;
    }
</style>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>订单导入</title>
</head>
<body>
<form name="form" action="xiaoShou/importExcelFromYoumi" enctype="multipart/form-data" method="post" onsubmit="return iframeCallback(this,dialogAjaxDone);">
    <input type="hidden" name="parentId" value="${params.parentId}"/>
    <input type="hidden" name="navTabId" value="${params.parentId}"/>
    <div id="top" class="pageFormContent" layoutH="120">
        <span>
            <input type="file" name="file" id="file" style="align-content: center"/>
        </span>
        <span>
            <input type="button" id="upload" value="导入订单" onclick="check()"/>
        </span>
        <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
        <span>
            <label style="width: 500px"></label>
            <input type="button" id="dispatcherOrderId" value="开始分单" onclick="dispatcherOrder()"/>
        </span>

        <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
        <div>
            温馨提示：请关闭其他标签页（客户总列表、我的客户列表页面），再进行导入操作
        </div>


    </div>
</form>
</body>

<script>
    function check() {
        var file;
        file = $("input[name=file]").val();
        if (file == '' || file == '') {
            alertMsg.warn("文件为空，请重新选择！");
            return false;
        }

        var files = document.getElementById("file").files;
        var fileName = files[0].name;
        if ((!fileName.endsWith(".xlsx")) && (!fileName.endsWith(".xls"))) {
            alertMsg.warn("文件类型错误，请重新选择！");
            return false;
        }
        var fileSize = files[0].size;
        if (fileSize > 1024 * 1024 * 4) {
            alertMsg.warn("导入文件大小不能操作4M！");
            return false;
        }
        $("form").submit();
    }


    //开始分单
    function dispatcherOrder() {
        // debugger;
        $.ajax({
            url: "xiaoShou/dispatcherOrder?orderFrom=ymgj",
            type: "GET",
            success: function (data) {
                alertMsg.info("已完成分单!");
            },
            error: function () {
                alertMsg.error("分单处理异常!")
            }
        })
    };
</script>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改</title>
</head>

<body>
<div class="pageContent" style="width: 505px">
    <form id="frm" method="post" enctype="multipart/form-data" action="dianxiao/updateDianXiaoOrder" onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <input type="hidden" name="parentId" value="${params.myId}"/>
        <input type="hidden" name="id" id="id" value="${params.id }">

        <div class="pageFormContent" layoutH=50 style="overflow: auto;">

            <dl>
                <dt style="width: 80px;">
                    <label>
                        公司名称:
                    </label>
                </dt>
                <dd style="width: 150px;">
                    <select name="repaymentIntention" style="width:150px " >
                        <option value="1" <c:if test="${'1' eq order.repaymentIntention}">selected = "selected"</c:if>>未接通</option>
                        <option value="2" <c:if test="${'2' eq order.repaymentIntention}">selected = "selected"</c:if>>已还款</option>
                        <option value="3" <c:if test="${'3' eq order.repaymentIntention}">selected = "selected"</c:if>>稍后还款</option>
                        <option value="4" <c:if test="${'4' eq order.repaymentIntention}">selected = "selected"</c:if>>下午还款</option>
                        <option value="5" <c:if test="${'5' eq order.repaymentIntention}">selected = "selected"</c:if>>晚上还款</option>
                        <option value="6" <c:if test="${'6' eq order.repaymentIntention}">selected = "selected"</c:if>>无还款意向</option>
                        <option value="7" <c:if test="${'7' eq order.repaymentIntention}">selected = "selected"</c:if>>挂电话</option>
                        <option value="8" <c:if test="${'8' eq order.repaymentIntention}">selected = "selected"</c:if>>过几天还款</option>
                        <option value="9" <c:if test="${'9' eq order.repaymentIntention}">selected = "selected"</c:if>>其他</option>
                    </select>
                </dd>

            </dl>
            <div class="divider"></div>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        备注:
                    </label>
                </dt>
                <dd style="width: 160px;">
                    <textarea id="remark" name="remark" rows="5" cols="21" maxlength="50" >${order.remark}</textarea>
                </dd>

            </dl>


        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">
                                确认修改
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
<script type="text/javascript">

    $(function () {
        $.pdialog.resizeDialog({style: {width: 710, height: 360}}, $.pdialog.getCurrent(), "");
    });

</script>
</html>

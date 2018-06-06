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
    <title>大额订单催收代扣</title>
</head>
<body>
<div class="pageContent">
    <form id="frm" method="post" enctype="multipart/form-data" action="collectionOrder/kokuan" onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <input type="hidden" name="parentId" value="${params.parentId}"/>
        <input type="hidden" name="id" id="id" value="${params.id }">
        <input type="hidden" name="borrowingType" id="borrowingType" value="1">
        <div class="pageFormContent" layoutH=50 style="overflow: auto;">
            <fieldset name="message" style="padding-bottom: 30px;height: 310px">
                <!-- 借款信息 -->
                <legend>欠款信息</legend>
                <table class="userTable">
                    <tr>
                        <dt style="width: 80px;">
                            <label>
                                欠款金额:
                            </label>
                        </dt>
                        <dd>
                            <label>
                                ${receivableMoney}
                            </label>
                        </dd>
                        <dt style="width: 80px;">
                            <label>
                                欠款本金:
                            </label>
                        </dt>
                        <dd>
                            <label>
                                ${loanMoney}
                            </label>
                        </dd>
                        <dt style="width: 80px;">
                            <label>
                                欠款滞纳金:
                            </label>
                        </dt>
                        <dd>
                            <label>
                                ${loanPenalty}
                            </label>
                        </dd>
                        <c:if test="${type eq 1}">
                            <dt style="width: 80px;">
                                <label>
                                    欠款利息:
                                </label>
                            </dt>
                            <dd>
                                <label>
                                        ${accrual}
                                </label>
                            </dd>
                        </c:if>
                        <dt style="width: 80px;">
                            <label>
                                已还金额:
                            </label>
                        </dt>
                        <dd>
                            <label>
                                ${realMoney}
                            </label>
                        </dd>

                        <dt style="width: 80px;color: red">
                            <label>
                                剩余待还金额:
                            </label>
                        </dt>
                        <dd style="color: red">
                            <label>
                                ${deductibleMoney}
                            </label>
                        </dd>
                        </td>
                    </tr>

                </table>
                <div class="divider"></div>
                <div class="pageFormContents" style="height: 220px;">
                    <dl>
                        <dt style="width: 400px;color: red">
                            &nbsp;&nbsp;
                        </dt>
                    </dl>
                    <div class="divider"></div>
                    <dl>
                        <dt style="width: 80px;">
                            <label>
                                代扣金额:
                            </label>
                        </dt>
                        <dd>
                            <input type="text" name="payMoney"  maxlength="16" class="number" value="${deductibleMoney}">
                        </dd>
                    </dl>
                    <div class="divider"></div>
                    <dl>
                        <dt style="width: 400px;color: red">
                            &nbsp;&nbsp;注意:
                            <br/>&nbsp;&nbsp;1.每次代扣金额不得小于50元
                            <br/>&nbsp;&nbsp;2.当日代扣操作失败次数大于2次后，不可再扣。请于次日重试
                        </dt>
                    </dl>
                </div>
            </fieldset>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">
                                确认代扣
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

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
    <title>订单详情</title>
    <style type="text/css">
        .userTable td {
            border-bottom: 1px solid #928989;
            border-right: 1px solid #928989;
            line-height: 31px;
            overflow: hidden;
            padding: 0 3px;
            vertical-align: middle;
            font-size: 14px;
        }

        .userTable td a {
            color: #5dacd0;
        }

        .userTable {
            padding: 0;
            border: solid 1px #928989;
            width: 100%;
            line-height: 21px;
        }

        .tdGround {
            background-color: #ededed;
        }

        .detailB th {
            border: 1px solid darkgray;
            color: #555;
            background: #f5ecec none repeat scroll 0 0;
            font-weight: bold;
            width: 100px;
            line-height: 21px;
        }

        .detailB td {
            border: 1px solid darkgray;
            font-weight: bold;
            width: 100px;
            line-height: 21px;
            text-align: center;
        }

        * {
            margin: 0;
            padding: 0;
            list-style: none;
        }

        #content {
            width: 500px;
            height: 170px;
            margin: 100px auto;
        }

        #imgbox-loading {
            position: absolute;
            top: 0;
            left: 0;
            cursor: pointer;
            display: none;
            z-index: 90;
        }

        #imgbox-loading div {
            background: #FFF;
            width: 100%;
            height: 100%;
        }

        #imgbox-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: #000;
            display: none;
            z-index: 80;
        }

        .imgbox-wrap {
            position: absolute;
            top: 0;
            left: 0;
            background: #FFF;
            display: none;
            z-index: 90;
        }

        .imgbox-img {
            padding: 0;
            margin: 0;
            border: none;
            width: 250%;
            height: 250%;
            vertical-align: top;
        }

        .imgbox-title {
            padding-top: 0px;
            font-size: 11px;
            text-align: center;
            font-family: Arial;
            color: #333;
            display: none;
        }

        .imgbox-bg-wrap {
            position: absolute;
            padding: 0;
            margin: 0;
            display: none;
        }

        .imgbox-bg {
            position: absolute;
            width: 20px;
            height: 20px;
        }
    </style>
</head>
<body>
<div class="pageContent">
    <div class="pageFormContent" layoutH="50" style="overflow: auto;">
        <!-- 借款信息 -->
        <fieldset>
            <legend>个人信息</legend>
            <table class="userTable">
                <tbody>
                <tr>
                    <td style="font-weight:bold">用户基本信息</td>
                    <td>
                        <table class="userTable">
                            <tr>
                                <td class="tdGround" style="width:180px;">借款人姓名:</td>
                                <td>${userInfo.realname}</td>
                                <td class="tdGround">借款人手机号码:</td>
                                <td>${userInfo.userPhone}</td>
                                <td class="tdGround">身份证号码:</td>
                                <td>${userInfo.idNumber}</td>
                            </tr>
                            <tr>
                                <td class="tdGround">银行名称:</td>
                                <td>${userCar.depositBank}</td>
                                <td class="tdGround">银行卡号:</td>
                                <td>${userCar.bankCard}</td>
                                <td class="tdGround">预留手机号码:</td>
                                <td>${userCar.mobile}</td>
                            </tr>

                            <tr>
                                <td class="tdGround">性别:</td>
                                <td colspan="2">${userInfo.userSex}</td>
                                <td class="tdGround">现居时长</td>
                                <td colspan="2">
                                    <c:choose>
                                        <c:when test="${userInfo.presentPeriod eq 1}">1~6个月</c:when>
                                        <c:when test="${userInfo.presentPeriod eq 2}">6~12个月</c:when>
                                        <c:when test="${userInfo.presentPeriod eq 3}">1年以上</c:when>
                                        <c:otherwise>--</c:otherwise>
                                    </c:choose>

                                </td>
                            </tr>
                            <tr>
                                <td class="tdGround">婚姻:</td>
                                <td colspan="2">
                                    <c:choose>
                                        <c:when test="${userInfo.maritalStatus eq 1}">未婚</c:when>
                                        <c:when test="${userInfo.maritalStatus eq 2}">已婚未育</c:when>
                                        <c:when test="${userInfo.maritalStatus eq 3}">未婚已育</c:when>
                                        <c:when test="${userInfo.maritalStatus eq 4}">离异</c:when>
                                        <c:when test="${userInfo.maritalStatus eq 5}">其他</c:when>
                                        <c:otherwise>--</c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="tdGround">学历</td>

                                <td colspan="2">
                                    <c:choose>
                                        <c:when test="${userInfo.education eq 1}">博士</c:when>
                                        <c:when test="${userInfo.education eq 2}">硕士</c:when>
                                        <c:when test="${userInfo.education eq 3}">本科</c:when>
                                        <c:when test="${userInfo.education eq 4}">大专</c:when>
                                        <c:when test="${userInfo.education eq 5}">中专</c:when>
                                        <c:when test="${userInfo.education eq 6}">高中</c:when>
                                        <c:when test="${userInfo.education eq 7}">初中及以下</c:when>
                                        <c:otherwise>--</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdGround" style="height: 100px;">身份证图片:</td>
                                <td colspan="2">
                                    <c:if test="${userInfo.idcardImgZ!=null}">
                                        <a id="img1" href="${userInfo.idcardImgZ}"><img src="${userInfo.idcardImgZ}"/></a>
                                    </c:if>
                                    <c:if test="${userInfo.idcardImgF!=null}">
                                        <a id="img2" href="${userInfo.idcardImgF}"><img src="${userInfo.idcardImgF}"/></a>
                                    </c:if>

                                </td>
                                <td class="tdGround" style="height: 100px;">个人名片:</td>
                                <td colspan="2">
                                    <c:if test="${userInfo.headPortrait!=null}">
                                        <img src="${userInfo.headPortrait}"/>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdGround">身份证地址:</td>
                                <td colspan="5">${userInfo.presentAddress}</td>
                            </tr>
                            <tr>
                                <td class="tdGround">现居住地址:</td>
                                <td colspan="5">${userInfo.presentAddress}${userInfo.presentAddressDistinct}</td>
                            </tr>

                        </table>
                    </td>
                </tr>
                <tr>
                    <td style="font-weight:bold">借款信息</td>
                    <td>
                        <table class="userTable">
                            <tr>
                                <td class="tdGround" style="width:180px;">欠款金额:</td>
                                <td>${userLoan.loanMoney+userLoan.loanPenalty+userLoan.serviceCharge+userLoan.accrual}</td>
                                <td class="tdGround">欠款本金:</td>
                                <td>${userLoan.loanMoney}</td>
                            </tr>
                            <tr>
                                <td class="tdGround">欠款滞纳金:</td>
                                <td>${userLoan.loanPenalty}</td>
                                <c:if test="${userLoan.borrowingType eq 2}">
                                    <td class="tdGround">欠款服务费:</td>
                                    <td>${userLoan.serviceCharge}</td>
                                </c:if>
                                <c:if test="${userLoan.borrowingType eq 1}">
                                    <td class="tdGround">欠款利息:</td>
                                    <td>${userLoan.accrual}</td>
                                </c:if>
                            </tr>
                            <tr>
                                <td class="tdGround">已还金额:</td>
                                <td>${payMonery}</td>
                                <td class="tdGround">逾期天数:</td>
                                <td>${collectionOrder.overdueDays}</td>
                            </tr>
                            <tr>
                                <td class="tdGround">借款时间:</td>
                                <td><fmt:formatDate value="${userLoan.loanStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td class="tdGround">应还时间:</td>
                                <td><fmt:formatDate value="${userLoan.loanEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>

                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
        </fieldset>
        <!-- 个人信息 -->
        <fieldset>
            <legend>还款详情</legend>
            <table class="detailB" width="100%">
                <tr>
                    <th align="center">序号</th>
                    <th align="left">实还本金</th>
                    <th align="center">实还罚息</th>
                    <th align="center">实还利息</th>
                    <th align="center">剩余应还本金</th>
                    <th align="center">剩余应还罚息</th>
                    <th align="center">剩余应还利息</th>
                    <th align="center">还款方式</th>
                    <th align="center">还款时间</th>
                </tr>
                <c:forEach var="pay" items="${detailList}" varStatus="status">
                    <tr>
                        <td>
                                ${status.count}
                        </td>
                        <td>
                                ${pay.realMoney}
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${pay.realPenlty}" pattern="0.00" maxFractionDigits="2"/>
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${pay.realgetAccrual}" pattern="0.00" maxFractionDigits="2"/>
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${pay.realPrinciple}" pattern="0.00" maxFractionDigits="2"/>
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${pay.realInterest}" pattern="0.00" maxFractionDigits="2"/>
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${pay.remainAccrual}" pattern="0.00" maxFractionDigits="2"/>
                        </td>
                        <td>
                            <c:if test="${pay.returnType eq '1' }">支付宝</c:if>
                            <c:if test="${pay.returnType eq '2' }">银行卡主动还款</c:if>
                            <c:if test="${pay.returnType eq '3' }">代扣</c:if>
                            <c:if test="${pay.returnType eq '4' }">对公银行卡转账</c:if>
                            <c:if test="${pay.returnType eq '5' }">线下还款</c:if>
                            <c:if test="${pay.returnType eq '6' }">减免</c:if>
                            <c:if test="${pay.returnType eq '99' }">大额减免</c:if>
                        </td>
                        <td>
                            <fmt:formatDate value="${pay.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </fieldset>
        <fieldset>
            <legend>代扣记录</legend>
            <table class="detailB" width="100%">
                <tr>
                    <th align="center">创建时间</th>
                    <th align="center">借款人姓名</th>
                    <th align="center">借款人电话</th>
                    <th align="center">催收状态</th>
                    <th align="center">欠款金额</th>
                    <%--<th align="center">已还金额</th>--%>
                    <th align="center">扣款金额</th>
                    <th align="center">扣款状态</th>
                    <th align="center">失败原因</th>
                    <th align="center">更新时间</th>
                </tr>
                <c:forEach var="withhold" items="${withholdList}" varStatus="status">
                    <tr>
                        <td>
                            <fmt:formatDate value="${withhold.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                                ${withhold.loanUserName }
                        </td>
                        <td>
                                ${withhold.loanUserPhone }
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${withhold.orderStatus eq 1 }">催收中</c:when>
                                <c:when test="${withhold.orderStatus eq 2}">承诺还款</c:when>
                                <c:when test="${withhold.orderStatus eq 3}">待催收（委外）</c:when>
                                <c:when test="${withhold.orderStatus eq 4}">催收成功</c:when>
                                <c:when test="${withhold.orderStatus eq 5}">续期</c:when>
                                <c:otherwise>待催收</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                                ${withhold.arrearsMoney}
                        </td>
                            <%--<td>
                                    ${withhold.hasalsoMoney }
                            </td>--%>
                        <td>
                                ${withhold.deductionsMoney }
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${withhold.status eq 0}">申请中</c:when>
                                <c:when test="${withhold.status eq 1}">成功</c:when>
                                <c:when test="${withhold.status eq 2}">失败</c:when>
                            </c:choose>
                        </td>
                        <td>
                                ${withhold.remark }
                        </td>
                        <td>
                            <fmt:formatDate value="${withhold.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </fieldset>

    </div>
</div>
</body>
</html>

<script type="text/javascript">
    $(document).ready(function () {
        $("#img1").imgbox({
            'speedIn': 0,
            'speedOut': 0,
            'alignment': 'top',
            'overlayShow': true,
            'allowMultiple': false
        });

        $("#img2").imgbox({
            'speedIn': 0,
            'speedOut': 0,
            'alignment': 'top',
            'overlayShow': true,
            'allowMultiple': false
        });
        if (${empty userInfo.idcardImgZ}) {
            $("#img1").hide();
        }
        if (${empty userInfo.idcardImgF}) {
            $("#img2").hide();
        }
    });
    //var a = 'asdfsdfsdfsadf';
    //a=a.split('');  //将a字符串转换成数组
    //a.splice(1,1,'xxxxx'); //将1这个位置的字符，替换成'xxxxx'. 用的是原生js的splice方法。
    //console.log(a);   //结果是：
    //["a", "xxxxx", "d", "f", "s", "d", "f", "s", "d", "f", "s", "a", "d", "f"]
    //
    //a.join('');
</script>

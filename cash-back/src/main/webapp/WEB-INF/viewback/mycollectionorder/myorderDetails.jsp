<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = path + "/common/back";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=7"/>
    <title>订单详情</title>
    <link rel="stylesheet" href="<%=basePath%>/css/myOrderDetailCss.css">

    <script type="text/javascript" src="<%=basePath%>/js/imgjs/postbird-img-glass.min.js"/>
    <script type="text/javascript" src="<%=basePath%>/js/orderDetail/myOrderDetailJs.js"/>
</head>
<body>
<div style="height:800px;overflow:auto;">
    <%--<div class="pageContent">--%>
    <input type="hidden" value="${params.id}">
    <input type="hidden" id="orderId" value="${params.id}">
    <input type="hidden" id="orderLoanId" value="${collectionOrder.loanId}">
    <input type="hidden" id="orderStatus" value="${collectionOrder.status}">
    <input type="hidden" id="parentId" value="${params.parentId}">
    <input type="hidden" id="phoneNumber" value="${collectionOrder.loanUserPhone}">
    <input type="hidden" id="userId" value="${collectionOrder.userId}">
    <input type="hidden" id="contactId">
    <input type="hidden" id="collectionRecordId">
    <%-- 标记用户选择的是否是紧急联系人 --%>
    <input type="hidden" id="isCloseRelation">
    <div class="tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li class="selected"><a href="#"><span>订单详情</span></a></li>
                    <li><a href="#" onclick="getUserRepayInfo()"><span>还款信息</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent" style="height:305px;">
            <div class="pageFormContent" style="overflow: auto;">
                <!-- 借款信息 -->
                <fieldset>
                    <legend>个人信息</legend>
                    <%--<table class="userTable">
                        <tbody>
                        <tr>
                            <td style="font-weight:bold">用户基本信息</td>
                            <td>--%>
                    <table class="userTable">
                        <tr>
                            <td class="tdGround" style="width:80px;">借款人姓名:</td>
                            <td>${userInfo.realname}</td>
                            <%--<td class="tdGround">借款人手机号码:</td>
                            <td>${userInfo.userPhone}</td>--%>
                            <td class="tdGround">身份证号码:</td>
                            <td>${userInfo.idNumber}</td>
                            <td class="tdGround">性别:</td>
                            <td colspan="2">${userInfo.userSex}</td>
                        </tr>
                        <tr>
                            <td class="tdGround">借款人手机:</td>
                            <td>${userInfo.userPhones}</td>
                            <%--<td class="tdGround">现居时长</td>
                            <td>
                                <c:choose>
                                    <c:when test="${userInfo.presentPeriod eq 1}">1~6个月</c:when>
                                    <c:when test="${userInfo.presentPeriod eq 2}">6~12个月</c:when>
                                    <c:when test="${userInfo.presentPeriod eq 3}">1年以上</c:when>
                                    <c:otherwise>--</c:otherwise>
                                </c:choose>

                            </td>--%>
                            <td class="tdGround">婚姻:</td>
                            <td colspan="1">
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
                            <td class="tdGround">身份证地址:</td>
                            <td>${userInfo.presentAddress}</td>
                            <td class="tdGround">现居住地址:</td>
                            <td colspan="5">${userInfo.presentAddress}${userInfo.presentAddressDistinct}</td>
                        </tr>
                        <tr>
                            <td class="tdGround" style="height: 116px;border-bottom: 0px;">身份证图片:</td>
                            <td colspan="7" style="border-bottom: 0px;">
                                <div style="margin: 2px 200px 2px;" id="userPhoto">
                                    <c:if test="${userInfo.idcardImgZ!=null}">
                                        <img id="imgZ" class="img-container" src="${userInfo.idcardImgZ}"/>
                                    </c:if>
                                    <c:if test="${userInfo.idcardImgF!=null}">
                                        <img id="imgF" class="img-container" src="${userInfo.idcardImgF}"/>
                                    </c:if>
                                    <c:if test="${userInfo.headPortrait!=null}">
                                        <img class="img-container" src="${userInfo.headPortrait}"/>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </div>

            <div>
                <!-- 还款信息 -->
                <fieldset>
                    <legend>还款信息</legend>
                    <table class="repayTable">
                        <tbody id="repayInfo">
                        </tbody>
                    </table>
                </fieldset>
                <!-- 还款详情 -->
                <fieldset>
                    <legend>还款详情</legend>
                    <table class="detailB" width="100%">
                        <thead>
                        <tr>
                            <th align="center">序号</th>
                            <th align="center">实还本金</th>
                            <th align="center">实还罚息</th>
                            <th align="center">实还利息</th>
                            <th align="center">剩余应还本金</th>
                            <th align="center">剩余应还罚息</th>
                            <th align="center">剩余应还利息</th>
                            <th align="center">还款方式</th>
                            <th align="center">还款时间</th>
                        </tr>
                        </thead>
                        <tbody id="payDetail">
                        </tbody>
                    </table>
                </fieldset>
                <!-- 代扣信息 -->
                <fieldset>
                    <legend>代扣记录</legend>
                    <table class="detailB" width="100%">
                        <thead>
                        <tr>
                            <th align="center">创建时间</th>
                            <th align="center">借款人姓名</th>
                            <th align="center">借款人电话</th>
                            <th align="center">催收状态</th>
                            <th align="center">欠款金额</th>
                            <th align="center">已还金额</th>
                            <th align="center">扣款金额</th>
                            <th align="center">扣款状态</th>
                            <th align="center">失败原因</th>
                            <th align="center">更新时间</th>
                        </tr>
                        </thead>
                        <tbody id="withholdDetail">
                        </tbody>
                    </table>
                </fieldset>
            </div>
        </div>
    </div>


    <div class="tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent" id="collectionZone">
                <ul>
                    <li class="selected" onclick='getCollectionLists();'><a href="#"><span>催收记录</span></a></li>
                    <%--<li><a href="#"><span>通话记录</span></a></li>--%>
                    <li id="addressList"><a href="#" onclick="getUserRealContent();"><span>通讯录</span></a></li>
                    <li id="orderStatusChangeLog"><a href="#" onclick="getStatusChangeLogContent();"><span>订单流转日志</span></a></li>
                    <li id="jxlLable"><a href="#" onclick="getJxlContent();"><span>聚信立报告</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent" id="jxlTabs" style="height:300px;">
            <div>
                <fieldset>
                    <legend>催收记录</legend>
                    <div class="pageContent" id="collectionTable">
                        <div class="pageHeader">
                            <div class="searchBar">
                                <table class="searchContent">
                                    <tr>
                                        <td>
                                            催收组(逾期等级):
                                            <select name="overdueLevel">
                                                <option value="">全部</option>
                                                <c:forEach var="overdueLevel" items="${overdueLevelMap}">
                                                    <option value="${overdueLevel.key}">${overdueLevel.value}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            催收状态:
                                            <select name="collectionStatus">
                                                <option value="">全部</option>
                                                <c:forEach var="status" items="${orderStatusMap}">
                                                    <option value="${status.key}">${status.value}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            催收类型:
                                            <select name="collectionType">
                                                <option value="">全部</option>
                                                <option value="1">电话催收</option>
                                                <option value="2">短信催收</option>
                                            </select>
                                        </td>
                                        <td>
                                            催&nbsp;&nbsp;收&nbsp;&nbsp;员:
                                            <input type="text" name="collectionName" value="${params.collectionName }" style="width: 60px;"/>
                                        </td>
                                        <td>
                                            沟通情况:
                                            <select name="communicationSituation">
                                                <option value="">全部</option>
                                                <c:forEach var="status" items="${communicationSituationsMap}">
                                                    <option value="${status.key}">${status.value}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <div class="buttonActive" style="margin-left: 180px;">
                                                <div class="buttonContent">
                                                    <button type="button" id="searchOrderCollectionRecord">
                                                        查询
                                                    </button>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div id="recordListContent"></div>
                    </div>
                </fieldset>
            </div>
            <%--<div>通话记录</div>--%>
            <div>
                <fieldset>
                    <legend>通讯录</legend>
                    <div class="pageContent" id="userRealContent">
                    </div>
                </fieldset>
            </div>
            <div>
                <fieldset>
                    <legend>流转日志</legend>
                    <div class="pageContent" id="statusChangeLog">
                    </div>
                </fieldset>
            </div>
            <div id="jxl">
            </div>
        </div>
    </div>

    <div class="tabs" id="collectionRecord">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li class="selected"><a href="#"><span>添加催记</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent" style="height:300px;">
            <div class="pageContent" style="width: 100%;">
                <div class="pageFormContent" style="overflow: auto;">
                    <fieldset>
                        <dl>
                            <dt style="width: 80px;">
                                <label>
                                    催收类型:
                                </label>
                            </dt>
                            <dd>
                                <select class="required" name="collectionMode">
                                    <option value="1">电话催收</option>
                                    <option value="2">短信催收</option>
                                </select>
                            </dd>
                        </dl>
                    </fieldset>
                    <fieldset>
                        <dl id="promisePay">
                            <dt style="width: 80px;">
                                <label>
                                    承诺还款:
                                </label>
                            </dt>
                            <dd style="column-span: 1;">
                                <span><input type="radio" name="promiseRepay" value="1"/>是</span>
                                <span style="margin-left: 50px;"><input id="notPromiseRepay" type="radio" name="promiseRepay" checked="checked" value="0"/>否</span>
                            </dd>

                        </dl>
                    </fieldset>
                    <div id="promiseRepayTime">
                        <fieldset>
                            <dl>
                                <dt style="width: 80px;">
                                    <label>
                                        承诺还款时间:
                                    </label>
                                </dt>
                                <dd>
                                    <input type="text" id="repaymentTime" name="repaymentTime" value="" class="date textInput readonly" datefmt="yyyy-MM-dd"
                                           readonly="readonly"/>
                                </dd>
                            </dl>
                        </fieldset>
                    </div>
                    <div id="collectionWithPhone">
                        <fieldset>
                            <dl>
                                <dt style="width: 80px;">
                                    <label>
                                        是否接通:
                                    </label>
                                </dt>
                                <dd style="width: 500px">
                                    <span><input type="radio" name="isConnected" value="1"/>接通</span>
                                    <span style="margin-left: 30px;"><input type="radio" name="isConnected" value="2"/>无人接听</span>
                                    <span style="margin-left: 30px;"><input type="radio" name="isConnected" value="3"/>空号/停机/关机</span>
                                    <span style="margin-left: 30px;"><input type="radio" name="isConnected" value="4"/>拒接/挂断/拉黑</span>
                                </dd>
                            </dl>
                        </fieldset>
                        <div id="communicate">
                            <fieldset>
                                <dl>
                                    <dt style="width: 80px;">
                                        <label>
                                            沟通情况:
                                        </label>
                                    </dt>
                                    <dd style="width: 500px">
                                        <span><input type="radio" name="communication" value="1"/>无偿还意愿</span>
                                        <span style="margin-left: 40px;"><input type="radio" name="communication" value="2"/>无偿还能力</span>
                                        <span style="margin-left: 40px;"><input type="radio" name="communication" value="3"/>虚假通讯录</span>
                                    </dd>
                                    <dd style="width: 500px">
                                        <span style="margin-left: 90px;"><input type="radio" name="communication" value="4"/>虚假个人信息</span>
                                        <span style="margin-left: 28px;"><input type="radio" name="communication" value="5"/>有偿还意愿</span>
                                        <span style="margin-left: 40px;"><input type="radio" name="communication" value="6"/>亲友答应转告</span>
                                    </dd>
                                    <dd style="width: 500px">
                                        <span style="margin-left: 90px;"><input type="radio" name="communication" value="7"/>亲友拒绝沟通</span>
                                        <span style="margin-left: 28px;"><input type="radio" name="communication" value="8"/>无</span>
                                    </dd>
                                </dl>
                            </fieldset>
                        </div>
                        <fieldset>
                            <div class="divider"></div>
                            <dl>
                                <dt style="width: 80px;">
                                    <label>
                                        催收内容:
                                    </label>
                                </dt>
                                <dd>
                                    <textarea name="content" rows="5" cols="80" maxlength="100" id="collectionContent"></textarea>
                                    <div style="width: 500px;height: 20px;margin-top: 100px;">
                                        <font style="color: #1b8d0f">温馨提示：</font><font style="color: #cd0a0a">如果不点选催收记录或通讯录中联系人则默认催收本人。</font>
                                    </div>
                                </dd>
                            </dl>
                        </fieldset>
                    </div>
                    <div id="collectionWithMsg">
                        <div class="pageFormContent">
                            <dl>
                                <dt style="width: 80px;">
                                    <label>
                                        短信模板:
                                    </label>
                                </dt>
                                <dd>
                                    <select name="msgTemplate" id="msgTemplate">
                                        <option value="">请选择模板</option>
                                        <c:forEach var="msg" items="${msgs}">
                                            <option value="${msg.id}">${msg.name}</option>
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
                        </div>
                    </div>
                </div>
                <ul>
                    <li>
                        <button style="margin-left: 110px;width: 70px;height: 25px;" type="button" id="addCollectionRecord">
                            添加催记
                        </button>
                    </li>
                </ul>
                <div style="height: 50px;"></div>
            </div>
        </div>
    </div>
</div>
</body>

</html>

<script type="text/javascript">
    /*
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
     */
    //var a = 'asdfsdfsdfsadf';
    //a=a.split('');  //将a字符串转换成数组
    //a.splice(1,1,'xxxxx'); //将1这个位置的字符，替换成'xxxxx'. 用的是原生js的splice方法。
    //console.log(a);   //结果是：
    //["a", "xxxxx", "d", "f", "s", "d", "f", "s", "d", "f", "s", "a", "d", "f"]
    //
    //a.join('');
</script>

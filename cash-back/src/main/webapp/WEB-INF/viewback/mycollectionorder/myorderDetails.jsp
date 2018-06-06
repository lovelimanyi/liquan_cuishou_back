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
                            <td class="tdGround">借款人手机号码:</td>
                            <td>${userInfo.userPhone}</td>
                            <td class="tdGround">身份证号码:</td>
                            <td>${userInfo.idNumber}</td>
                            <td class="tdGround">性别:</td>
                            <td colspan="2">${userInfo.userSex}</td>
                        </tr>
                        <tr>
                            <td class="tdGround">预留手机号:</td>
                            <td>${userCar.mobile}</td>
                            <td class="tdGround">现居时长</td>
                            <td>
                                <c:choose>
                                    <c:when test="${userInfo.presentPeriod eq 1}">1~6个月</c:when>
                                    <c:when test="${userInfo.presentPeriod eq 2}">6~12个月</c:when>
                                    <c:when test="${userInfo.presentPeriod eq 3}">1年以上</c:when>
                                    <c:otherwise>--</c:otherwise>
                                </c:choose>

                            </td>
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
                                <div style="margin: 2px 200px 2px;">
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
            <div class="tabsHeaderContent">
                <ul>
                    <li class="selected" onclick='script:$("#collectionRecord").show();'><a href="#"><span>催收记录</span></a></li>
                    <li><a href="#"><span>通话记录</span></a></li>
                    <li><a href="#" onclick="getUserRealContent();"><span>通讯录</span></a></li>
                    <li><a href="#" onclick="getJxlContent();"><span>聚信立报告</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent" id="jxlTabs" style="height:300px;">
            <div>
                <fieldset>
                    <legend>催收记录</legend>
                    <div class="pageContent">
                        <table class="table" style="width: 100%;" nowrapTD="false">
                            <thead>
                            <tr>
                                <th align="center" width="20">
                                    <input type="checkbox" id="checkAlls"/>
                                </th>
                                <th align="center" width="30">序号</th>
                                <th align="center" width="40">借款编号</th>
                                <th align="center" width="50">借款人</th>
                                <th align="center" width="60">联系人姓名</th>
                                <th align="center" width="80">联系人电话</th>
                                <th align="center" width="80">当前催收状态</th>
                                <th align="center" width="120">催收时间</th>
                                <th align="center" width="80">催收组</th>
                                <th align="center" width="80">催收员</th>
                                <th align="center" width="60">催收建议</th>
                                <th align="center" width="200">风控标签</th>
                                <th align="center" width="180">催收内容</th>
                                <th align="center" width="180">催收建议备注</th>
                            </tr>
                            </thead>
                            <tbody id="generateContent">

                            <c:forEach var="record" items="${recordList }" varStatus="status">
                                <tr target="recordId" rel="${record.id }">
                                    <td>
                                        <input type="checkbox" name="checkItem" value="${record.id}"/>
                                    </td>
                                    <td>${status.count}</td>
                                    <td>${record.orderId}</td>
                                    <td>${record.userId}</td>
                                    <td>${record.contactName }</td>
                                    <td>${record.contactPhone }</td>
                                    <td>
                                        <c:if test="${record.orderState == '0'}">待催收</c:if>
                                        <c:if test="${record.orderState == '1'}">催收中</c:if>
                                        <c:if test="${record.orderState == '2'}">承诺还款</c:if>
                                        <c:if test="${record.orderState == '3'}">委外中</c:if>
                                        <c:if test="${record.orderState == '4'}">催收成功</c:if>
                                    </td>
                                    <td><fmt:formatDate value="${record.collectionDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>
                                        <c:if test="${record.collectionGroup == '1'}">超级管理员</c:if>
                                        <c:if test="${record.collectionGroup == '3'}">S1组</c:if>
                                        <c:if test="${record.collectionGroup == '4'}">S2组</c:if>
                                        <c:if test="${record.collectionGroup == '5'}">M1-M2组</c:if>
                                        <c:if test="${record.collectionGroup == '6'}">M2-M3组</c:if>
                                        <c:if test="${record.collectionGroup == '7'}">M3+</c:if>
                                    </td>
                                    <td>${record.collectionPerson }</td>
                                    <td>
                                        <c:if test="${record.collectionAdvice == '1'}">通过</c:if>
                                        <c:if test="${record.collectionAdvice == '2'}">拒绝</c:if>
                                        <c:if test="${record.collectionAdvice == '3'}">无建议</c:if>
                                    </td>
                                    <td>${record.fengKongLabel}</td>
                                    <td>${record.content }</td>
                                    <td>${record.collectionAdviceRemark }</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </fieldset>
            </div>
            <div>通话记录</div>
            <div>
                <fieldset>
                    <legend>通讯录</legend>
                    <div class="pageContent">
                        <table class="table" style="width: 100%;" nowrapTD="false">
                            <thead>
                            <tr>
                                <th align="center" width="30">序号</th>
                                <th align="center" width="20">借款用户</th>
                                <th align="center" width="40">联系人类型</th>
                                <th align="center" width="25">联系人关系</th>
                                <th align="center" width="20">联系人姓名</th>
                                <th align="center" width="30">归属地</th>
                                <th align="center" width="20">联系次数</th>
                                <th align="center" width="20">主叫次数</th>
                                <th align="center" width="20">被叫次数</th>
                            </tr>
                            </thead>
                            <tbody id="userRealContent">
                            <%--<c:forEach var="userReal" items="${mmanUserRelaList }" varStatus="status">
                                <tr target="userRealId" rel="${userReal.id }">
                                    <td>${status.count}</td>
                                    <td>${userReal.realName}</td>
                                    <td>
                                        <c:if test="${MmanUserRela.contactsKey eq '1' }">直系亲属联系人</c:if>
                                        <c:if test="${MmanUserRela.contactsKey eq '2' }">其他联系人</c:if>
                                    </td>
                                    <td>
                                        <c:if test="${userReal.contactsKey eq '1' }">
                                            <c:if test="${userReal.relaKey eq '1' }">父亲</c:if>
                                            <c:if test="${userReal.relaKey eq '2' }">母亲</c:if>
                                            <c:if test="${userReal.relaKey eq '3' }">儿子</c:if>
                                            <c:if test="${userReal.relaKey eq '4' }">女儿</c:if>
                                            <c:if test="${userReal.relaKey eq '5' }">配偶</c:if>
                                        </c:if>
                                        <c:if test="${userReal.contactsKey eq '2' }">
                                            <c:if test="${userReal.relaKey eq '1' }">同学</c:if>
                                            <c:if test="${userReal.relaKey eq '2' }">亲戚</c:if>
                                            <c:if test="${userReal.relaKey eq '3' }">同事</c:if>
                                            <c:if test="${userReal.relaKey eq '4' }">朋友</c:if>
                                            <c:if test="${userReal.relaKey eq '5' }">其他</c:if>
                                        </c:if>
                                    </td>
                                    <td>${userReal.infoName}</td>
                                    <td>${userReal.phoneNumLoc}</td>
                                    <td>${userReal.callCnt}</td>
                                    <td>${userReal.callOutCnt}</td>
                                    <td>${userReal.callInCnt}</td>
                                </tr>
                            </c:forEach>--%>
                            </tbody>
                        </table>
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
            <div class="pageContent" style="width: 80%;">
                <form id="frm" method="post" enctype="multipart/form-data" action="collectionOrder/addRecordAndAdvice" onsubmit="return validateCallback(this, dialogAjaxDone);"
                      class="pageForm required-validate">
                    <input type="hidden" name="parentId" value="${params.parentId}"/>
                    <input type="hidden" name="id" id="id" value="${params.id }">
                    <input type="hidden" name="type" id="type" value="${params.type }"/>
                    <div class="pageFormContent" style="overflow: auto;">
                        <fieldset>
                            <dl>
                                <dt style="width: 80px;">
                                    <label>
                                        催收类型:
                                    </label>
                                </dt>
                                <dd>
                                    <select class="required" name="collectionType">
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
                                    <span style="margin-left: 50px;"><input type="radio" name="promiseRepay" checked="checked" value="0"/>否</span>
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
                                        <textarea name="content" rows="5" cols="80" maxlength="100"></textarea>
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
                                            <c:forEach var="msg" items="${msgs}" varStatus="status">
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
                    <div class="formBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="send">
                                            保存
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
        </div>
    </div>
</div>
</body>

</html>

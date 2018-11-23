<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<style type="text/css">
    div .dialogContent .layoutBox .unitBox {
        width: 1000px;
        height: 450px;
    }

    a {

    }
</style>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="collectionOrder/getListCollectionOrder?myId=${params.myId}" method="post">
    <div class="pageHeader">
        <input type="hidden" id="parentId" name="parentId" value="${params.myId}">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>借 款 编 号: <input type="text" id="loanId" name="loanId" value="${params.loanId}"/></td>
                    <td>借款人姓名: <input type="text" id="loanRealName" name="loanRealName" value="${params.loanRealName}"/></td>
                    <td>借款人手机: <input type="text" id="loanUserPhone" name="loanUserPhone" value="${params.loanUserPhone}"/></td>
                    <td>跟 进 等 级:
                        <select name="topImportant" id="topImportant">
                            <option value="">全部</option>
                            <c:forEach var="company" items="${levellist }">
                                <option value="${company.value }" <c:if test="${company.value eq params.topImportant}">selected="selected"</c:if>>
                                        ${company.label}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        催 收 时 间:
                        <input type="text" id="collectionBeginTime" name="collectionBeginTime" value="${params.collectionBeginTime}" class="date textInput readonly"
                               datefmt="yyyy-MM-dd" readonly="readonly"/>
                        至
                        <input type="text" id="collectionEndTime" name="collectionEndTime" value="${params.collectionEndTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"
                               readonly="readonly"/>
                    </td>
                    <td>
                        派 单 时 间:
                        <input type="text" id="dispatchBeginTime" name="dispatchBeginTime" value="${params.dispatchBeginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"
                               readonly="readonly"/>
                        至
                        <input type="text" id="dispatchEndTime" name="dispatchEndTime" value="${params.dispatchEndTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"
                               readonly="readonly"/>
                    </td>
                    <td>逾 期 天 数: <input type="text" id="overDueDaysBegin" name="overDueDaysBegin" value="${params.overDueDaysBegin}"/>至<input type="text" id="overDueDaysEnd"
                                                                                                                                              name="overDueDaysEnd"
                                                                                                                                              value="${params.overDueDaysEnd}"/>
                    </td>
                    <c:if test="${userGropLeval ne '10021'}">
                        <c:choose>
                            <c:when test="${not empty params.CompanyPermissionsList}">
                                <td>催 收 公 司:
                                    <select name="companyId" id="companyId">
                                        <option value="">全部</option>
                                        <c:forEach var="company" items="${ListMmanLoanCollectionCompany }">
                                            <c:forEach var="companyViw" items="${params.CompanyPermissionsList}">
                                                <c:if test="${companyViw.companyId eq company.id}">
                                                    <option value="${company.id }" <c:if test="${company.id eq params.companyId}">selected="selected"</c:if>>
                                                            ${company.title}
                                                    </option>
                                                </c:if>
                                            </c:forEach>
                                        </c:forEach>
                                    </select>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>催 收 公 司:
                                    <select name="companyId" id="companyId">
                                        <option value="">全部</option>
                                        <c:forEach var="company" items="${ListMmanLoanCollectionCompany }">
                                            <option value="${company.id }" <c:if test="${company.id eq params.companyId}">selected="selected"</c:if>>
                                                    ${company.title}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </tr>
                <tr>
                    <td>催&nbsp;&nbsp;&nbsp;&nbsp;收&nbsp;&nbsp;&nbsp;组:
                        <select name="collectionGroup" id="collectionGroup">
                            <c:forEach var="group" items="${dictMap }">
                                <option value="${group.key }" <c:if test="${group.key eq params.collectionGroup}">selected="selected"</c:if>>
                                        ${group.value}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        订 单 排 序:
                        <select id="sortBy" name="sortBy">
                            <option value="" <c:if test="${params.sortBy eq ''}">selected="selected"</c:if>>初始排序</option>
                            <option value="overdueDays ASC" <c:if test="${params.sortBy eq 'overdueDays ASC'}">selected="selected"</c:if>>逾期天数升序</option>
                            <option value="overdueDays DESC" <c:if test="${params.sortBy eq 'overdueDays DESC'}">selected="selected"</c:if>>逾期天数降序</option>
                            <option value="dispatch_time ASC" <c:if test="${params.sortBy eq 'dispatch_time ASC'}">selected="selected"</c:if>>派单时间升序</option>
                            <option value="dispatch_time DESC" <c:if test="${params.sortBy eq 'dispatch_time DESC'}">selected="selected"</c:if>>派单时间降序</option>
                            <option value="last_collection_time ASC" <c:if test="${params.sortBy eq 'last_collection_time ASC'}">selected="selected"</c:if>>最新催收时间升序</option>
                            <option value="last_collection_time DESC" <c:if test="${params.sortBy eq 'last_collection_time DESC'}">selected="selected"</c:if>>最新催收时间降序</option>
                            <option value="update_date ASC" <c:if test="${params.sortBy eq 'update_date ASC'}">selected="selected"</c:if>>最新还款时间升序</option>
                            <option value="update_date DESC" <c:if test="${params.sortBy eq 'update_date DESC'}">selected="selected"</c:if>>最新还款时间降序</option>

                        </select>
                    </td>
                    <td>
                        催 收 状 态:
                        <select id="status" name="status">
                            <option value="" <c:if test="${params.status eq '0'}">selected="selected"</c:if>>全部</option>
                            <option value="0" <c:if test="${params.status eq '0'}">selected="selected"</c:if>> 待催收</option>
                            <option value="1" <c:if test="${params.status eq '1'}">selected="selected"</c:if>> 催收中</option>
                            <option value="2" <c:if test="${params.status eq '2'}">selected="selected"</c:if>> 承诺还款</option>
                            <option value="3" <c:if test="${params.status eq '3'}">selected="selected"</c:if>> 待催收（委外）</option>
                            <option value="4" <c:if test="${params.status eq '4'}">selected="selected"</c:if>> 催收成功</option>
                            <option value="6" <c:if test="${params.status eq '6'}">selected="selected"</c:if>> 减免审核中</option>
                            <option value="7" <c:if test="${params.status eq '7'}">selected="selected"</c:if>> 减免审核成功</option>
                            <option value="8" <c:if test="${params.status eq '8'}">selected="selected"</c:if>> 减免审核拒绝</option>
                        </select>
                    </td>
                    <td>
                        新 老 用 户:
                        <select id="customerType" name="customerType">
                            <option value="" <c:if test="${params.customerType eq ''}">selected="selected"</c:if>>全部</option>
                            <option value="0" <c:if test="${params.customerType eq '0'}">selected="selected"</c:if>>新用户</option>
                            <option value="1" <c:if test="${params.customerType eq '1'}">selected="selected"</c:if>>老用户</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        渠 道 来 源:
                        <select id="channelFrom" name="channelFrom">
                            <option value="">全部</option>
                            <c:forEach var="channelFromMap" items="${channelFromMap }">
                                <option value="${channelFromMap.key }" <c:if test="${channelFromMap.key eq params.channelFrom}">selected="selected"</c:if>>
                                        ${channelFromMap.value}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        放 款 主 体:
                        <select id="repayChannel" name="repayChannel">
                            <option value="">全部</option>
                            <c:forEach var="repayChannel" items="${repayChannelMap }">
                                <option value="${repayChannel.key }" <c:if test="${repayChannel.key eq params.repayChannel}">selected="selected"</c:if>>
                                        ${repayChannel.value}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">
                                    查询
                                </button>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div class="pageContent">
        <c:set var="page" value="${page}"></c:set>
        <!-- 分页 -->
        <%@ include file="/WEB-INF/viewback/page.jsp" %>
       <%-- <jsp:include page="${BACK_URL}/rightSubList">
            <jsp:param value="${params.myId}" name="parentId"/>
        </jsp:include>--%>
        <table class="table" style="width: 100%;" layoutH="160"
               nowrapTD="false">
            <thead>
            <tr>
                <!-- 				    <th align="center" width="10"> -->
                <!-- 				      <input type="checkbox" id="checkAlls" onclick="checkAll(this);"/> -->
                <!-- 				    </th> -->
                <th align="center" width="50">
                    序号
                </th>
                <th align="center" width="50">
                    借款编号
                </th>
                <th align="center" width="50">
                    借款人
                </th>
                <th align="center" width="50">
                    借款人手机号
                </th>
                <%--<th align="center" width="60">
                    借款人身份证
                </th>--%>
                <th align="center" width="50">
                    催收状态
                </th>
                <th align="center" width="50">
                    跟进等级
                </th>
                <th align="center" width="50">
                    借款金额
                </th>
                <th align="center" width="50">
                    利息
                </th>
                <th align="center" width="50">
                    服务费
                </th>
                <th align="center" width="50">
                    滞纳金
                </th>
                <th align="center" width="50">
                    减免滞纳金
                </th>
                <th align="center" width="50">
                    已还金额
                </th>
                <th align="center" width="50">
                    逾期天数
                </th>
                <th align="center" width="50">
                    逾期等级
                </th>
                <th align="center" width="50">
                    用户类型
                </th>
                <th align="center" width="50">
                    渠道来源
                </th>
                <th align="center" width="50">
                    放款主体
                </th>
                <th align="center" width="80">
                    应还时间
                </th>
                <%--<th align="center" width="70">
                    派单时间
                </th>--%>
                <th align="center" width="70">
                    最新催收时间
                </th>
                <th align="center" width="70">
                    承诺还款时间
                </th>
                <%--<th align="center" width="70">
                    最新还款时间
                </th>--%>
                <%--<th align="center" width="50">
                    派单人
                </th>--%>
                <th align="center" width="150">
                    操作
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${page.items}" varStatus="status">
                <tr target="id" rel="${order.id }">
                    <!-- 						<td> -->
                        <%-- 					      <input type="checkbox" name="checkItem" value="${order.id}" group="${order.collectionGroup}"/> --%>
                    <!-- 					    </td> -->
                    <td align="center" width="50">
                            ${status.count}
                    </td>
                    <td align="center" width="50">
                            ${order.loanId}
                    </td>
                    <td align="center" width="50">
                            ${order.realName}
                    </td>
                    <td align="center" width="50">
                            ${order.phoneNumber}
                    </td>
                    <td align="center" width="50">
                        <c:choose>
                            <c:when test="${order.collectionStatus eq '0'}">待催收</c:when>
                            <c:when test="${order.collectionStatus eq '1'}"> 催收中</c:when>
                            <c:when test="${order.collectionStatus eq '2'}">承诺还款</c:when>
                            <c:when test="${order.collectionStatus eq '3'}">待催收（委外）</c:when>
                            <c:when test="${order.collectionStatus eq '4'}">催收成功</c:when>
                            <c:when test="${order.collectionStatus eq '5'}">续期</c:when>
                            <c:when test="${order.collectionStatus eq '6'}">减免审核中</c:when>
                            <c:when test="${order.collectionStatus eq '7'}">减免审核成功</c:when>
                            <c:when test="${order.collectionStatus eq '8'}">减免审核拒绝</c:when>
                        </c:choose>
                    </td>
                    <td align="center" width="50">
                            ${levelMap[order.topImportant]}
                    </td>
                    <td align="center" width="50">
                            ${order.loanMoney}
                    </td>
                    <td align="center" width="50">
                        <c:if test="${order.accrual eq null}">
                            0
                        </c:if>
                        <c:if test="${order.accrual ne null}">
                            ${order.accrual}
                        </c:if>
                    </td>
                    <td align="center" width="50">
                        <c:if test="${order.paidMoney > 0}">
                            ${order.serviceCharge}
                        </c:if>
                        <c:if test="${order.paidMoney <= 0}">
                            0
                        </c:if>
                    </td>
                    <td align="center" width="50">
                            ${order.loanPenlty}
                    </td>
                    <td align="center" width="50">
                            ${order.reductionMoney}
                    </td>
                    <td align="center" width="50">
                            ${order.returnMoney}
                    </td>
                    <td align="center" width="50">
                            ${order.overdueDays}
                    </td>
                    <td align="center" width="50">
                            ${dictMap[order.collectionGroup]}
                    </td>
                    <td align="center" width="50">
                        <c:choose>
                            <c:when test="${order.customerType eq null}">数据缺失</c:when>
                            <c:when test="${order.customerType eq '0'}">新用户</c:when>
                            <c:when test="${order.customerType eq '1'}">老用户</c:when>
                        </c:choose>
                    </td>
                    <td align="center" width="50">
                            ${channelFromMap[order.channelFrom]}
                    </td>
                    <td align="center" width="50">
                            ${repayChannelMap[order.repayChannel]}
                    </td>
                    <td align="center" width="50">
                        <fmt:formatDate value="${order.loanEndTime}" pattern="yyyy-MM-dd"/>
                    </td>
                        <%--<td align="center" width="50">
                            <fmt:formatDate value="${order.dispatchTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>--%>
                    <td align="center" width="50">
                        <fmt:formatDate value="${order.lastCollectionTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td align="center" width="50">
                        <fmt:formatDate value="${order.promiseRepaymentTime}" pattern="yyyy-MM-dd"/>
                    </td>
                        <%--<td align="center" width="50">
                            <c:if test="${order.returnMoney>0.0}">
                                <fmt:formatDate value="${order.currentReturnDay}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </c:if>
                        </td>--%>
                        <%--<td align="center" width="50">
                                ${order.dispatchName}
                        </td>--%>
                    <td align="center" width="150">
                        <c:if test="${order.collectionStatus ne '4'}">
                            <a href="collectionOrder/toxianqin?id=${order.id }" target="navtab"
                               style="color: #cd0a0a;margin: 8px;font-size: 15px;text-decoration: none;">催收</a>
                            <a href="collectionOrder/tokokuan?id=${order.id }&myId=${params.myId}" target="dialog"
                               style="color: #1b8d0f;margin: 8px;font-size: 15px;text-decoration: none;">代扣
                            </a>
                            <a href="collectionOrder/jianmian?id=${order.id }&myId=${params.myId}" target="dialog"
                               style="color: #0f579f;margin: 8px;font-size: 15px;text-decoration: none;">减免
                            </a>
                        </c:if>
                        <c:if test="${order.collectionStatus eq '4'}">
                            <a href="collectionOrder/toxianqin?id=${order.id }" target="navtab"
                               style="color: #1b8d0f;font-size: 15px;margin-top: 2px;text-decoration: none;">催收记录</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>

    <script type="text/javascript">
        function getMsg(obj) {
            var tt = $(".pageContent tbody tr").is(':checked').value();
            var bol = $(obj).is(':checked');
            var text = bol.get(1).text();
            console.log(text);
        }

        /* $("#pagerForm tbody tr").each(function() {
         $("input [name='checkItem']").live("click",function(){
         var $thisTD=$(this).parents("tr").find("td:eq(2)");
         var tt = $thisTD.val();
         alert(tt);
         }
         );
         });

         $(".pageContent tbody tr").each(function(){
         $("tr",this).click(function(){
         alert(1);
         alert($(this).parents("tr").child("td").get(3));
         });
         }); */
    </script>

</form>

<script type="text/javascript">
    function checkAll(obj) {
        var bol = $(obj).is(':checked');
        $("input[name='checkItem']").attr("checked", bol);
    }
    function sel(obj) {
        var bol = true;
        var check = $(obj).find("input[name='checkItem']");
        if ($(check).is(':checked')) {
            bol = false;
        }
        $(check).attr("checked", bol);
    }
    function getOrderIds(obj) {
        var href = $(obj).attr("href");
        if (href.indexOf('&ids') > -1) {
            href = href.substring(0, href.indexOf('&ids'));
        }
        var hasDifferentGroup = '0';
        var selectedGroup = "";
        $("input[name='checkItem']:checked").each(function () {
            var group = $(this).attr("group");
            if (group != undefined && group != '') {
                if (selectedGroup == '') {
                    selectedGroup = group;//第一次赋值
                } else if (selectedGroup != group) {// 之后和第一次的值比较，有不同就GG
                    hasDifferentGroup = '1';
                }
            }
        })
        if (hasDifferentGroup) {
            var ids = "";
            $("input[name='checkItem']:checked").each(function () {
                ids = ids + "," + $(this).val();
            });
            var toHref = href + "&ids=" + ids.substring(1) + "&groupStatus=" + hasDifferentGroup;
            $(obj).attr("href", toHref);
        } else {
            return;
        }
    }
</script>

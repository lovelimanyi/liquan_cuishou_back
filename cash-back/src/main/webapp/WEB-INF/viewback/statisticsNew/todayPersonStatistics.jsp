<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="statisticsNew/recoveryRateStatistics?Flag=person&myId=${params.myId}">

    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        派单时间:
                        <input type="text" name="dispatchTime" id="dispatchTime" value="${params.dispatchTime}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />至
                        <input type="text" name="dispatchEndTime" id="dispatchEndTime" value="${params.dispatchEndTime}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
                    </td>
                    <td>
                        借款类型:
                        <select name="borrowingType" id="borrowingType">
                            <c:forEach items="${borrowingTypeMap}" var="map">
                                <option value="${map.key}" <c:if test="${params.borrowingType eq map.key}">selected="selected"</c:if> >${map.value}</option>
                            </c:forEach>
                        </select>
                    </td>

                    <td>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">
                                    查询
                                </button>
                                <button id="btnExport" value="导出"/></button>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <div class="pageContent">
        <jsp:include page="${BACK_URL}/rightSubList">
            <jsp:param value="" name=""/>
        </jsp:include>
        <table class="table" style="width: 100%;" layoutH="112"
               nowrapTD="false">
            <thead>
            <tr>
                <th align="center" width="40">
                    序号
                </th>
                <th align="center" width="100">
                    统计日期
                </th>
                <th align="center" width="100">
                    催收公司
                </th>
                <th align="center" width="50">
                    催收组
                </th>
                <th align="center" width="100">
                    催收员姓名
                </th>
                <th align="center" width="100">
                    本金金额
                </th>
                <th align="center" width="100">
                    已还本金
                </th>
                <th align="center" width="100">
                    未还本金
                </th>
                <th align="center" width="100">
                    本金催回率
                </th>
                <th align="center" width="100">
                    完成订单总滞纳金
                </th>
                <th align="center" width="120">
                    完成订单实收滞纳金
                </th>
                <th align="center" width="100">
                    总订单未还滞纳金
                </th>
                <th align="center" width="100">
                    滞纳金催回率
                </th>
                <th align="center" width="70">
                    订单量
                </th>
                <th align="center" width="90">
                    已还订单量
                </th>
                <th align="center" width="90">
                    未还订单量
                </th>
                <th align="center" width="100">
                    订单还款率
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="list" items="${list }" varStatus="status">
                <tr>
                    <td>
                            ${status.count}
                    </td>
                    <td>
                        <fmt:formatDate value="${list.createDate}" pattern="yyyy-MM-dd"/>
                    </td>
                    <td>
                        <c:if test="${companyName == null}">
                            <c:forEach items="${company}" var="company">
                                <c:if test="${list.companyId == company.id}">${company.title}
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:if test="${companyName != null}">
                            ${companyName}
                        </c:if>
                    </td>
                    <td>
                            ${dictMap[list.groupLevel] }
                    </td>
                    <td>
                            ${list.backUserName}
                    </td>
                    <td>
                            ${list.totalPrincipal}
                    </td>
                    <td>
                            ${list.realgetTotalPrincipal}
                    </td>
                    <td>
                            ${list.remainPrincipal}
                    </td>
                    <td>
                            ${list.repaymentProbability}%
                    </td>

                    <td>
                            ${list.totalPenalty}
                    </td>
                    <td>
                            ${list.realgetTotalPenalty}
                    </td>
                    <td>
                            ${list.remainPenalty}
                    </td>
                    <td>
                            ${list.penaltyProbability}%
                    </td>
                    <td>
                            ${list.totalOrderCount}
                    </td>
                    <td>
                            ${list.doneOrderCount}
                    </td>
                    <td>
                            ${list.undoneOrderCount}
                    </td>
                    <td>
                            ${list.orderProbability}%
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${pm}"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp"%>
    </div>
</form>
<script type="text/javascript">

    function reportExcel(obj){
        var href=$(obj).attr("href");
        if(href.indexOf('&begDate') > -1){
            href = href.substring(0,href.indexOf('&begDate'));
        }
        var begDate=$("#begDate").val();
        var endDate=$("#endDate").val();
        var personName = $("#personName").val() == undefined ? '' : $("#personName").val();
        var orderGroupId = $("#orderGroupId").val() == undefined ? '' : $("#orderGroupId").val();
        var groupId = $("#groupId").val() == undefined ? '' : $("#groupId").val();
        var companyId = $("#companyId").val() == undefined ? '' : $("#companyId").val();
        var type='${params.type}';
        var toHref=href+"&begDate="+begDate+"&endDate="+endDate+"&personName="+personName+"&orderGroupId="+orderGroupId
            +"&groupId="+groupId+"&companyId="+companyId+"&type="+type;

        $(obj).attr("href",toHref);
    }
</script>
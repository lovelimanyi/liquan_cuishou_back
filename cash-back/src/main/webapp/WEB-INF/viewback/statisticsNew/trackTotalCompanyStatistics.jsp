<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="statisticsNew/trackTotalStatistics?Flag=company&myId=${params.myId}">

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
                        催收组:
                        <select name="groupLevel" id="groupLevel">
                            <option value="">全部</option>
                            <c:forEach items="${groupLevelMap}" var="map">
                                <option value="${map.key}" <c:if test="${params.groupLevel eq map.key}">selected="selected"</c:if> >${map.value}</option>
                            </c:forEach>
                        </select>
                    </td>

                    <td>
                        催收公司:
                        <select name="companyId" id="companyId">
                            <option value="">全部</option>
                            <c:forEach items="${companys}" var="company">
                                <option value="${company.id}" <c:if test="${params.companyId eq company.id}">selected="selected"</c:if> >${company.title}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        借款类型:
                        <select name="borrowingType" id="borrowingType">
                            <option value="">全部</option>
                            <c:forEach items="${borrowingTypeMap}" var="map">
                                <option value="${map.key}" <c:if test="${params.borrowingType eq map.key}">selected="selected"</c:if> >${map.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        新老用户:
                        <select name="userType" id="userType">
                            <option value="">全部</option>
                            <c:forEach items="${userTypeMap}" var="map">
                                <option value="${map.key}" <c:if test="${params.userType eq map.key}">selected="selected"</c:if> >${map.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                        <td>
                            排序:
                            <select name="orderBy">
                                <option value="" <c:if test="${params.orderBy eq ''}">selected="selected"</c:if>>默认排序</option>
                                <option value="repaymentProbability ASC" <c:if test="${params.orderBy eq 'repaymentProbability ASC'}">selected="selected"</c:if>>按本金催回率升序</option>
                                <option value="repaymentProbability DESC" <c:if test="${params.orderBy eq 'repaymentProbability DESC'}">selected="selected"</c:if>>按本金催回率降序</option>
                                <option value="penaltyProbability ASC" <c:if test="${params.orderBy eq 'penaltyProbability ASC'}">selected="selected"</c:if>>按滞纳金催回率升序</option>
                                <option value="penaltyProbability DESC" <c:if test="${params.orderBy eq 'penaltyProbability DESC'}">selected="selected"</c:if>>按滞纳金催回率降序</option>
                                <option value="orderProbability ASC" <c:if test="${params.orderBy eq 'orderProbability ASC'}">selected="selected"</c:if>>按订单催回率升序</option>
                                <option value="orderProbability DESC" <c:if test="${params.orderBy eq 'orderProbability DESC'}">selected="selected"</c:if>>按订单催回率降序</option>
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
                    派单日期
                </th>
                <th align="center" width="100">
                    催收公司
                </th>
                <th align="center" width="100">
                    用户类型
                </th>
                <th align="center" width="50">
                    催收组
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
                <th align="center" width="100">
                    订单还款率
                </th>
                <th align="center" width="100">
                    每日处理量
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
                        <fmt:formatDate value="${list.dispatchTime}" pattern="yyyy-MM-dd"/>
                    </td>
                    <td>
                            ${list.companyName}
                    </td>
                    <td>
                            ${list.userType}
                    </td>
                    <td>
                            ${dictMap[list.groupLevel] }
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
                            ${list.orderProbability}%
                    </td>
                    <td>
                            ${list.handleCount}
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
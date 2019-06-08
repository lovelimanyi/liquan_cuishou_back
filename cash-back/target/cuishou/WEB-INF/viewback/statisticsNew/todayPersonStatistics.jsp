<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="statisticsNew/todayStatistics?Flag=person&myId=${params.myId}">

    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        统计时间:
                        <input type="text" name="createDate" id="createDate" value="${params.createDate}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
                    </td>
                    <c:if test="${roleId  != '10021' }">
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
                                    <option value="${map.key}"
                                            <c:if test="${params.borrowingType eq map.key}">selected="selected"</c:if> >${map.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            产 品 名 称:
                            <select id="merchantNo" name="merchantNo">
                                <option value="">全部</option>
                                <c:forEach var="merchantNo" items="${merchantNoMap }">
                                    <option value="${merchantNo.key }" <c:if test="${merchantNo.key eq params.merchantNo}">selected="selected"</c:if>>
                                            ${merchantNo.value}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            催收员姓名:
                            <input type="text" name="backUserName" id="backUserName"
                                   value="${params.backUserName }" />
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
                    </c:if>
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
                <th align="center" width="70">
                    产品名称
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
                    借款类型
                </th>
                <th align="center" width="100">
                    本金金额
                </th>
                <th align="center" width="100">
                    当日催回本金
                </th>
                <th align="center" width="100">
                    当日本金催回率
                </th>
                <th align="center" width="100">
                    滞纳金总额
                </th>
                <th align="center" width="120">
                    当日滞纳金
                </th>
                <th align="center" width="100">
                    当日滞纳金催回率
                </th>
                <th align="center" width="70">
                    订单总数
                </th>
                <th align="center" width="90">
                    当日催回订单数
                </th>
                <th align="center" width="100">
                    当日订单催回率
                </th>
                <th align="center" width="90">
                    当日结清单数
                </th>
                <th align="center" width="90">
                    当日结清金额
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
                    <td align="center" width="50">
                            ${merchantNoMap[list.merchantNo]}
                    </td>
                    <td>
                            ${list.companyName}
                    </td>
                    <td>
                            ${dictMap[list.groupLevel] }
                    </td>
                    <td>
                            ${list.backUserName}
                    </td>
                    <td>
                            ${borrowingTypeMap[list.borrowingType]}
                    </td>
                    <td>
                            ${list.totalPrincipal}
                    </td>
                    <td>
                            ${list.realgetTotalPrincipal}
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
                            ${list.todayDoneCount}
                    </td>
                    <td>
                            ${list.todayDoneMoney}
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
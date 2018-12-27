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
                    借款类型
                </th>
                <th align="center" width="100">
                    产品名称
                </th>
                <th align="center" width="100">
                    S1入催率
                </th>
                <th align="center" width="100">
                    待催回
                </th>
                <th align="center" width="100">
                    逾期1天
                </th>
                <th align="center" width="100">
                    逾期2天
                </th>
                <th align="center" width="100">
                    逾期3天
                </th>
                <th align="center" width="100">
                    逾期4天
                </th>
                <th align="center" width="100">
                    逾期5天
                </th>
                <th align="center" width="100">
                    逾期6天
                </th>
                <th align="center" width="100">
                    逾期7天
                </th>
                <th align="center" width="100">
                    逾期8-10天
                </th>
                <th align="center" width="100">
                    逾期11-30天
                </th>
                <th align="center" width="100">
                    逾期31-60天
                </th>
                <th align="center" width="100">
                    逾期61-90天
                </th>
                <th align="center" width="100">
                    逾期91-180天
                </th>
                <th align="center" width="100">
                    逾期180天以上
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
                            ${borrowingTypeMap[list.borrowingType] }
                    </td>
                    <td>
                            ${merchantNoMap[list.merchantNo]}
                    </td>
                    <td>
                            ${list.entryRate}
                    </td>
                    <td>
                            ${list.unentryRate }
                    </td>
                    <td>
                            ${list.oneDay}
                    </td>
                    <td>
                            ${list.twoDays}
                    </td>
                    <td>
                            ${list.threeDays}
                    </td>
                    <td>
                            ${list.fourDays}
                    </td>
                    <td>
                            ${list.fiveDays}
                    </td>

                    <td>
                            ${list.sixDays}
                    </td>
                    <td>
                            ${list.sevenDays}
                    </td>
                    <td>
                            ${list.eightTOTen}
                    </td>
                    <td>
                            ${list.toThirty}
                    </td>
                    <td>
                            ${list.toSixty}
                    </td>
                    <td>
                            ${list.toNinety}
                    </td>
                    <td>
                            ${list.toHundredEight}
                    </td>
                    <td>
                            ${list.overHundredEight}
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
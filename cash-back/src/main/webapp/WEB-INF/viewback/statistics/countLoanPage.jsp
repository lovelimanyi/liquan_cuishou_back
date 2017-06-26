<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="statistics/countLoanPage?myId=${params.myId}" method="post">

    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        时间:
                        <input type="text" name="begDate" id="begDate" value="${params.begDate}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
                        至       <input type="text" name="endDate" id="endDate" value="${params.endDate}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
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
        <jsp:include page="${BACK_URL}/rightSubList">
            <jsp:param value="${params.myId}" name="parentId"/>
        </jsp:include>
        <table class="table" style="width: 100%;" layoutH="112"
               nowrapTD="false">
            <thead>
            <tr>
                <th align="center" width="100">
                    时间
                </th>
                <th align="center" width="100">
                    到期总量
                </th>
                <th align="center" width="100">
                    放款总订单数量
                </th>
                <th align="center" width="100">
                    7天产品放款总订单数量
                </th>
                <th align="center" width="100">
                    14天产品放款总订单数量
                </th>
                <th align="center" width="100">
                    21天产品放款总订单数量
                </th>
                <th align="center" width="100">
                    逾期总量
                </th>
                <th align="center" width="100">
                    7天产品逾期总量
                </th>
                <th align="center" width="100">
                    14天产品逾期总量
                </th>
                <th align="center" width="100">
                    21天产品逾期总量
                </th>
                <%--<th align="center" width="100">--%>
                    <%--7天产品数量逾期率--%>
                <%--</th>--%>
                <%--<th align="center" width="100">--%>
                    <%--14天产品数量逾期率--%>
                <%--</th>--%>
                <%--<th align="center" width="100">--%>
                    <%--坏账率--%>
                <%--</th>--%>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="list" items="${pm.items }" varStatus="status">
                <tr>
                    <td><fmt:formatDate value="${list.reportDate}" pattern="yyyy-MM-dd"/></td>
                    <td>${list.expireCount}</td>
                    <td>${list.borrowOrderCount}</td>
                    <td>${list.borrowOrderSevenCount}</td>
                    <td>${list.borrowOrderFourteenCount}</td>
                    <td>${list.borrowOrderTwentyoneCount}</td>
                    <td>${list.overdueCount}</td>
                    <td>${list.overdueSevenCount}</td>
                    <td>${list.overdueFourteenCount}</td>
                    <td>${list.overdueTwentyoneCount}</td>
                    <%--<td><fmt:formatNumber value="${list.overdueMoneySumCountStatistic7Value}" maxFractionDigits="2" />%</td>--%>
                    <%--<td><fmt:formatNumber value="${list.overdueMoneySumCountStatistic14Value}" maxFractionDigits="2" />%</td>--%>
                    <%--<td><fmt:formatNumber value="${list.baddebtCountRate}" maxFractionDigits="2" />%</td>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${pm}"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp"%>
    </div>
</form>


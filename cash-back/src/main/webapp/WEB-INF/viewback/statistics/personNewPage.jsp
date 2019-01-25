<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="statistics/smallAmountStatistics?Flag=personNew&myId=${params.myId}">

    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        统计时间:
                        <input type="text" name="createDate" id="createDate" value="${params.createDate}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
                    </td>

                    <c:if test="${params.roleId  != '10021' }">

                        <td>
                            催收组:
                            <select name="groupLevel" id="groupLevel">
                                <option value="">全部</option>
                                <c:forEach items="${groupLevelMap}" var="map">
                                    <option value="${map.key}" <c:if test="${groupLevel eq map.key}">selected="selected"</c:if> >${map.value}</option>
                                </c:forEach>
                            </select>
                        </td>

                        <td>
                            催收公司:
                            <select name="companyId" id="companyId">
                                <option value="">全部</option>
                                <c:forEach items="${company}" var="company">
                                    <option value="${company.id}" <c:if test="${params.companyId == company.id}">selected="selected"</c:if>>${company.title}</option>
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
                            <select name="orderBy" id="orderBy">
                                <option value="" <c:if test="${params.orderBy eq ''}">selected="selected"</c:if>>默认排序</option>
                                <option value="cleanPrincipalProbability ASC" <c:if test="${params.orderBy eq 'cleanPrincipalProbability ASC'}">selected="selected"</c:if>>本金完成率升序</option>
                                <option value="cleanPrincipalProbability DESC" <c:if test="${params.orderBy eq 'cleanPrincipalProbability DESC'}">selected="selected"</c:if>>本金完成率降序</option>
                                <option value="cleanPenaltyProbability ASC" <c:if test="${params.orderBy eq 'cleanPenaltyProbability ASC'}">selected="selected"</c:if>>滞纳金完成率升序</option>
                                <option value="cleanPenaltyProbability DESC" <c:if test="${params.orderBy eq 'cleanPenaltyProbability DESC'}">selected="selected"</c:if>>滞纳金完成率降序</option>
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
            <jsp:param value="${params.myId}" name="parentId"/>
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
                    入催本金
                </th>
                <th align="center" width="100">
                    入催订单数
                </th>
                <th align="center" width="100">
                    当日完成单数
                </th>
                <th align="center" width="100">
                    当日完成金额
                </th>
                <th align="center" width="100">
                    完成单数
                </th>
                <th align="center" width="100">
                    完成本金
                </th>
                <th align="center" width="100">
                    完成订单滞纳金
                </th>
                <th align="center" width="100">
                    实收滞纳金
                </th>
                <th align="center" width="100">
                    不考核滞纳金
                </th>
                <th align="center" width="70">
                    本金完成率
                </th>
                <th align="center" width="90">
                    滞纳金完成率
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="list" items="${listNew }" varStatus="status">
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
                            ${list.totalOrderCount}
                    </td>
                    <td>
                            ${list.todayDoneCount}
                    </td>
                    <td>
                            ${list.todayDoneMoney}
                    </td>
                    <td>
                            ${list.doneOrderCount}
                    </td>
                    <td>
                            ${list.donePrincipal}
                    </td>
                    <td>
                            ${list.donePenalty}
                    </td>
                    <td>
                            ${list.realgetPenalty}
                    </td>
                    <td>
                            ${list.noCheckPenalty}
                    </td>
                    <td>
                            ${list.cleanPrincipalProbability}%
                    </td>

                    <td>
                            ${list.cleanPenaltyProbability}%
                    </td>


                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${pm2}"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp"%>
    </div>
</form>
<script type="text/javascript">

    function reportExcel(obj){
        var href=$(obj).attr("href");
        href = href.split("&")[0];
        var createDate=$("#createDate").val();
        var backUserName = $("#backUserName").val() == undefined ? '' : $("#backUserName").val();
        var groupLevel = $("#groupLevel").val() == undefined ? '' : $("#groupLevel").val();
        var companyId = $("#companyId").val() == undefined ? '' : $("#companyId").val();
        var orderBy = $("#orderBy").val() == undefined ? null : $("#orderBy").val();
        var Flag='personNew';
        var toHref=href+"&createDate="+createDate+"&backUserName="+backUserName+"&groupLevel="+groupLevel+"&companyId="+companyId
            +"&Flag="+Flag +"&orderBy="+orderBy;

        $(obj).attr("href",toHref);
    }
</script>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="companyIpAddress/list?myId=${params.myId}" method="post">

    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        催收公司
                        <select name="companyId">
                            <option value="">全部</option>
                            <c:forEach var="company" items="${companyList}" varStatus="status">
                                <option value="${company.id}" <c:if test="${params.companyId eq company.id}">selected="selected"</c:if>>${company.title}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        ip地址
                        <input type="text" name="ipAddress" id="ipAddress" value="${params.ipAddress}" class="textInput"/>
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
                <th align="center" width="50">
                    编号
                </th>
                <th align="center" width="50">
                    公司名称
                </th>
                <th align="center" width="50">
                    ip地址
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="address" items="${page.items }" varStatus="status">
                <tr target="id" rel="${address.id }">
                    <td>
                            ${status.index+1}
                    </td>
                    <td>
                            ${address.companyName}
                    </td>
                    <td>
                            ${address.ipAddress}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${page }"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp" %>
    </div>
</form>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="userAccountWhiteList/list?myId=${params.myId}" method="post">

    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        登录账号
                        <input type="text" name="userAccount" id="userAccount" value="${params.userAccount}" class="textInput"/>
                    </td>
                    <td>
                        真实姓名
                        <input type="text" name="userName" id="userName" value="${params.userName}" class="textInput"/>
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
                    登录账号
                </th>
                <th align="center" width="50">
                    姓名
                </th>
                <th align="center" width="50">
                    公司
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="whiteList" items="${page.items }" varStatus="status">
                <tr target="id" rel="${whiteList.id }">
                    <td>
                            ${status.index+1}
                    </td>
                    <td>
                            ${whiteList.userAccount}
                    </td>
                    <td>
                            ${whiteList.userName}
                    </td>
                    <td>
                            ${whiteList.companyName}
                    </td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${page}"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp" %>
    </div>
</form>
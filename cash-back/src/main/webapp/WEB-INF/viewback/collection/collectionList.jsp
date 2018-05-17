<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="collection/getCollectionPage?myId=${params.myId}" method="post">

    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        手机号
                        <input type="text" name="userMobile" value="${params.userMobile }"/>
                    </td>
                    <td>
                        姓名
                        <input type="text" name="userName" value="${params.userName }"/>
                    </td>
                    <td>
                        催收组
                        <select name="groupLevel">
                            <option value="">全部</option>
                            <c:forEach items="${groupNameMap}" var="map">
                                <option value="${map.key}"
                                        <c:if test="${params.groupLevel eq map.key}">selected="selected"</c:if> >${map.value}</option>
                            </c:forEach>
                        </select>
                    </td>
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
                        催收员状态
                        <select name="userStatus">
                            <option value="">全部</option>
                            <c:forEach var="statuss" items="${groupStatusMap}" varStatus="status">
                                <option value="${statuss.key}" <c:if test="${params.userStatus eq statuss.key}">selected="selected"</c:if>>${statuss.value}</option>
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
                    登录名称
                </th>
                <th align="center" width="30">
                    姓名
                </th>
                <th align="center" width="100">
                    手机号
                </th>
                <th align="center" width="100">
                    邮箱
                </th>
                <th align="center" width="100">
                    公司
                </th>
                <th align="center" width="50">
                    催收组
                </th>
                <th align="center" width="30">
                    状态
                </th>
                <th align="center" width="30">
                    添加时间
                </th>
                <th align="center" width="30">
                    操作
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="collection" items="${pm.items }" varStatus="status">
                <tr target="id" rel="${collection.id }">
                    <td>
                            ${status.index+1}
                    </td>
                    <td>
                            ${collection.userAccount}
                    </td>
                    <td>
                            ${collection.userName}
                    </td>
                    <td>
                            ${collection.userMobile}
                    </td>
                    <td>
                            ${collection.userEmail}
                    </td>
                    <td>
                            ${collection.companyName}
                    </td>
                    <td>
                            ${groupNameMap[collection.groupLevel]}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${collection.userStatus eq 1}">
                                启用
                            </c:when>
                            <c:when test="${collection.userStatus eq 0}">
                                禁用
                            </c:when>
                            <c:otherwise>
                                删除
                            </c:otherwise>
                        </c:choose>

                    </td>
                    <td>
                        <fmt:formatDate value="${collection.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <c:choose>
                        <c:when test="${verifyCodePermission eq 1}">
                            <td>
                                <a href="collection/getVerifyCode?phone=${collection.userMobile}" class="add" target="dialog" width="820" height="420" mask="true"
                                   style="color: #1b8d0f">查询验证码</a>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                暂无操作权限
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${pm }"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp" %>
    </div>
</form>

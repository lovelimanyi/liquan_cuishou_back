<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="user/getUserPage?myId=${params.myId}" method="post">

    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>

                    <td>
                        用户名称:
                        <input type="text" name="userAccountLike"
                               value="${params.userAccountLike }"/>
                    </td>
                    <td>
                        手机:
                        <input type="text" name="userMobileLike"
                               value="${params.userMobileLike }"/>
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
                    序号
                </th>
                <th align="center" width="100">
                    用户名
                </th>
                <th align="center" width="130">
                    公司名称
                </th>
                <th align="center" width="130">
                    角色
                </th>
                <th align="center" width="50">
                    姓名
                </th>
                <th align="center" width="30">
                    性别
                </th>
                <th align="center" width="100">
                    地址
                </th>
                <th align="center" width="100">
                    电话
                </th>
                <th align="center" width="100">
                    手机
                </th>
                <th align="center" width="100">
                    邮箱
                </th>
                <th align="center" width="120">
                    添加时间
                </th>
                <th align="center" width="120">
                    添加IP
                </th>
                <th align="center" width="120">
                    备注
                </th>
                <%--
                <th align="center" width="80">
                    操作
                </th>
                --%>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${pm.items }" varStatus="status">
                <tr target="userId" rel="${user.id }">
                    <td>
                            ${status.count}
                    </td>
                    <td>
                            ${user.userAccount}
                    </td>
                    <td>
                            ${user.companyTitle }
                    </td>
                    <td>
                        <c:if test="${user.roleId  eq '10001'}">系统管理员</c:if>
                        <c:if test="${user.roleId  eq '10019'}">经理</c:if>
                        <c:if test="${user.roleId  eq '10021'}">催收员</c:if>
                        <c:if test="${user.roleId  eq '10022'}">委外经理</c:if>
                        <c:if test="${user.roleId  eq '10023'}">财务</c:if>
                        <c:if test="${user.roleId  eq '10024'}">客服</c:if>
                        <c:if test="${user.roleId  eq '10025'}">技术</c:if>
                        <c:if test="${user.roleId  eq '10030'}">高级经理</c:if>
                        <c:if test="${user.roleId  eq '10033'}">电催员</c:if>
                        <c:if test="${user.roleId  eq '10034'}">电销经理</c:if>
                        <c:if test="${user.roleId  eq '10035'}">坐席经理</c:if>
                        <c:if test="${user.roleId  eq '10036'}">坐席</c:if>
                    </td>
                    <td>
                            ${user.userName }
                    </td>
                    <td>
                            ${user.userSex }
                    </td>
                    <td>
                            ${user.userAddress }
                    </td>
                    <td>
                            ${user.userTelephone }
                    </td>
                    <td>
                            ${user.userMobile }
                    </td>
                    <td>
                            ${user.userEmail }
                    </td>
                    <td>
                        <fmt:formatDate value="${user.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                            ${user.addIp }
                    </td>
                    <td>
                            ${user.remark }
                    </td>
                    <%--
                        <c:choose>
                            <c:when test="${verifyCodePermission eq 1}">
                                <td>
                                    <a href="collection/getVerifyCode?phone=${user.userMobile}" class="add" target="dialog" width="820" height="420" mask="true"
                                       style="color: #1b8d0f">查询验证码</a>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    暂无操作权限
                                </td>
                            </c:otherwise>
                        </c:choose>
                    --%>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${pm }"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp" %>
    </div>
</form>
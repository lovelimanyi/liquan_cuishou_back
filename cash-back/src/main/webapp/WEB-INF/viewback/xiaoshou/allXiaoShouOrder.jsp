<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<style type="text/css">
    div .dialogContent .layoutBox .unitBox {
        width: 1000px;
        height: 450px;
    }

    a {

    }
</style>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="xiaoShou/getAllXiaoShouOrder?myId=${params.myId}" method="post">
    <div class="pageHeader">
        <input type="hidden" id="parentId" name="parentId" value="${params.myId}">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>商户:
                        <select id="merchantNo" name="merchantNo">
                            <option value="">全部</option>
                            <c:forEach var="merchantNo" items="${merchantNoMap }">
                                <option value="${merchantNo.key }" <c:if test="${merchantNo.key eq params.merchantNo}">selected="selected"</c:if>>
                                        ${merchantNo.value}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>批次: <input type="text" id="batchId" name="batchId" value="${params.batchId}"/></td>
                    <td>用户意向:
                        <select id="userIntention" name="userIntention">
                            <option value="">全部</option>
                            <c:forEach var="userIntention" items="${userIntentionMap }">
                                <option value="${userIntention.key }" <c:if test="${userIntention.key eq params.userIntention}">selected="selected"</c:if>>
                                        ${userIntention.value}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>销售公司:
                        <select id="saleCompany" name="saleCompany">
                            <option value="">全部</option>
                            <c:forEach var="saleCompany" items="${saleCompanyMap }">
                                <option value="${saleCompany.key }" <c:if test="${saleCompany.key eq params.saleCompany}">selected="selected"</c:if>>
                                        ${saleCompany.value}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>坐席: <input type="text" id="saler" name="saler" value="${params.currentCollectionUserName}"/></td>
                    <td>
                        分单时间:
                        <input type="text" id="startDispatcherTime" name="startDispatcherTime" value="${params.startDispatcherTime}" class="date textInput readonly"
                               datefmt="yyyy-MM-dd" readonly="readonly"/>
                        至
                        <input type="text" id="endDispatcherTime" name="endDispatcherTime" value="${params.endDispatcherTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"
                               readonly="readonly"/>
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
        <c:set var="page" value="${page}"></c:set>
        <%@ include file="/WEB-INF/viewback/page.jsp" %>
        <table class="table" style="width: 100%;" layoutH="160"
               nowrapTD="false">
            <thead>
            <tr>
                <th align="center" width="50">
                    序号
                </th>
                <th align="center" width="50">
                    批次
                </th>
                <th align="center" width="50">
                    销售公司
                </th>
                <th align="center" width="50">
                    坐席
                </th>
                <th align="center" width="50">
                    商户号
                </th>
                <th align="center" width="50">
                    User ID
                </th>
                <th align="center" width="50">
                    客户姓名
                </th>
                <th align="center" width="50">
                    手机号
                </th>
                <th align="center" width="50">
                    注册时间
                </th>
                <th align="center" width="50">
                    当前状态
                </th>
                <th align="center" width="50">
                    用户意向
                </th>
                <th align="center" width="50">
                    备注
                </th>
                <th align="center" width="50">
                    分单时间
                </th>
                <th align="center" width="250">
                    操作
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${page.items}" varStatus="status">
                <tr target="id" rel="${order.id }">
                    <td align="center" width="50">
                            ${status.count}
                    </td>
                    <td align="center" width="50">
                            ${order.batchId}
                    </td>
                    <td align="center" width="100">
                            ${saleCompanyMap[order.companyId]}
                    </td>
                    <td align="center" width="50">
                            ${order.currentCollectionUserName}
                    </td>
                    <td align="center" width="50">
                            ${merchantNoMap[order.merchantNo]}
                    </td>
                    <td align="center" width="50">
                            ${order.userId}
                    </td>
                    <td align="center" width="50">
                            ${order.userName}
                    </td>
                    <td align="center" width="50">
                            ${order.mobile}
                    </td>
                    <td align="center" width="100">
                            <fmt:formatDate value="${order.registerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td align="center" width="50">
                        <c:choose>
                            <c:when test="${order.loanOrderStatus eq 0}">无在借订单</c:when>
                            <c:when test="${order.loanOrderStatus eq 1}">有在借订单</c:when>
                        </c:choose>
                    </td>
                    <td>
                        <select id="userIntention2" name="userIntention2">
                            <option value="${order.userIntention }" <c:if test="${order.userIntention eq 1}">selected="selected"</c:if>>有意向</option>
                            <option value="${order.userIntention }" <c:if test="${order.userIntention eq 2}">selected="selected"</c:if>>无意向</option>
                            <option value="${order.userIntention }" <c:if test="${order.userIntention eq 3}">selected="selected"</c:if>>未接通</option>
                        </select>
                    </td>
                    <td align="center" width="50">
                            ${order.remark}
                    </td>
                    <td align="center" width="100">
                            <fmt:formatDate value="${order.dispatcherTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td align="center" width="350">
                        <%--<a href="collectionOrder/toxianqin?id=${order.id }" target="navtab"--%>
                           <%--style="color: #cd0a0a;margin: 8px;font-size: 15px;text-decoration: none;">查看手机号--%>
                        <%--</a>--%>
                        <%--<a href="collectionOrder/tokokuan?id=${order.id }&myId=${params.myId}" target="dialog"--%>
                           <%--style="color: #1b8d0f;margin: 8px;font-size: 15px;text-decoration: none;">添加备注--%>
                        <%--</a>--%>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>

    <script type="text/javascript">
        function getMsg(obj) {
            var tt = $(".pageContent tbody tr").is(':checked').value();
            var bol = $(obj).is(':checked');
            var text = bol.get(1).text();
            console.log(text);
        }

    </script>

</form>

<script type="text/javascript">
    function checkAll(obj) {
        var bol = $(obj).is(':checked');
        $("input[name='checkItem']").attr("checked", bol);
    }
    function sel(obj) {
        var bol = true;
        var check = $(obj).find("input[name='checkItem']");
        if ($(check).is(':checked')) {
            bol = false;
        }
        $(check).attr("checked", bol);
    }
    function getOrderIds(obj) {
        var href = $(obj).attr("href");
        if (href.indexOf('&ids') > -1) {
            href = href.substring(0, href.indexOf('&ids'));
        }
        var hasDifferentGroup = '0';
        var selectedGroup = "";
        $("input[name='checkItem']:checked").each(function () {
            var group = $(this).attr("group");
            if (group != undefined && group != '') {
                if (selectedGroup == '') {
                    selectedGroup = group;//第一次赋值
                } else if (selectedGroup != group) {// 之后和第一次的值比较，有不同就GG
                    hasDifferentGroup = '1';
                }
            }
        })
        if (hasDifferentGroup) {
            var ids = "";
            $("input[name='checkItem']:checked").each(function () {
                ids = ids + "," + $(this).val();
            });
            var toHref = href + "&ids=" + ids.substring(1) + "&groupStatus=" + hasDifferentGroup;
            $(obj).attr("href", toHref);
        } else {
            return;
        }
    }
</script>

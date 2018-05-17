<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<style type="text/css">
   <c:if test="${orderType == 1}">
    .smallEstimate{
        background:#CCCCCC;
    }
   </c:if>
<c:if test="${orderType == 2}">
   .bigEstimate{
       background:#CCCCCC;
   }

    </c:if>
   .mingtianpaidan tr td{
       padding:5px;
   }
</style>
<form id="myPagerForm" onsubmit="return navTabSearch(this);" action="estimate/list?myId=${params.myId}" method="post">
    <div class="pageHeader">

    </div>
    <input type="hidden" id="orderType" name="orderType" value=""/>
    <input type="hidden" id="orderAge" name="orderAge" value=""/>
    <div class="pageContent">
        <jsp:include page="${BACK_URL}/rightSubList">
            <jsp:param value="${params.myId}" name="parentId"/>
        </jsp:include>
        <div style="background: #CCCCCC">
        <table class="mingtianpaidan">
            <tr>
                <td>明日派单预估</td>
            </tr>
            <c:if test="${orderType == 1}">
                <tr>
                    <td>F-M1组共0人，人均0单</td>
                </tr>
                <tr>
                    <td>F-M2组共0人，人均0单</td>
                </tr>
                <tr>
                    <td>F-M3+组共0人，人均0单</td>
                </tr>
                <tr>
                    <td>F-M6+组共0人，人均0单</td>
                </tr>
            </c:if>
            <c:if test="${orderType == 2}">
                <tr>
                    <td>S1组共0人，人均0单</td>
                </tr>
                <tr>
                    <td>M1组共0人，人均0单</td>
                </tr>
                <tr>
                    <td>M2组共0人，人均0单</td>
                </tr>
                <tr>
                    <td>M3+组共0人，人均0单</td>
                </tr>
                <tr>
                    <td>M6+组共0人，人均0单</td>
                </tr>
            </c:if>
        </table>
        </div>
        <table class="table" style="width: 100%;" layoutH="160"
               nowrapTD="false">
            <thead>
            <tr>
                <!-- 				    <th align="center" width="10"> -->
                <!-- 				      <input type="checkbox" id="checkAlls" onclick="checkAll(this);"/> -->
                <!-- 				    </th> -->
                <th align="center" width="50">
                    到期时间
                </th>
                <th align="center" width="50">
                    到期订单数
                </th>
                <th align="center" width="50">
                    到期订单本金
                </th>
                <th align="center" width="50">
                    入催时间
                </th>
                <th align="center" width="50">
                    账龄
                </th>
                <th align="center" width="50">
                    入催单数预估
                </th>
                <th align="center" width="50">
                    入催金额预估
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${estimateList}" varStatus="status">
                <tr target="id" rel="${order.id }">

                    <td align="center" width="50">
                            <fmt:formatDate value="${order.overDate}" pattern="yyyy-MM-dd"/>
                    </td>
                    <td align="center" width="50">
                            ${order.orderCount}
                    </td>
                    <td align="center" width="50">
                            ${order.amountTotal}
                    </td>
                    <td align="center" width="50">
                           <fmt:formatDate value="${order.collectionDate}" pattern="yyyy-MM-dd"/>
                    </td>
                    <td align="center" width="50">
                            ${order.orderAge}
                    </td>
                    <td align="center" width="50">
                            ${order.estimateOrderCount}
                    </td>
                    <td align="center" width="50">
                            ${order.estimateAmountCount}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${page}"></c:set>
        <!-- 分页 -->
        <%@ include file="/WEB-INF/viewback/page.jsp" %>
    </div>

    <script type="text/javascript">

    </script>

</form>

<script type="text/javascript">
    function changeOrderType(orderType,orderAge){
        $("#orderType").val(orderType);
        $("#orderAge").val(orderAge);
        $("#myPagerForm").submit();
        return false;
    }
</script>

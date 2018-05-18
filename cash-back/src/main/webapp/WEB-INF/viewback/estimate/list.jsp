<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<style type="text/css">
   <c:if test="${orderType == 2}">
    .smallEstimate{
        background:#CCCCCC;
    }
   </c:if>
<c:if test="${orderType == 1}">
   .bigEstimate{
       background:#CCCCCC;
   }

    </c:if>
   .mingtianpaidan{
    background: #F5F5F5;
       width: 100%;
   }
   .mingtianpaidan tr td{
       padding:12px;padding-left: 20px;padding-top:12px;

   }
    .dataTr td{
        height: 30px;
    }
</style>
<form id="myPagerForm" onsubmit="return navTabSearch(this);" action="estimate/list?myId=${params.myId}" method="post">
    <div class="pageHeader">

    </div>
    <input type="hidden" id="orderType" name="orderType" value=""/>
    <input type="hidden" id="orderAge" name="orderAge" value=""/>
    <input type="hidden" id="testDate" name="testDate" value="${testDate}"/>
    <div class="pageContent">
        <jsp:include page="${BACK_URL}/rightSubList">
            <jsp:param value="${params.myId}" name="parentId"/>
        </jsp:include>
        <div style="padding:5px">
        <table class="mingtianpaidan">
            <tr>
                <td style="padding:20px;">明日派单预估</td>
            </tr>
            <c:if test="${orderType == 1}">
                <tr>
                    <td>F-M1组共${dispatchInfo.m1.userCount}人，人均${dispatchInfo.m1.orderCount}单</td>
                </tr>
                <tr>
                    <td>F-M2组共${dispatchInfo.m2.userCount}人，人均${dispatchInfo.m2.orderCount}单</td>
                </tr>
                <tr>
                    <td>F-M3+组共${dispatchInfo.m3.userCount}人，人均${dispatchInfo.m3.orderCount}单</td>
                </tr>
                <tr style="padding-bottom: 20px">
                    <td>F-M6+组共${dispatchInfo.m6.userCount}人，人均${dispatchInfo.m6.orderCount}单</td>
                </tr>
            </c:if>
            <c:if test="${orderType == 2}">
                <tr>
                    <td>S1组共${dispatchInfo.s1.userCount}人，人均${dispatchInfo.s1.orderCount}单</td>
                </tr>
                <tr>
                    <td>M1组共${dispatchInfo.m1.userCount}人，人均${dispatchInfo.m1.orderCount}单</td>
                </tr>
                <tr>
                    <td>M2组共${dispatchInfo.m2.userCount}人，人均${dispatchInfo.m2.orderCount}单</td>
                </tr>
                <tr>
                    <td>M3+组共${dispatchInfo.m3.userCount}人，人均${dispatchInfo.m3.orderCount}单</td>
                </tr>
                <tr>
                    <td style="padding-bottom: 20px">M6+组共0人，人均0单</td>
                </tr>
            </c:if>
        </table>
        </div>
        <div style="padding-right:5px;padding-left: 5px;margin-top:15px;">
            <div style="background: #87CEFA;height: 30px;line-height: 30px;padding:3px; color: #FFFFFF;">
                未来几天入催情况：近七天本金平均入催率：
                <span style="color: red">
                    <fmt:formatNumber value="${oldAmountRate/100}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>%
                </span>，
                订单平均入催率：
                <span style="color: red">
                    <fmt:formatNumber value="${oldOrderRate/100}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>%
                </span>
            </div>

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
                <tr target="id" rel="${order.id }" class="dataTr">

                    <td align="center" width="50">
                            <fmt:formatDate value="${order.overDate}" pattern="yyyy-MM-dd"/>
                    </td>
                    <td align="center" width="50">
                            ${order.orderCount}
                    </td>
                    <td align="center" width="50">
                            <fmt:formatNumber value="${order.amountTotal/100}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>
                    </td>
                    <td align="center" width="50">
                           <fmt:formatDate value="${order.collectionDate}" pattern="yyyy-MM-dd"/>
                    </td>
                    <td align="center" width="50">
                            ${ORDER_AGE_MAP[order.orderAge]}
                    </td>
                    <td align="center" width="50">
                            ${order.estimateOrderCount}
                    </td>
                    <td align="center" width="50">
                        <fmt:formatNumber value="${order.estimateAmountCount/100}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>
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

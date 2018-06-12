<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="divider"></div>
<div class="pageContent">
    <table class="table" style="width: 100%;">
        <thead>
        <tr style="height: 24px;">
            <th align="center" width="60">
                选择
            </th>
            <th align="center" width="100">
                联系人
            </th>
            <th align="center" width="80">
                关系
            </th>
            <th align="center" width="150">
                联系人电话
            </th>
            <th align="center" width="100">
                催收类型
            </th>
            <th align="center" width="100">
                逾期等级
            </th>
            <th align="center" width="200">
                承诺还款时间
            </th>

            <th align="center" width="120">
                沟通情况
            </th>
            <th align="center" width="100">
                催收状态
            </th>
            <th align="center" width="120">
                催收员
            </th>
            <th align="center" width="300">
                备注
            </th>
            <th align="center" width="200">
                催收时间
            </th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="record" items="${list }">
            <tr target="recordId" rel="${record.id }" style="height: 24px;">
                <td align="center">
                    <input type="radio" name="collectionRecord" id="${record.id }" onchange="getChooseVal(this);">
                </td>
                <td align="center">
                        ${record.contactName }
                </td>
                <td align="center">
                        ${record.relation }
                </td>
                <td align="center">
                        ${record.contactPhone }
                </td>
                <td align="center">
                    <c:if test="${record.collectionType == '1'}">电话催收</c:if>
                    <c:if test="${record.collectionType == '2'}">短信催收</c:if>
                </td>
                <td align="center">
                        ${overdueLevelMap[record.currentOverdueLevel]}
                </td>
                <td align="center">
                    <fmt:formatDate value="${record.collectionDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td align="center">
                        ${communicationSituationsMap[record.communicationStatus]}
                </td>
                <td align="center">
                        ${orderStatusMap[record.orderState]}
                </td>
                <td align="center">
                        ${record.collectionPerson }
                </td>
                <td align="center">
                        ${record.content }
                </td>
                <td align="center">
                    <fmt:formatDate value="${record.collectionDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%-- <c:set var="page" value="${pm}"></c:set>
     <!-- 分页 -->
     <%@ include file="../page.jsp" %>--%>
</div>
<%--<script type="text/javascript">
    $("#searchOrderCollectionRecord").click(function () {
        alertMsg.warn("监听到点击事件")
    });
</script>--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="operationRecord/list?myId=${params.myId}" method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                     <td>
                         操 作 时 间：
                         <input type="text" id="beginOperateTime" name="beginOperateTime" value="${params.beginOperateTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
                         至
                         <input type="text" id="endOperateTime" name="endOperateTime"  value="${params.endOperateTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
                     </td>
                    <%--<c:choose>
                        <c:when test="${not empty params.CompanyPermissionsList}">
                            <td>催 收 公 司：
                                <select name="companyId">
                                    <option value="">全部</option>
                                    <c:forEach var="company" items="${MmanLoanCollectionCompanys }">
                                        <c:forEach var="companyViw" items="${params.CompanyPermissionsList}">
                                            <c:if test="${companyViw.companyId eq company.id}">
                                                <option value="${company.id }" <c:if test="${company.id eq params.companyId}">selected = "selected"</c:if>>
                                                        ${company.title}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </select>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>催 收 公 司：
                                <select name="companyId">
                                    <option value="">全部</option>
                                    <c:forEach var="company" items="${MmanLoanCollectionCompanys }">
                                        <option value="${company.id }" <c:if test="${company.id eq params.companyId}">selected = "selected"</c:if>>
                                                ${company.title}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        操作账号：
                        <select name="type">
                            <option value="">全部</option>
                            <c:forEach var="problemFeedBackType" items="${problemFeedBackType}">
                                <option value="${problemFeedBackType.key}" <c:if test="${problemFeedBackType.key eq params.type}">selected="selected"</c:if> >${problemFeedBackType.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>--%>
                <td>
                    操作账号：
                    <input type="text" id="operateUserAccount" name="operateUserAccount" value="${params.operateUserAccount }"/>
                </td>
                <%--<tr>
                    <td>
                        借 款 编 号：
                        <input type="text" name="loanId" value="${params.loanId }"/>
                    </td>
                    <td>
                        执&nbsp;&nbsp;&nbsp;&nbsp;行&nbsp;&nbsp;&nbsp;人：
                        <input type="text" name="operator" value="${params.operator }"/>
                    </td>
                    <td>
                        创&nbsp;&nbsp;建&nbsp;&nbsp;人：
                        <input type="text" name="createUsername" value="${params.createUsername }"/>
                    </td>
                    <td>
                        状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：
                        <select name="status">
                            <option value="">全部</option>
                            <c:forEach var="problemFeedBackStatus" items="${problemFeedBackStatus}">
                                <option value="${problemFeedBackStatus.key}" <c:if test="${problemFeedBackStatus.key eq params.status}">selected="selected"</c:if>>${problemFeedBackStatus.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        问题编号：
                        <input type="text" style="width: 50px;" name="problemNumber" value="${params.problemNumber }"/>
                    </td>--%>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
        <table class="table" style="width: 100%;" layoutH="116"
               nowrapTD="false">
            <thead>
            <tr>
                <th align="center" width="30">
                    编号
                </th>
                <th align="center" width="80">
                    操作账号
                </th>
                <th align="center" width="80">
                    操作时间
                </th>
                <th align="center" width="50">
                    借款id
                </th>
                <th align="center" width="50">
                    借款人姓名
                </th>
                <th align="center" width="60">
                    借款人电话
                </th>
                <th align="center" width="60">
                    催收时间(开始)
                </th>
                <th align="center" width="60">
                    催收时间(结束)
                </th>
                <th align="center" width="60">
                    分单时间(开始)
                </th>
                <th align="center" width="80">
                    分单时间(结束)
                </th>
                <th align="center" width="70">
                    逾期天数(开始)
                </th>
                <th align="center" width="70">
                    逾期天数(结束)
                </th>
                <th align="center" width="40">
                    跟进等级
                </th>
                <th align="center" width="80">
                    催收公司
                </th>
                <th align="center" width="50">
                    催收组
                </th>
                <th align="center" width="50">
                    催收状态
                </th>
                <th align="center" width="50">
                    当前催收员
                </th>
                <th align="center" width="60">
                    操作来源
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="record" items="${page.items }" varStatus="status" >
                <tr target="id" rel="${record.id }">
                    <td>
                            ${status.count}
                    </td>
                    <td>
                            ${record.operateUserAccount}
                    </td>
                    <td>
                        <fmt:formatDate value="${record.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                    </td>
                    <td>
                            ${record.loanId}
                    </td>
                    <td>
                            ${record.loanUserName}
                    </td>
                    <td>
                            ${record.loanUserPhone}
                    </td>
                    <td>
                            ${record.beginCollectionTime}
                    </td>
                    <td>
                            ${record.endCollectionTime}
                    </td>
                    <td>
                            ${record.beginDispatchTime}
                    </td>
                    <td>
                            ${record.endDispatchTime}
                    </td>
                    <td>
                            ${record.beginOverDuedays}
                    </td>
                    <td>
                            ${record.endOverDuedays}
                    </td>
                    <td>
                            ${record.followUpGrad}
                    </td>
                    <td>
                            ${record.collectionCompanyId}
                    </td>
                    <td>
                            ${record.collectionGroup}
                    </td>
                    <td>
                            ${record.collectionStatus}
                    </td>
                    <td>
                            ${record.currentCollectionUserName}
                    </td>
                    <td>
                        <c:if test="${record.source eq '1'}">催收总订单</c:if>
                        <c:if test="${record.source eq '2'}">我的催收订单</c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${page }"></c:set>
        <!-- 分页222 -->
        <%@ include file="../page.jsp" %>
    </div>
    <c:if test="not empty ${message}">
        alert("${message}");
        </c:if>
    <script type="text/javascript">
        function getExcelParams(obj){
            var href=$(obj).attr("href");
            href=href.split("&")[0];
            var beginOperateTime = $("#beginOperateTime").val();
            if(beginOperateTime != null && beginOperateTime != ''){
                href+="&beginOperateTime="+beginOperateTime;
            }
            var endOperateTime = $("#endOperateTime").val();
            if(endOperateTime != null && endOperateTime != ''){
                href+="&endOperateTime="+endOperateTime;
            }
            var operateUserAccount = $("#operateUserAccount").val();
            if(operateUserAccount != null && operateUserAccount != ''){
                href+="&operateUserAccount="+operateUserAccount;
            }
            $(obj).attr("href",href);
        }
    </script>
</form>
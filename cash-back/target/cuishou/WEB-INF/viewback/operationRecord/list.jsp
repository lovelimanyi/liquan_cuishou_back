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
                    <td>
                        操作账号：
                        <input type="text" id="operateUserAccount" name="operateUserAccount" value="${params.operateUserAccount }"/>
                    </td>
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
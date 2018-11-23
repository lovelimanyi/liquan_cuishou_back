<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="dianxiao/getMyDianXiaoOrderList?myId=${params.myId}" method="post">

    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>

                    <td>
                        借款人:
                        <input type="text" name="loanUserName" value="${params.loanUserName }" />
                    </td>
                    <td>
                        借款人手机号:
                        <input type="text" name="loanUserPhone" value="${params.loanUserPhone }" />
                    </td>
                    <td>
                        还款状态:
                        <select name="orderStatus" >
                            <option value="">全部</option>
                            <option value="1" <c:if test="${'1' eq params.orderStatus}">selected = "selected"</c:if>>未还款</option>
                            <option value="4" <c:if test="${'4' eq params.orderStatus}">selected = "selected"</c:if>>已还款</option>
                        </select>
                    </td>
                    <td>
                        客户意向:
                        <select name="repaymentIntention" >
                            <option value="">全部</option>
                            <option value="1" <c:if test="${'1' eq params.repaymentIntention}">selected = "selected"</c:if>>未接通</option>
                            <option value="2" <c:if test="${'2' eq params.repaymentIntention}">selected = "selected"</c:if>>已还款</option>
                            <option value="3" <c:if test="${'3' eq params.repaymentIntention}">selected = "selected"</c:if>>稍后还款</option>
                            <option value="4" <c:if test="${'4' eq params.repaymentIntention}">selected = "selected"</c:if>>下午还款</option>
                            <option value="5" <c:if test="${'5' eq params.repaymentIntention}">selected = "selected"</c:if>>晚上还款</option>
                            <option value="6" <c:if test="${'6' eq params.repaymentIntention}">selected = "selected"</c:if>>无还款意向</option>
                            <option value="7" <c:if test="${'7' eq params.repaymentIntention}">selected = "selected"</c:if>>挂电话</option>
                            <option value="8" <c:if test="${'8' eq params.repaymentIntention}">selected = "selected"</c:if>>过几天还款</option>
                            <option value="9" <c:if test="${'9' eq params.repaymentIntention}">selected = "selected"</c:if>>其他</option>
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
        <c:set var="page" value="${pm}"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp"%>
        <table class="table" style="width: 100%;" layoutH="115"
               nowrapTD="false">
            <thead>
            <tr>
                <th align="center" width="30">
                    序号
                </th>
                <th align="center" width="40">
                    借款编号
                </th>
                <th align="center" width="40">
                    借款人
                </th>
                <th align="center" width="50">
                    借款人手机号
                </th>
                <th align="center" width="120">
                    借款时间
                </th>
                <th align="center" width="120">
                    应还时间
                </th>
                <th align="center" width="80">
                    借款金额
                </th>
                <th align="center" width="80">
                    服务费
                </th>
                <th align="center" width="60">
                    还款状态
                </th>
                <th align="center" width="50">
                    电销员姓名
                </th>
                <th align="center" width="80">
                    商户号
                </th>
                <th align="center" width="180">
                    客户意向
                </th>
                <th align="center" width="180">
                    备注
                </th>
                <th align="center" width="150">
                    操作
                </th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="order" items="${pm.items }" varStatus="status">
                <tr target="id" rel="${order.id }">
                    <td>
                            ${status.count}
                    </td>
                    <td>
                            ${order.loanId}
                    </td>
                    <td>
                            ${order.loanUserName}
                    </td>
                    <td>
                            ${order.loanUserPhone}
                    </td>
                    <td>
                        <fmt:formatDate value="${order.loanStartDate }" pattern="yyyy-MM-dd"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${order.loanEndDate }" pattern="yyyy-MM-dd"/>
                    </td>
                    <td>
                            ${order.loanMoney}
                    </td>
                    <td>
                            ${order.loanServiceCharge}
                    </td>
                    <td>
                        <c:if test="${order.orderStatus == 1}">未还款</c:if>
                        <c:if test="${order.orderStatus == 4}">已还款</c:if>
                    </td>

                    <td>
                            ${order.currentCollectionUserName }
                    </td>

                    <td>
                        <c:forEach var="merchant" items="${merchantMap }">
                            <c:if test="${merchant.key eq order.merchantNo}">${merchant.value}</c:if>
                        </c:forEach>
                    </td>

                    <td>
                        <c:if test="${1 == order.repaymentIntention}"> 未接通 </c:if>
                        <c:if test="${2 == order.repaymentIntention}">已还款</c:if>
                        <c:if test="${3 == order.repaymentIntention}">稍后还款</c:if>
                        <c:if test="${4 == order.repaymentIntention}">下午还款</c:if>
                        <c:if test="${5 == order.repaymentIntention}">晚上还款</c:if>
                        <c:if test="${6 == order.repaymentIntention}">无还款意向</c:if>
                        <c:if test="${7 == order.repaymentIntention}">挂电话</c:if>
                        <c:if test="${8 == order.repaymentIntention}">过几天还款</c:if>
                        <c:if test="${9 == order.repaymentIntention}">其他</c:if>
                    </td>
                    <td>
                            ${order.remark }
                    </td>
                    <td>
                        <c:if test="${1 == order.orderStatus}">
                            <a href="dianxiao/toUpdateDianXiaoOrderPage?id=${order.id }&myId=${params.myId}" target="dialog"
                               style="color: #1b8d0f;margin: 8px;font-size: 15px;text-decoration: none; "> <button>修改</button>
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="#">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>

                    <%--<td>
                        借款编号:
                        <input type="text" name="orderId"
                            value="${params.orderId }" />
                    </td>
                    --%>
                    <td>
                        催收组(逾期等级):
                        <%-- <select name="">
                            <option value="">全部</option>
                            <c:forEach var="" items="">
                                <option value=""
                                    <c:if test="}">selected = "selected"</c:if>>
                                        </option>
                            </c:forEach>
                        </select> --%>
                        <select name="overdueLevel">
                            <option value="">全部</option>
                             <%--<option value=""  <c:if test="${'' eq params.overdueLevel}">selected = "selected"</c:if>>全部</option>
                            <option value="3" <c:if test="${'3' eq params.overdueLevel}">selected="selected"</c:if>>S1</option>
                            <option value="4" <c:if test="${'4' eq params.overdueLevel}">selected="selected"</c:if>>S2</option>
                            <option value="5" <c:if test="${'5' eq params.overdueLevel}">selected="selected"</c:if>>M1-M2</option>
                            <option value="6" <c:if test="${'6' eq params.overdueLevel}">selected="selected"</c:if>>M2-M3</option>
                            <option value="7" <c:if test="${'7' eq params.overdueLevel}">selected="selected"</c:if>>M3+</option>
                            <option value="8" <c:if test="${'8' eq params.overdueLevel}">selected="selected"</c:if>>M6+</option>
                            <option value="11" <c:if test="${'11' eq params.overdueLevel}">selected="selected"</c:if>>F-M1</option>
                            <option value="12" <c:if test="${'12' eq params.overdueLevel}">selected="selected"</c:if>>F-M2</option>
                            <option value="13" <c:if test="${'13' eq params.overdueLevel}">selected="selected"</c:if>>F-M3</option>
                            <option value="16" <c:if test="${'16' eq params.overdueLevel}">selected="selected"</c:if>>F-M6</option>--%>
                            <c:forEach var="overdueLevel" items="${overdueLevelMap}">
                                <option value="${overdueLevel.key}"
                                        <c:if test="${overdueLevel.key eq params.overdueLevel}">selected="selected"</c:if>>
                                        ${overdueLevel.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        催收状态:
                        <select name="collectionStatus">
                            <option value="">全部</option>
                            <c:forEach var="status" items="${orderStatusMap}">
                                <option value="${status.key}"
                                        <c:if test="${status.key eq params.collectionStatus}">selected="selected"</c:if>>
                                        ${status.value}</option>
                            </c:forEach>
                        </select>
                        <%--<select name	="collectionStatus" >
                            <option value="" <c:if test="${'' eq params.collectionStatus}">selected = "selected"</c:if>>全部</option>
                            <option value="0"  <c:if test="${'0' eq params.collectionStatus}">selected = "selected"</c:if>>待催收</option>
                            <option value="1" <c:if test="${'1' eq params.collectionStatus}">selected = "selected"</c:if>>催收中</option>
                            <option value="2" <c:if test="${'2' eq params.collectionStatus}">selected = "selected"</c:if>>承诺还款</option>
                            <option value="3" <c:if test="${'3' eq params.collectionStatus}">selected = "selected"</c:if>>委外中</option>
                            &lt;%&ndash; <option value="4" <c:if test="${'4' eq params.collectionStatus}">selected = "selected"</c:if>>委外成功</option> &ndash;%&gt;
                            <option value="4" <c:if test="${'4' eq params.collectionStatus}">selected = "selected"</c:if>>催收成功</option>
                        </select>--%>
                    </td>
                    <td>
                        催收类型:
                        <%-- <select name="collectionType">
                            <option value="">全部</option>
                            <c:forEach var="type" items="${collectionType}">
                                <option value="${type.id}"
                                    <c:if test="${type.id eq params.collectionType}">selected = "selected"</c:if>>
                                        ${type.label}</option>
                            </c:forEach>
                        </select> --%>
                        <select name="collectionType">
                            <option value="" <c:if test="${'' eq params.collectionStatus}">selected="selected"</c:if>>全部</option>
                            <option value="1" <c:if test="${'1' eq params.collectionType}">selected="selected"</c:if>>电话催收</option>
                            <option value="2" <c:if test="${'2' eq params.collectionType}">selected="selected"</c:if>>短信催收</option>
                        </select>
                    </td>
                    <td>
                        催&nbsp;&nbsp;收&nbsp;&nbsp;员:
                        <input type="text" name="collectionName" value="${params.collectionName }" style="width: 60px;"/>
                    </td>
                    <td>
                        沟通情况:无
                        <select name="communicationSituation">
                            <option value="">全部</option>
                            <c:forEach var="status" items="${communicationSituations}">
                                <option value="${status.id}"
                                        <c:if test="${status.id eq params.communicationSituation}">selected="selected"</c:if>>
                                        ${status.communicationLabel}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <%-- <td>
                        施压等级:
                        <select name="stressLevel">
                            <option value="">全部</option>
                            <c:forEach var="level" items="${stressLevel}">
                                <option value="${level.id}"
                                    <c:if test="${level.id eq params.stressLevel}">selected = "selected"</c:if>>
                                        ${level.label}</option>
                            </c:forEach>
                        </select>
                        <select name="stressLevel" >
                            <option value=""  <c:if test="${'' eq params.stressLevel}">selected = "selected"</c:if>>全部</option>
                            <option value="1" <c:if test="${'1' eq params.stressLevel}">selected = "selected"</c:if>>一级施压</option>
                            <option value="2" <c:if test="${'2' eq params.stressLevel}">selected = "selected"</c:if>>二级施压</option>
                            <option value="3" <c:if test="${'3' eq params.stressLevel}">selected = "selected"</c:if>>三级施压</option>
                        </select>
                    </td> --%>
                    <%--<td>
                        催收时间:
                        <input type="text" name="collectionDateBegin" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"
                            value="${params.collectionDateBegin }" />
                    至
                        <input type="text" name="collectionDateEnd" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"
                            value="${params.collectionDateEnd }" />
                    </td>
                    <td>
                        催收建议:
                        <select name="collectionAdvice" >
                        <option value="" <c:if test="${'' eq params.collectionAdvice}">selected = "selected"</c:if>>全部</option>
                        <option value="1" <c:if test="${'1' eq params.collectionAdvice}">selected = "selected"</c:if>>审核通过</option>
                        <option value="2" <c:if test="${'2' eq params.collectionAdvice}">selected = "selected"</c:if>>审核拒绝</option>
                        <option value="3" <c:if test="${'3' eq params.collectionAdvice}">selected = "selected"</c:if>>无建议</option>
                        </select>
                    </td>
                    <td>
                        风控标签:
                        <select name="fengKongLabel" >
                            <option value="">全部</option>
                            <c:forEach var="fengKongLabel" items="${fengKongLabels }">
                                <option value="${fengKongLabel.id }" <c:if test="${fengKongLabel.id eq labels.get(fn:replace(params.fengKongLabel,'%',''))}">selected
                                        ="selected"</c:if>>
                                        ${fengKongLabel.fkLabel}
                                </option>
                            </c:forEach>
                        </select>
                    </td>--%>
                    <td>
                        <div class="buttonActive" style="margin-left: 350px;">
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
                        <input type="radio" name="collectionRecord">
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
                        ${record.collectionAdviceRemark }
                    </td>
                    <td align="center">
                        <fmt:formatDate value="${record.collectionDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${pm}"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp" %>
    </div>
</form>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="collectionRecord/getMmanLoanCollectionRecord?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						借款编号:
						<input type="text" name="orderId"
							value="${params.orderId }" />
					</td>
					<td>
						催&nbsp;&nbsp;收&nbsp;&nbsp;员:
						<input type="text" name="collectionName"
							value="${params.collectionName }" />
					</td>
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
						<select name="overdueLevel" >
							<option value="">全部</option>
							<%-- <option value=""  <c:if test="${'' eq params.overdueLevel}">selected = "selected"</c:if>>全部</option> --%>
							<option value="3" <c:if test="${'3' eq params.overdueLevel}">selected = "selected"</c:if>>S1</option>
							<option value="4" <c:if test="${'4' eq params.overdueLevel}">selected = "selected"</c:if>>S2</option>
							<option value="5" <c:if test="${'5' eq params.overdueLevel}">selected = "selected"</c:if>>M1-M2</option>
							<option value="6" <c:if test="${'6' eq params.overdueLevel}">selected = "selected"</c:if>>M2-M3</option>
							<option value="7" <c:if test="${'7' eq params.overdueLevel}">selected = "selected"</c:if>>M3+</option>
						</select>
					</td>
					<td>
						催收状态:
						<%-- <select name="collectionStatus">
							<option value="">全部</option>
							<c:forEach var="statu" items="${status}">
								<option value="${statu.id}" 
									<c:if test="${statu.id eq params.collectionStatus}">selected = "selected"</c:if>>
										${statu.label}</option>
							</c:forEach>
						</select> --%>
						<select name="collectionStatus" >
							<option value="" <c:if test="${'' eq params.collectionStatus}">selected = "selected"</c:if>>全部</option>
							<option value="0"  <c:if test="${'0' eq params.collectionStatus}">selected = "selected"</c:if>>待催收</option>
							<option value="1" <c:if test="${'1' eq params.collectionStatus}">selected = "selected"</c:if>>催收中</option>
							<option value="2" <c:if test="${'2' eq params.collectionStatus}">selected = "selected"</c:if>>承诺还款</option>
							<option value="3" <c:if test="${'3' eq params.collectionStatus}">selected = "selected"</c:if>>委外中</option>
							<%-- <option value="4" <c:if test="${'4' eq params.collectionStatus}">selected = "selected"</c:if>>委外成功</option> --%>
							<option value="4" <c:if test="${'4' eq params.collectionStatus}">selected = "selected"</c:if>>催收成功</option>
						</select>
					</td>
				</tr>	
				<tr>	
					<%--<td>
						催收类型:
						&lt;%&ndash; <select name="collectionType">
							<option value="">全部</option>
							<c:forEach var="type" items="${collectionType}">
								<option value="${type.id}" 
									<c:if test="${type.id eq params.collectionType}">selected = "selected"</c:if>>
										${type.label}</option>
							</c:forEach>
						</select> &ndash;%&gt;
						<select name="collectionType" >
							<option value=""  <c:if test="${'' eq params.collectionStatus}">selected = "selected"</c:if>>全部</option>
							<option value="1" <c:if test="${'1' eq params.collectionType}">selected = "selected"</c:if>>电话催收</option>
							<option value="2" <c:if test="${'2' eq params.collectionType}">selected = "selected"</c:if>>短信催收</option>
						</select>
					</td>--%>
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
					<td>
						催收时间:
						<input type="text" name="collectionDateBegin" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"
							value="${params.collectionDateBegin }" />
					至
						<input type="text" name="collectionDateEnd" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"
							value="${params.collectionDateEnd }" />
					</td>
					<td>
						风控标签:
						<select name="fengKongLabel" >
							<option value="">全部</option>
							<c:forEach var="fengKongLabel" items="${fengKongLabels }">
								<option value="${fengKongLabel.id }" <c:if test="${fengKongLabel.id eq labels.get(params.fengKongLabel)}">selected = "selected"</c:if>>
										${fengKongLabel.fkLabel}
								</option>
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
						<!-- <th align="center" width="50">
							催收员姓名
						</th> -->
						<th align="center" width="50">
							借款人
						</th>
						<%--<th align="center" width="100">
							联系人类型
						</th>
						<th align="center" width="50">
							关系
						</th>--%>
						<th align="center" width="60">
							联系人姓名
						</th>
						<th align="center" width="80">
							联系人电话
						</th>
						<!-- <th align="center" width="80">
							施压等级
						</th> -->
						<th align="center" width="80">
							当前催收状态
						</th>
						<%--<th align="center" width="80">
							催收类型
						</th>--%>
						<th align="center" width="120">
							催收时间
						</th>
						<!-- <th align="center" width="90">
							催收到的金额
						</th> -->
						<th align="center" width="80">
							催收组
						</th>
						<th align="center" width="80">
							催收员
						</th>
						<%--<th align="center" width="170">
							公司名称
						</th>--%>
						<th align="center" width="60">
							催收建议
						</th>
						<th align="center" width="200">
							风控标签
						</th>
						<!-- <th align="center" width="120">
							催收内容
						</th> -->
						<!-- <th align="center" width="120">
							创建时间
						</th> -->
						<%--<th align="center" width="120">
							更新时间
						</th>--%>
						<th align="center" width="180">
							催收内容
						</th>
					</tr>
				</thead>
				<tbody>
				
					<c:forEach var="record" items="${pm.items }" varStatus="status">
						<tr target="recordId" rel="${record.id }">
							<td>
								${status.count}
							</td>
							<td>
								${record.orderId}
							</td>
							<td>
								${record.collectionId}
							</td>
							<%--<td>
								${record.userId}
							</td>--%>
							<%--<td >
								<c:if test="${record.contactType == '1'}">紧急联系人</c:if>
								<c:if test="${record.contactType == '2'}">通讯录联系人</c:if>
							</td>
							<td>
								${record.relation }
							</td>--%>
							<td>
								${record.contactName }
							</td>
							<td>
								${record.contactPhone }
							</td>
							<%-- <td>
								<c:if test="${record.stressLevel == '1'}">一级施压</c:if>
								<c:if test="${record.stressLevel == '2'}">二级施压</c:if>
								<c:if test="${record.stressLevel == '3'}">三级施压</c:if>
							</td> --%>
							<td>
								<c:if test="${record.orderState == '0'}">待催收</c:if>
								<c:if test="${record.orderState == '1'}">催收中</c:if>
								<c:if test="${record.orderState == '2'}">承诺还款</c:if>
								<c:if test="${record.orderState == '3'}">委外中</c:if>
								<c:if test="${record.orderState == '4'}">催收成功</c:if>
								<%-- <c:if test="${record.orderState == '5'}">催收成功</c:if> --%>
								
							</td>
							<%--<td>
								<c:if test="${record.collectionType == '1'}">电话催收</c:if>
								<c:if test="${record.collectionType == '2'}">短信催收</c:if>
							</td>--%>
							<td>
								<fmt:formatDate value="${record.collectionDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<%-- <td>
								${record.collectionAmount }
							</td> --%>
							<td>
								<c:if test="${record.collectionGroup == '1'}">超级管理员</c:if>
								<c:if test="${record.collectionGroup == '3'}">S1组</c:if>
								<c:if test="${record.collectionGroup == '4'}">S2组</c:if>
								<c:if test="${record.collectionGroup == '5'}">M1-M2组</c:if>
								<c:if test="${record.collectionGroup == '6'}">M2-M3组</c:if>
								<c:if test="${record.collectionGroup == '7'}">M3+</c:if>
							</td>
							<td>
								${record.collectionPerson }
							</td>
                            <%--<td>
                                    ${record.companyTitle }
                            </td>--%>
                            <td>
                                <c:if test="${record.collectionAdvice == '1'}">通过</c:if>
                                <c:if test="${record.collectionAdvice == '2'}">拒绝</c:if>
                                <c:if test="${record.collectionAdvice == '3'}">无建议</c:if>
                            </td>
                            <td>
                                    ${record.fengKongLabel}
                                    <%--<c:if test="${record.fengKongLabel eq '0'}">无</c:if>
                                    <c:if test="${record.fengKongLabel ne '0'}">有</c:if>--%>
                            </td>
                                <%--<td>
                                    <fmt:formatDate value="${record.updateDate }"	pattern="yyyy-MM-dd HH:mm:ss" />
                                </td>--%>
							<td>
								${record.content }
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm}"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>
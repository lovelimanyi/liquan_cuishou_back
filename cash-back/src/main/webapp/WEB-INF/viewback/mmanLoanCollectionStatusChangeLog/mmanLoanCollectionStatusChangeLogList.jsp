<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="collectionRecordStatusChangeLog/getMmanLoanCollectionStatusChangeLog?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						创 建 时 间: 
						<input type="text" id="beginTime" name="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						至
						<input type="text" id="endTime" name="endTime"  value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
					</td>
					<td>
						操作员:
						<input type="text" name="operatorName" id="operatorName"
							value="${params.operatorName }" />
					</td>
					<td>
						当前催收员:
						<input type="text" name="collectionUser" id="collectionUser"
							value="${params.collectionUser }" />
					</td>
					<td>
						借款编号:
						<input type="text" name="loanCollectionOrderId" id="loanCollectionOrderId"
							value="${params.loanCollectionOrderId }" />
					</td>
					<td>
						操作类型:
						<select name="type" id="type" >
							<option value="0" <c:if test="${'0' eq params.type}">selected = "selected"</c:if>>全部</option>
							<option value="1" <c:if test="${'1' eq params.type}">selected = "selected"</c:if>>入催</option>
							<option value="2" <c:if test="${'2' eq params.type}">selected = "selected"</c:if>>逾期等级转换</option>
							<option value="3" <c:if test="${'3' eq params.type}">selected = "selected"</c:if>>转单</option>
							<option value="4" <c:if test="${'4' eq params.type}">selected = "selected"</c:if>>委外</option>
							<option value="5" <c:if test="${'5' eq params.type}">selected = "selected"</c:if>>催收完成</option>
							
							<%-- <option value="6" <c:if test="${'6' eq params.type}">selected = "selected"</c:if>>取消委外</option>
							<option value="7" <c:if test="${'7' eq params.type}">selected = "selected"</c:if>>委外成功</option>
							<option value="8" <c:if test="${'8' eq params.type}">selected = "selected"</c:if>>月初分组</option>
							<option value="9" <c:if test="${'9' eq params.type}">selected = "selected"</c:if>>回收</option>
							<option value="100" <c:if test="${'100' eq params.type}">selected = "selected"</c:if>>催收完成</option> --%>
						</select>
					</td>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${not empty params.CompanyPermissionsList}">
							<td>催 收 公 司:
								<select name="companyId" id="companyId">
									<option value="">全部</option>
									<c:forEach var="company" items="${ListMmanLoanCollectionCompany }">
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
							<td>催 收 公 司:
								<select name="companyId" id="companyId">
									<option value="">全部</option>
									<c:forEach var="company" items="${ListMmanLoanCollectionCompany }">
										<option value="${company.id }" <c:if test="${company.id eq params.companyId}">selected = "selected"</c:if>>
												${company.title}
										</option>
									</c:forEach>
								</select>
							</td>
						</c:otherwise>
					</c:choose>
					<td>催&nbsp;&nbsp;&nbsp;&nbsp;收&nbsp;&nbsp;&nbsp;组:
						<select name="collectionGroup" id="orderCollectionGroup">
							<option value="">全部</option>
							<c:forEach var="group" items="${dictMap }">
								<option value="${group.key }" <c:if test="${group.key eq params.collectionGroup}">selected = "selected"</c:if>>
										${group.value}
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
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include>
			<table class="table" style="width: 100%;" layoutH="138"
				nowrapTD="false">
				<thead>
					<tr>
						<th align="center" width="40">
							序号
						</th>
						<th align="center" width="50">
							借款编号
						</th>
						<th align="center" width="50">
							操作前状态
						</th>
						<th align="center" width="50">
							操作后状态
						</th>
						<th align="center" width="50">
							操作类型
						</th>
						<th align="center" width="100">
							催收公司
						</th>
						<th align="center" width="50">
							催收组
						</th>
						<th align="center" width="50">
							当前催收员
						</th>
						<th align="center" width="50">
							订单组
						</th>
						<th align="center" width="100">
							创建时间
						</th>
						<th align="center" width="50">
							操作人
						</th>
						<th align="center" width="200">
							操作备注
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="log" items="${pm.items }" varStatus="status">
						<tr target="recordId" rel="${record.id }">
							<td>
								${status.count}
							</td>
							<td>
								${log.loanCollectionOrderId}
							</td>
							<td>
								${orderStatusMap[log.beforeStatus] }
							</td>
							<td>
								${orderStatusMap[log.afterStatus] }
							</td>
							<td >
								${collectionStatusMoveTypeMap[log.type] }
							</td>
							 <td>
								${log.companyTitle }
							</td>
							<td>
								${dictMap[log.currentCollectionUserLevel] }
							</td>
							<td>
								${log.currentCollectionUserId }
							</td>
							<td>
								${dictMap[log.currentCollectionOrderLevel] }
							</td>
							<td>
								<fmt:formatDate value="${log.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/> 
							</td>
							<td>
								${log.operatorName }
							</td>
							<td>
								${log.remark }
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm}"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>

	<script type="text/javascript">
		function getExcel(obj){
            var href=$(obj).attr("href");
		    var beginTime = $("#beginTime").val();
		    if(beginTime != null && beginTime != ''){
                href += "&beginTime="+beginTime;
			}
		    var endTime = $("#endTime").val();
		    if(endTime != null && endTime != ''){
		        href += "&endTime="+endTime;
			}
            var operatorName = $("#operatorName").val();
		    if(operatorName != null && operatorName != ''){
                href += "&operatorName="+operatorName;
			}
            var collectionUser = $("#collectionUser").val();
            if(collectionUser != null && collectionUser != ''){
                href += "&collectionUser="+collectionUser;
            }
            var loanCollectionOrderId = $("#loanCollectionOrderId").val();
            if(loanCollectionOrderId != null && loanCollectionOrderId != ''){
                href += "&loanCollectionOrderId="+loanCollectionOrderId;
            }
			var type = $("#type").val();
            if(type != null && type != ''){
                href += "&type="+type;
            }
            var companyId = $("#companyId").val();
            if(companyId != null && companyId != ''){
                href += "&companyId="+companyId;
            }
            var collectionGroup = $("#orderCollectionGroup").val();
            if(collectionGroup != null && collectionGroup != ''){
                href += "&collectionGroup="+collectionGroup;
            }

            $(obj).attr("href",href);
		}
	</script>
</form>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="sysDict/getSysDictPage?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						类型:
						<input type="text" name="type" value="${params.type}"/>
					</td>
					<td>
						创建时间
						<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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
			<table class="table" style="width: 100%;" layoutH="112"
				nowrapTD="false">
				<thead>
					<tr>
						<th align="center" width="15">
							编号
						</th>
						<th align="center" width="50">
							数据值
						</th>
						<th align="center" width="50">
							标签名
						</th>
						<th align="center" width="20">
							类型
						</th>
						<th align="center" width="100">
							描述
						</th>
						<th align="center" width="60">
							排序（升序）
						</th>
						<th align="center" width="30">
							父ID
						</th>
						<th align="center" width="30">
							创建者
						</th>
						<th align="center" width="90">
							创建时间
						</th>
						<th align="center" width="30">
							修改者
						</th>
						<th align="center" width="90">
							修改时间
						</th>
						<th align="center" width="80">
							备注信息
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="sysDict" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${sysDict.id }">
							<td>
								${status.index+1}
							</td>
							<td>
								${sysDict.value}
							</td>
							<td>
								${sysDict.label}
							</td>
							<td>
								${sysDict.type}
							</td>
							<td>
								${sysDict.description}
							</td>
							<td>
								${sysDict.sort}
							</td>
							<td>
								${sysDict.parentId}
							</td>
							<td>
								${sysDict.createBy}
							</td>
							<td>
								<fmt:formatDate value="${sysDict.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${sysDict.updateBy}
							</td>
							<td>
								<fmt:formatDate value="${sysDict.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${sysDict.remarks}
							</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>
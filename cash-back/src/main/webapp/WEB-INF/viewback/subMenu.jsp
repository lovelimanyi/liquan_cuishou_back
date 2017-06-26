<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<c:if test="${not empty message}">
	<script type="text/javascript">
	alertMsg.error("${message}");
</script>
</c:if>
<c:if test="${ empty message}">
	<div class="accordion" fillSpace="sideBar">
			<c:forEach items="${subMenu}" var="item" varStatus="count">
						<div class="accordionHeader">
							<c:if test="${ count.index==0}">
								<h2 class="collapsable">
									<span>icon</span>${item.moduleName }
								</h2>
							</c:if>
							<c:if test="${ count.index>0}">
								<h2 class="">
									<span>icon</span>${item.moduleName }
								</h2>
							</c:if>
						</div>
						<div class="accordionContent">
							<ul class="tree treeFolder">
								<c:forEach items="${item.moduleList}" var="rightSubList">
									<c:if test="${fn:contains(rightSubList.moduleUrl,'?')}">
										<c:if test="${fn:contains(rightSubList.moduleUrl,'myId')}">
											<c:set var="startIndx"
												value="${fn:indexOf(rightSubList.moduleUrl,'myId')}"></c:set>
											<c:set var="urlLength"
												value="${fn:length(rightSubList.moduleUrl)}"></c:set>
											<li><a href="${rightSubList.moduleUrl}" target="navTab"
												rel="${fn:substring(rightSubList.moduleUrl,startIndx+5,urlLength)}">${rightSubList.moduleName}</a></li>
										</c:if>
										<c:if
											test="${!fn:contains(rightSubList.moduleUrl,'myId')}">
											<li><a
												href="${rightSubList.moduleUrl}&myId=${rightSubList.id}"
												target="navTab" rel="${rightSubList.id}">${rightSubList.moduleName}</a></li>
										</c:if>
									</c:if>
									<c:if test="${!fn:contains(rightSubList.moduleUrl,'?')}">
										<li><a
											href="${rightSubList.moduleUrl}?myId=${rightSubList.id}"
											target="navTab" rel="${rightSubList.id}">${rightSubList.moduleName}</a></li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
					</c:forEach>
	</div>

</c:if>

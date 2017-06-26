<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${empty message}">
<div class="panelBar">
	<ul class="toolBar">
		<c:forEach items="${rightSubList}" var="rightsub">
			<li>
				<a
					<c:if test="${!fn:contains(rightsub.moduleUrl,'?')}">
								href="${rightsub.moduleUrl}?myId=${rightsub.id}&parentId=${rightsub.moduleParentId}"  
							</c:if><c:if test="${fn:contains(rightsub.moduleUrl,'?')}">
								href="${rightsub.moduleUrl}&myId=${rightsub.id}&parentId=${rightsub.moduleParentId}"  
							</c:if>
							  ${rightsub.moduleStyle }>
					<span>${rightsub.moduleName}</span> </a>
			</li>
			<li class="line">
				line
			</li>
		</c:forEach>
	</ul>
</div>
</c:if>
<c:if test="${not empty message}">
	<script type="text/javascript">
	alertMsg.error("${message}");
	</script>
</c:if>
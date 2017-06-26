<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
</script>

<div class="pageContent">
<c:if test="${not empty message}">
<script type="text/javascript">
	alertMsg.error("${message}");
</script>
</c:if>
<c:if test="${empty message}">
		<form method="post" action="" 
				onsubmit="return validateCallback(this, navTabAjaxDone);" class="pageForm required-validate">
			<div class="pageFormContent" layoutH="56">
				${outGroupHtml}
			</div>
		</form>
</c:if>
</div>
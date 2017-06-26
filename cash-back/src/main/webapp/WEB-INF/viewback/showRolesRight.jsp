<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
function checkOnlyOne(){
	var checkedLength = $("input[name='roleIds']:checked").length;
	if(checkedLength>1){
		alertMsg.warn("用户授权失败，一个用户不能设置多个角色!");
		return false;
	}else{
		return true;
	}
	
}
</script>

<div class="pageContent">
			<c:if test="${not empty message}">
				<script type="text/javascript">
						alertMsg.error("${message}");
				</script>
			</c:if>
			<c:if test="${ empty message}">
				<form method="post" action="${params.url }" 
					onsubmit="return (checkOnlyOne()  && validateCallback(this, ${params.ajaxType }));" class="pageForm required-validate">
						<input type="hidden" name="parentId" value="${params.parentId}" />
						<input type="hidden" name="id" value="${params.id}" />
					<div class="pageFormContent" layoutH="56">
						${outGroupHtml}
					</div>
					<div class="formBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button  type="submit">保存</button></div></div></li>
							<li>
								<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
							</li>
						</ul>
					</div>
				</form>
		</c:if>
</div>

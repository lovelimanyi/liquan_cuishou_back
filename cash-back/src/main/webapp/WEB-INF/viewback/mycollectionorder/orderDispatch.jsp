<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert user</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" enctype="multipart/form-data"
				action="collectionOrder/doDispatch"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="id" id="id" value="${params.ids}">
				<input type="hidden" name="groupStatus" id="groupStatus" value="${params.groupStatus}">
				<div class="pageFormContent" layoutH="50" style="overflow: auto;">
					<dl>
						<dt style="width: 80px;">
							<label>
								公司名称:
							</label>
						</dt>
						<dd>
							<select name="outsideCompanyId" id="company" onchange="linkage();" class="required">
									<c:forEach var="company" items="${companyList}" varStatus="status">
										<option value="${company.id}" <c:if test="${params.companyId eq company.id}">selected="selected"</c:if>>${company.title}</option>
									</c:forEach>
							</select>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								催收组:
							</label>
						</dt>
						<dd>
							<select name="group" id="group" onchange="linkage();" class="required">
								<c:forEach items="${group}" var="group">
								  	<option value="${group.value}">${group.label}</option>
							    </c:forEach>   
						</select>
						</dd>
					</dl>
					<div class="divider"></div>
					
					<dl>
						<dt style="width: 80px;">
							<label>
								催收员:
							</label>
						</dt>
						<dd>
							<select name="currentCollectionUserId" id="currentCollectionUserId" class="required">
							</select>
						</dd>
					</dl>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										保存
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</body>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			alertMsg.error("${message}");
		</script>
	</c:if>
</html>
<script type="text/javascript">
$(function(){
	linkage();
});

  function linkage(){
	 var companyId=$("#company option:selected").val();
	 var group = $("#group option:selected").val();
	 var currUserId='${currUserId}';
     if(!companyId || !group) {  //根据公司和组查询催收员
    	 return; //alertMsg.error();
     }
     var url='<%=path%>/back/collectionOrder/findPerson?companyId='+companyId+'&groupId='+group+'&currUserId='+currUserId;
     
     $.getJSON(url, function(data) {
    	 if(data){
 			$("#currentCollectionUserId").empty();
 			$.each(data,function(n,person){
 				$("#currentCollectionUserId").append("<option value='"+person.uuid+"'>"+person.userName+"</option>");
 			});
 		}else{
 			   $("#currentCollectionUserId").empty();
 			  $("#currentCollectionUserId").append("<option value=''>没有催收员</option>");
 		}  
 	});
     
  }
</script>
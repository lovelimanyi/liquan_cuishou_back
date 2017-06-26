<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = path+"/common/back";
%>

<html>
	<head>
		<title>Insert title here</title>
		<script type="text/javascript">
	$(function() {
		//重置P元素的高度
		$("p[id^='p_id_']").each(
				function() {
					var itemId = $(this).attr("id").replace("p_id_", "");
					var inputField = $("[name='list[" + itemId + "']");
					$(this).height(inputField.height());

					if (inputField.is("textarea")
							&& parseInt(inputField.attr("cols")) > 100) {
						//cols大于100以后会换行，所以高度+30
						$(this).height($(this).height() + 30);
					}
				});
	});
</script>
	</head>
	<body>
		<form method="post" action="cofigParams/enterCfg/${params.sysType }?myId=${params.myId}"
			onsubmit="return validateCallback(this, dialogAjaxDone);"
			class="pageForm required-validate">

			<div class="pageFormContent" layoutH="55">
				<c:forEach items="${ cfgList}" var="cfg" varStatus="count">
					<input type="hidden" name="list[${count.index }].id" value="${cfg.id }"/>
					<c:if
						test="${not empty cfg.remark and cfg.inputType == 'textarea'}">
						<div style="margin-left: 10%;">
							<div style="color: red;">
								${cfg.remark }
							</div>
						</div>
					</c:if>

					<p id="p_id_${cfg.id }"  class="nowrap">
						<label style="width: 145px;">
							${cfg.sysName }：
						</label>
						<c:choose>
							<c:when test="${cfg.inputType == 'textarea' }">
								<textarea ${cfg.limitCode }  name="list[${count.index }].sysValueBig">${cfg.sysValueBig }</textarea>
							</c:when>
							<c:when test="${cfg.inputType == 'password' }">
								<input type="password" ${cfg.limitCode }  name="list[${count.index }].sysValue"
									value="${cfg.sysValue }" />
								<span
									style="float: left; width: 540px; padding: 0 5px; line-height: 21px;">${cfg.remark
									}</span>
							</c:when>
							<c:when test="${cfg.inputType == 'image' }">
								<input type="file"    id="input${cfg.id }"		value="${cfg.sysValue }" />
							    <div id="fileQueue_${cfg.id }" style="width: 740px auto;">
								      <ul id="fileUL_${cfg.id }" style="list-style: none;">
									    <c:if test="${not empty  cfg.sysValue }">
										    <li id="li_${cfg.id }">
											    <a  href="<%=path%>${cfg.sysValue}" target="blank">
												    <img   src="<%=path%>${cfg.sysValue}" width="50px" height="50px"/>
												    <input type='hidden' name='list[${count.index }].sysValue' type='text' value="${cfg.sysValue}" />
											    </a>
										    </li>
									    </c:if>
								      </ul>
							    </div>
							    	<script type="text/javascript">
							$(document).ready(function() { 
									var id = "${cfg.id }";
									    $("#input"+id).uploadify({  
									        'uploader'       : '<%=basePath %>/uploadify/scripts/uploadify.swf',  
									        'script'         : 'upload/uploadFiles;jsessionid=<%=session.getId()%>',  
									        'cancelImg'      : '<%=basePath %>/uploadify/cancel.png',  
									        'buttonImg'      : '<%=basePath %>/uploadify/buttonImgPic.png',  
									        'folder'         : '/jxdBlog/photos',  
									        'queueID'        : 'fileQueue_'+id,  
									        'auto'           : true,  
									        'multi'          : true,  
									        'wmode'          : 'transparent',  
									        'simUploadLimit' : 999,  
									        'fileExt'        : '*.png;*.gif;*.jpg;*.jpeg',  
									        'fileDesc'       : '*.png,*.gif,*.jpg,*.jpeg',  
									        'onComplete'  :function(event,queueId,fileObj,response,data){
									        	var retJson = eval(response)[0];
									        	$("#li_"+${cfg.id } ).remove();
									        	$("#fileUL_${cfg.id }").append("<li  id='li_"+${cfg.id }+"'> <img src='<%=path %>"+retJson.filepath+"' style='width: 50px;height: 50px;' ></img>"
									        	+"<input type='hidden' name='list[${count.index }].sysValue' type='text' value='"+retJson.filepath+"' ></input>"
									        	+"</li>");
									        	
									        }  
									    }); 
							});
							</script>
							</c:when>
							<c:otherwise>
								<input type="text" ${cfg.limitCode }  name="list[${count.index }].sysValue"
									value="${cfg.sysValue }" />
								<span
									style="float: left; width: 540px; padding: 0 5px; line-height: 21px;">${cfg.remark
									}</span>
							</c:otherwise>
						</c:choose>
					</p>
					<div class="divider"></div>
				</c:forEach>
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
				</ul>
			</div>
		</form>
	</body>
</html>

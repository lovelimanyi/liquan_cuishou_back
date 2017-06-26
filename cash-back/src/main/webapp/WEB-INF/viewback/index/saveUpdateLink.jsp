<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/common/back";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>修改/添加内容</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" method="post" enctype="multipart/form-data"
			action="indexManage/saveUpdateLink?parentId=${params.parentId}" onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">

				<div class="pageFormContent" layoutH="56">
					<p>
						<label>
							名称：
						</label>
						<input name="linkName" type="text" class="required" maxlengt"20"/>
					</p>
					<div class="divider"></div>
					<p>
						<label>
							页面链接：
						</label>
						<input type="text" name="linkUrl" style="width: 50%" class="isUrl"/>
					</p>
					<div class="divider"></div>
					<p class="nowrap">
						<label>
							简介内容：
						</label>
						<textarea rows="10" cols="55" name="content" maxlength="200"></textarea>
					</p>
					<div class="divider"></div>
					<p>
<!--						<input type="file" name="uploadify" id="uploadify" class='required' />-->
						<input type="file" name="uploadify" id="uploadify" />
						<span id="result" style="font-size: 13px; color: red"></span>
						<div class="divider"></div>
						<div id="fileQueue" style="width: 740px auto; border: 1px solid #99BBE8;">
							<ul id="fileUL">
							</ul>
							<input name='imgUrl' type='hidden'  ></input>
						</div>
					</p>
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
<script type="text/javascript">  
   $(document).ready(function() { 
       $("#uploadify").uploadify({  
           'uploader'       : '<%=basePath%>/uploadify/scripts/uploadify.swf',  
           'script'         : 'upload/uploadFiles;jsessionid=<%=session.getId()%>',  
           'cancelImg'      : '<%=basePath%>/uploadify/cancel.png',  
           'buttonImg'      : '<%=basePath%>/uploadify/buttonImg.png',
			'folder' : '/jxdBlog/photos',
			'queueID' : 'fileQueue',
			'auto' : true,
			'multi' : true,
			'wmode' : 'transparent',
			'simUploadLimit' : 20,
			'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.bmp',
			'fileDesc' : '请上传合法文件(*.jpg;*.jpeg;*.gif;*.png)',
			'sizeLimit' : 20000000,
			'onComplete' : function(event, queueId, fileObj, response, data) {
			var retJson = eval(response)[0];
			$("input[name='imgUrl']").val(""+retJson.filepath +"");
			$('#fileUL') .append( "<li id='"
									+ queueId + "'>&nbsp;&nbsp;&nbsp;&nbsp;"
									+"<image style=\"width:120px;height:120px;\" src='<%=path%>"+retJson.filepath+"'/>"
									+"&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javaScript:delet('" + queueId + "')\" >删除</a></li>");
				$("#uploadify").removeClass("required");
			}
		});

	});

	function delet(attachId) {
		$("#" + attachId).remove();
		$("#uploadify").addClass("required");
		$.ajax({
			type : "post",
			url : "upload/removeImg?imgUrl="+$("input[name='imgUrl']").val(),
			success : function(ret) {
			}
		});
		$("input[name='imgUrl']").val('');
	}
</script>
</html>

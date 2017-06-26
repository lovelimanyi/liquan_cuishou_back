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
		<script type="text/javascript">
                $("#userPhone").blur(function () {
                    var userPhone = $("#userPhone").val();
                    if(userPhone != null && userPhone != ""){
                        $("#loanEndTime").addClass("required");
                    }else{
                        $("#loanEndTime").removeClass("required");
                        $("#userPhone").removeClass("required");
                    }
                });

                $("#loanEndTime").blur(function () {
                    var loanEndTime = $("#loanEndTime").val();
                    if(loanEndTime != null && loanEndTime != ""){
                        $("#userPhone").addClass("required");
                    }else {
                        $("#userPhone").removeClass("required");
                        $("#loanEndTime").removeClass("required");
                    }
                });
                
                /*$("#frm").submit(function () {
                    var userPhone = $("#userPhone").val();
                    var loanEndTime = $("#loanEndTime").val();
                    if(!((loanEndTime != null || loanEndTime != "")) && ((userPhone == null || userPhone == ""))){
                        alert("借款人手机号和应还时间要么都填要么都不填！");
                        return false;
                    }else{
                        return true;
                    }
                });*/

		</script>
	</head>
	<body>
		<div class="pageContent">
			<form id="frm" method="post" enctype="multipart/form-data" action="problemFeedback/saveProblemFeedback" onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<div class="pageFormContent" layoutH="65" style="overflow: auto;">
					<dl>
						<dt style="width: 80px;">
							<label>
								反馈类型:
							</label>
						</dt>
						<dd>
							<select name="type" >
								<option value="">全部</option>
								<c:forEach var="problemFeedBackType" items="${problemFeedBackType}">
									<option value="${problemFeedBackType.key}" <c:if test="${problemFeedBackType.key eq params.type}">selected="selected"</c:if> >${problemFeedBackType.value}</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label id="loanUserPhone">
								借款人手机:
							</label>
						</dt>
						<dd>
							<input name="loanUserPhone" id="userPhone" value="" class="phone" minlength="1" maxlength="30" type="text" alt="请输入借款人手机号" size="30" />
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt style="width: 80px;">
							<label>
								应还时间:
							</label>
						</dt>
						<dd>
							<input name="loanEndTime" id="loanEndTime" value="" class="dateISO textInput" minlength="1" type="text" alt="请输入应还时间"/>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt style="width: 80px;">
							<label>
								反馈内容:
							</label>
						</dt>
						<dd>
							<textarea name="content" style="width:600px;height:150px;" value="" minlength="1" class="required" type="text" placeholder="请输入反馈问题的具体描述，限255汉字（包括标点符号），超出部分不显示！" />
						</dd>
					</dl>
				</div>
                <div>
                    <dl>
                        <dd>
                            <span style="color: red">温馨提示：</span><span style="color: #00be00">借款人手机和应还时间如果一个有值则另一必填！</span>
                        </dd>
                    </dl>
                </div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" id="submit">
										添加
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
</html>
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
		<title>添加催收记录和催收建议</title>
	</head>
	<body>
		<div class="pageContent">

			<form id="frm" method="post" enctype="multipart/form-data" action="collectionOrder/addRecordAndAdvice" onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="id" id="id" value="${params.id }">
				<input type="hidden" name="fengkongIds" id="fengkongIds" />
				<input type="hidden" name="fkLabels" id="fkLabels" />
				<input type="hidden" name="type" id="type" value="${params.type }"/>
				<div class="pageFormContent" layoutH="50" style="overflow: auto;">
					<fieldset>
						<legend>催收记录</legend>
					<dl>
						<dt style="width: 80px;">
							<label>
								借款id:
							</label>
						</dt>
						<dd>
							<input type="text" name="loanId" value="${params.loanId }" readonly="readonly" >
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								借款人姓名:
							</label>
						</dt>
						<dd>
							<input type="text" name="loanUserName" value="${params.loanUserName }" readonly="readonly" >
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								借款金额:
							</label>
						</dt>
						<dd>
							<input type="text" name="loanMoney" value="${params.loanMoney }" readonly="readonly" >
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								滞纳金金额:
							</label>
						</dt>
						<dd>
							<input type="text" name="loanPenlty" value="${params.loanPenlty }" readonly="readonly" >
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								联系人电话:
							</label>
						</dt>
						<dd>
							<input type="text" name="contactPhone" minlength="6" maxlength="16" value="${params.infoVlue }" readonly="readonly" >
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								联系人名称:
							</label>
						</dt>
						<dd>
							<input type="text" name="contactName"  maxlength="30" value="${params.infoName }" readonly="readonly" >
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								联系人类型:
							</label>
						</dt>
						<dd>
							<select class="required" name="contactType">
								<option value="1">紧急联系人</option>
								<option value="2">通讯录联系人</option>	
							</select>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								联系人关系:
							</label>
						</dt>
						<dd>
							<select class="required" name="relation">
								<option value="父亲">父亲</option>
								<option value="母亲">母亲</option>	
								<option value="亲人">亲人</option>	
								<option value="朋友">朋友</option>	
								<option value="同事">同事</option>	
								<option value="其它">其它</option>	
							</select>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								承诺还款时间:
							</label>
						</dt>
						<dd>
							<input type="text" id="repaymentTime" name="repaymentTime" value="" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								催收类型:
							</label>
						</dt>
						<dd>
							<select class="required" name="collectionType">
								<option value="1">电话催收</option>
								<option  value="2">短信催收</option>
							</select>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;" >
							<label>
								跟进等级:
							</label>
						</dt>
						<dd>
							<select class="required" name="stressLevel">
								<c:forEach var="group" items="${dictlist }">
									<option value=${group.value }>
										${group.label}
									</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt style="width: 80px;">
							<label>
								催收内容:
							</label>
						</dt>
						<dd>
							<textarea  name="content" rows="5" cols="80" maxlength="100"></textarea>
						</dd>
					</dl>
					</fieldset>
					<fieldset>
						<legend>催收建议</legend>
						<p><span style="color:red;">重要提示：风控标签页所操作内容将作为客户诚信数据进入公司风控模型，会直接影响用户未来借贷行为，请慎重选择！</span></p>
						<p>无建议：客户当前无法评价，借贷时需要经过风控模型审核</p>
						<p>通过：客户信用良好，建议通过风控模型并考虑小幅降额</p>
						<p>拒绝：客户判断为欺诈类或无还款能力，建议进入黑名单</p>
						<dl>
							<dt style="width: 80px;">
								<label>
									催收建议:
								</label>
							</dt>
							<dd>
								<select name="status" id="collectionStatus" onchange="selectModelChange();">
									<!-- <option value="">无建议</option> -->
									<c:forEach var="dct" items="${statulist}" varStatus="status">
										<option value="${dct.value}">${dct.label}</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
						<div class="divider"></div>
						<dl >
							<dt style="width: 80px;">
								<label>
									催收建议备注:
								</label>
							</dt>
							<dd>
								<textarea onblur="checkLength();" name="collectionRemark" rows="5" cols="80" maxlength="500" id="collectionAdviceRemark"></textarea>
							</dd>
						</dl>
						<div class="divider"></div>
						<dl>
							<dt style="width: 80px;">
								<label>
									风控标签:
								</label>
							</dt>
							<dd>
								<table>
									<c:forEach items="${fengKongList}" var="item" varStatus="cou">
										<c:if test="${cou.count eq 1 || (cou.count) % 4 eq 1}">
											<c:out value="<tr>" escapeXml="false"></c:out>
										</c:if>
										<td><label><input type="checkbox" id="checkItem" name="${item.fkLabel}" value="${item.id}" />${item.fkLabel}</label>
										</td>
										<c:if test="${(cou.count) % 4 eq 0}">
											<c:out value="</tr>" escapeXml="false"></c:out>
										</c:if>
									</c:forEach>
								</table>
							</dd>
						</dl>
					</fieldset>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="send">
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
		<script type="text/javascript">

            $(function(){
                $("#send").bind("click",function(){
                    var ids="";
                    var labels="";
                    $("input[id='checkItem']:checked").each(function(){
                        ids += $(this).val() + ",";
                        labels +=$(this).attr("name") + ",";
                    });
                    ids = ids.substring(0,ids.length-1);
                    labels = labels.substring(0,labels.length-1);
                    $("#fengkongIds").val(ids);
                    $("#fkLabels").val(labels);
                    alertMsg.confirm("您确认要保存吗?",{okCall:function () {
                        $("#frm").submit();
                    }})
                });
            });

			function selectModelChange() {
                var collectionRemark = $("#collectionStatus option:checked").val();
                if(collectionRemark == 2){
                    $("#collectionAdviceRemark").addClass("required");
                    $("#collectionAdviceRemark").attr("placeholder","请填写不少于15字的催收建议描述！");
                }else {
                    $("#collectionAdviceRemark").removeClass("required");
                    $("#collectionAdviceRemark").removeAttr("placeholder","请填写不少于15字的催收建议描述！");
				}
            }

			function checkLength() {
			    if($("#collectionAdviceRemark").hasClass("required")){
                    var content = $("#collectionAdviceRemark").val();
                    var len = content.replace(/[^\x00-\xff]/g, "aa").length;  // //读取转换得到长度，中文转换成2个长度，英文空格忽视算1个长度
                    if(len < 15){
                        alertMsg.error("请填写不少于15字催收建议描述！");
                    }
                }
            }

		</script>
	</body>
</html>
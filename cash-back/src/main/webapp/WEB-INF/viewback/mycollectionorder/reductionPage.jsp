<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=7"/>
	<title>订单减免</title>

</head>
<body>

<div class="pageContent">

	<form id="frm" method="post" enctype="multipart/form-data" action="collectionOrder/doReduction" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="parentId" value="${params.parentId}"/>
		<input type="hidden" name="id" id="id" value="${params.id }">
		<input type="hidden" name="loanId" id="loanId" value="${params.loanId}">
		<input type="hidden" name="orderType" id="orderType" value="${params.type}">
		<div class="pageFormContent" layoutH="50" style="overflow: auto;">
			<fieldset name="message" style="padding-bottom: 30px;height: 310px">
				<!-- 借款信息 -->
				<legend>欠款信息</legend>
				<table class="userTable">
					<tr>
						<dt style="width: 80px;">
							<label>
								欠款金额:
							</label>
						</dt>
						<dd>
							<label>
								${params.receivableMoney}
							</label>
						</dd>
						<dt style="width: 80px;">
							<label>
								欠款本金:
							</label>
						</dt>
						<dd>
							<label>
								${params.loanMoney}
							</label>
						</dd>
						<dt style="width: 80px;">
							<label>
								欠款滞纳金:
							</label>
						</dt>
						<dd>
							<label>
								${params.loanPenalty}
							</label>
						</dd>
						<c:if test="${params.type eq 1}">
							<dt style="width: 80px;">
								<label>
									欠款利息:
								</label>
							</dt>
							<dd>
								<label>
										${params.accrual}
								</label>
							</dd>
						</c:if>
						<dt style="width: 80px;">
							<label>
								已还金额:
							</label>
						</dt>
						<dd>
							<label>
								${params.realMoney}
							</label>
						</dd>

						<dt style="width: 80px;">
							<label>
								可减免金额:
							</label>
						</dt>
						<dd>
							<label>
								${params.deductibleMoney}
							</label>
						</dd>
						</td>
					</tr>

				</table>
				<div class="divider"></div>
				<div class="pageFormContents" style="height: 220px;">
					<dl>
						<dt style="width: 400px;color: red">
							&nbsp;&nbsp;注意：本息还清后，方可减免滞纳金，且只能减免滞纳金
						</dt>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt style="width: 80px;">
							<label>
								减免金额:
							</label>
						</dt>
						<dd>
							<input type="text" id="reductionMoney" name="reductionMoney" value="${params.deductibleMoney}" readonly="readonly" class="required">
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt style="width: 80px;">
							<label>
								减免备注:
							</label>
						</dt>
						<dd>
							<textarea id="reductionRemark" name="reductionRemark" rows="5" cols="80" maxlength="100" class="required accept "></textarea>
						</dd>
					</dl>
				</div>
			</fieldset>
		</div>

		<div class="formBar">
			<ul>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" id="ok" onclick="doSubmit();">
							确认
						</button>
					</div>
				</div>
			</ul>
		</div>
	</form>
</div>

</body>
</html>
<script type="text/javascript">

    function doSubmit() {

        var deductibleMoney = ${params.deductibleMoney};
        if (deductibleMoney == 0) {
            if (${params.realMoney}==${params.receivableMoney}) {
                alertMsg.warn("人家钱已经还完了！");
                return false;
            } else {
                alertMsg.warn("不符合减免条件");
                return false;
            }
        } else {
            alertMsg.confirm("是否确认提交？", {
                okCall: function () {
                    $("#frm").submit();
                }
            });
        }
    }
</script>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>停催订单</title>
	</head>
	<body>
		<div class="pageContent" id="dialog">
		<form id="frm" method="post" enctype="multipart/form-data" action="mmanLoanCollectionOrder/stop-collection" onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="id" value="${params.id }">
				<input type="hidden" name="orderId" value="${info.orderId }">
				<%--<input type="hidden" name="userId" value="${info.userId }">--%>
				<div class="pageFormContent" layoutH="56">
					<dl>
						<dt style="width: 80px;">
							<label>
								借款id:
							</label>
						</dt>
						<dd>
							<input name="loanId" value="${info.loanId}" maxlength="30" type="text"  size="30" class="readonly"/>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								借款人:
							</label>
						</dt>
						<dd>
							<input name="loanUserName" value="${info.loanUserName}" maxlength="30" type="text"  size="30" class="readonly"/>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								手机号:
							</label>
						</dt>
						<dd>
							<input name="userPhone" value="${info.loanUserPhone}" maxlength="30" type="text"  size="30" class="readonly"/>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								借款金额:
							</label>
						</dt>
						<dd>
							<input name="loanMoney" value="${info.loanMoney}" maxlength="30" type="text"  size="30" class="readonly"/>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								借款时间:
							</label>
						</dt>
						<dd>
							<input name="loanStartTime" value="${info.loanStartTime}" maxlength="30" type="text"  size="30" class="readonly date-picker"/>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								应还时间:
							</label>
						</dt>
						<dd>
							<input name="loanEndTime" value="${info.loanEndTime}" maxlength="30" type="text"  size="30" class="readonly date-picker"/>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								逾期天数:
							</label>
						</dt>
						<dd>
							<input name="overDuedays" value="${info.overDuedays} 天" maxlength="30" type="text"  size="30" class="readonly"/>
						</dd>
					</dl>
					<dl>
						<dt style="width: 80px;">
							<label>
								滞纳金:
							</label>
						</dt>
						<dd>
							<input name="loanPenalty" value="${info.loanPenalty}" maxlength="30" type="text"  size="30" class="readonly"/>
						</dd>
					</dl>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="send" onclick="doCheck();">
										停催
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
<script type="text/javascript">
	function doCheck() {
        alertMsg.confirm("您确认要停催该订单吗?", {
            okCall: function(){
                $("#frm").submit();
            }
        });
    }
</script>
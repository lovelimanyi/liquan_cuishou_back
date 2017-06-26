<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:if test="${not empty page}">

<!--targetTypeParam 修复对话框中分页正常使用。  updated by LTQ, date:2014.11.14  -->
<c:if test="${empty targetTypeParam}">
<c:set var="targetTypeParam" value="navTab"></c:set>
</c:if>

<!-- 当前第几页 -->
<input type="hidden" name="pageNum" value="${page.currentPage }" />

	<div class="panelBar">
				<div class="pages">
					<span>共 ——<!--${page.totalResultSize } --> 条，共 —— <!-- ${page.totalPageNum } --> 页，每页</span>
				
	<select onchange="changeNumPerPage(this.value)" name="numPerPage">
		<option value="10">10</option>
		<option value="20">20</option>
		<option value="50">50</option>
		<option value="100">100</option>
		<option value="200">200</option>
		<option value="5000">5000</option>
	</select><span>条</span>
			</div>
				<div class="pagination" targetType="${targetTypeParam }"
				<c:if test="${not empty pagedRel}">
				 rel="${pagedRel }"
				 </c:if>
				 
					totalCount="${page.totalResultSize }" numPerPage="${page.pageSize }"
					pageNumShown="10" currentPage="${page.currentPage }"></div>
			</div>
	<script type="text/javascript">
	var func_pageBreak = navTabPageBreak;
	<c:if test="${ 'dialog' eq targetTypeParam}">
	func_pageBreak = dialogPageBreak ;
	</c:if>
	var rel = null;
	if('${pagedRel }'){
		rel = '${pagedRel }';
	}
	function changeNumPerPage(val){ //改变每页数
		$.cookie("numPerPage", val+"");
		func_pageBreak({numPerPage:val},rel);
	}
	if($.cookie("numPerPage")==null){
		var numPerPage = "${pm.pageSize }"; //每页显示条数
	}else{
		var numPerPage = $.cookie("numPerPage");
	}
	
	$("select[name='numPerPage']").val(numPerPage); //选中
	</script>
</c:if>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert user</title>
    <script type="text/javascript">

        $("#showCompanyIp").change(function(){
            var value = $("#showCompanyIp").val();
            if(value != ''){
                $("#ips").html(""); // 下一次请求之前先清空上一次请求的数据
                $.get("/back/company/getIps",{id:value},function(data){
                    ips = data.split(",");
                    $.each(ips,function(index,ip){
                        results = ip.split(".");
                        /*$("#ips").append("<input size='2' maxlength='3' min='0' max='255'>");*/


                       /*$("#ips").append("<td align='center'>");*/
                        $.each(results,function(index,ip){
                            console.log(ip);
                            $("#ips").append("<input size='2' maxlength='3' min='0' max='255'>");
                            if(index < 3){
                                $("#ips").append(".");
                            }
                            $("#ips").append("</input>");

                            $("#ips").append("</td>");
                        })
                    });

                })
            }else {
                $("#ips").html();
            }
        })


    </script>
</head>
<body>
<div class="pageContent">
    <form id="frm" method="post" enctype="multipart/form-data" action="problemFeedback/saveProblemFeedback" onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <input type="hidden" name="parentId" value="${params.parentId}"/>
        <div class="pageFormContent" layoutH="65" style="overflow: auto;">
            <dl>
                <dt style="width: 80px;">
                    <label>
                        公司名称:
                    </label>
                </dt>
                <dd>
                    <select name="companyTitle" id="showCompanyIp">
                        <option value="">全部</option>
                        <c:forEach var="company" items="${companys}">
                            <option value="${company.id}"
                                    <c:if test="${company.id eq params.id}">selected="selected"</c:if>
                            >${company.title}</option>
                        </c:forEach>
                    </select>
                </dd>
            </dl>
            <dl>
                <dt style="width: 80px;">
                <%--<p>ips: <span id="ips"></span></p>--%>
                <%--<label id="loanUserPhone">
                    借款人手机:
                </label>--%>
                </dt>
                <dd>
                    <p>ip地址: <span id="ips"></span></p>
                    <%--<input name="loanUserPhone" id="userPhone" value="" class="phone" minlength="1" maxlength="30" type="text" alt="请输入借款人手机号" size="30"/>--%>
                </dd>
            </dl>
            <div class="divider"></div>
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
        </div>
    </form>
</div>
</body>
</html>
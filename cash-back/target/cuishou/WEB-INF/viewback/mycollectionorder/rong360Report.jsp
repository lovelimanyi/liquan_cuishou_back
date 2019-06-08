
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<style type="">
    body,h1,h2,h3,h4,h5,h6,hr,p,blockquote,dl,dt,dd,ul,ol,li,pre,form,fieldset,legend,button,input,textarea,th,td{margin:0;padding:0;}
    body,button,input,select,textarea{font-family: "微软雅黑";,"MicroSoft YaHei","Arial Narrow","HELVETICA"; font-size:14px; padding:0; margin:0; color:#666;}
    table{border-collapse:collapse;border-spacing:0;}
    ul,li{ margin:0px; padding:0px; list-style:none; }
    img{border:none; display:block;}
    h1,h2,h3,h4,h5,h6,p{ margin:0; padding:0;}
    .clear{ clear:both; margin:0; padding:0;}
    a{text-decoration:none; color:#666;}
    html{-webkit-text-size-adjust:none;} /* 2016-7-13 修改css */
    input,textarea{border:none; outline:medium;}
    textarea{overflow-y:auto;}
    .clear{clear:both; overflow:hidden;height:1px;margin-top:-1px;font-size:0;}

    /* 用户资信报告 */
    .credit-report{
        width: 800px;
        background-color: #fff;
        margin: 0 auto;
        padding: 38px 66px;
    }
    .credit-report i{
        font-style: normal;
    }
    .credit-header{
        padding-bottom: 6px;
        border-bottom: 2px solid #434343;
    }
    .credit-title{
        text-align: center;
        padding: 26px 0 20px;
    }
    .credit-title h1{
        font-size: 18px;
        color: #1d1d1d;
        line-height: 18px;
        margin-bottom: 4px;
    }
    .credit-title p{
        font-size: 12px;
        color: #5c5c5c;
    }

    .info-list{

    }
    .info-list dl{
        overflow: hidden;
    }
    .info-list dl dd{
        width: 33.33%;
        float: left;
        font-size: 12px;
        color: #4e4e4e;
        margin-bottom: 10px;
    }
    .table-box{
        margin-top: 12px;
    }
    .table-title{
        height: 26px;
        background-color: #f8bd54;
        text-align: center;
        font-size: 14px;
        color: #666;
        line-height: 26px;
        font-weight: bold;
    }
    .table-box p{
        font-size: 14px;
        color: #464646;
        margin: 7px 0 2px;
    }
    .table-box table{
        width: 100%;
        text-align: left;
        table-layout: fixed;
    }
    .table-box-ts table{
        table-layout: auto;
    }
    .table-box table thead tr{
        height: 21px;
        border-top: 2px solid #666;
        border-bottom: 1px solid #888;
        line-height: 21px;
        font-size: 12px;
    }
    .table-box table tbody tr td{
        height: 19px;
        border-bottom: 1px solid #888;
        line-height: 19px;
        font-size: 12px;
        overflow: hidden;
        word-wrap:break-word;
        word-break:break-all;
    }
</style>
<div class="pageContent">
    <table class="" style="width: 100%;" layoutH="10" nowrapTD="false">
        <tbody>
        <tr target="id" rel="${order.id }" onclick="sel(this);">
            <td>
                <div class="pageContent">
                    <div class="wrapper wrapper-content animated fadeInRight">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="credit-title">
                                    <h1>互联网用户资信报告</h1>
                                    <p>此报告内容是根据&nbsp;"${name }"&nbsp;授权，整理汇总互联网数据、交叉验证产生</p>
                                </div>
                                <div class="info-list clearfix">
                                    <dl>
                                        <dd><span>姓名：</span>${name }</dd>
                                        <dd><span>性别：</span>${gender }</dd>
                                        <dd><span>年龄：</span>${age }岁</dd>
                                    </dl>
                                    <dl>
                                        <dd><span>身份证号：</span>${idNumber}</dd>
                                        <dd><span>注册时间：</span>${basicInfo.reg_time}</dd>
                                    </dl>
                                </div>
                                <div class="table-box">
                                    <div class="table-title">用户信息</div>
                                    <table>
                                        <tr>
                                            <td>姓名</td>
                                            <td>${inputInfo.user_name}</td>
                                        </tr>
                                        <tr>
                                            <td>身份证</td>
                                            <td>${inputInfo.id_card}</td>
                                        </tr>
                                        <tr>
                                            <td>手机号</td>
                                            <td>${inputInfo.phone}</td>
                                        </tr>
                                        <tr>
                                            <td>紧急联系人一</td>
                                            <td>${inputInfo.emergency_name1}</td>
                                        </tr>
                                        <tr>
                                            <td>关系</td>
                                            <td>${inputInfo.emergency_relation1}</td>
                                        </tr>
                                        <tr>
                                            <td>紧急联系人一电话</td>
                                            <td>${inputInfo.emergency_phone1}</td>
                                        </tr>
                                        <tr>
                                            <td>紧急联系人二</td>
                                            <td>${inputInfo.emergency_name2}</td>
                                        </tr>
                                        <tr>
                                            <td>关系</td>
                                            <td>${inputInfo.emergency_relation2}</td>
                                        </tr>
                                        <tr>
                                            <td>紧急联系人二电话</td>
                                            <td>${inputInfo.emergency_phone2}</td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="table-box">
                                    <div class="table-title">用户基本信息</div>
                                    <table>
                                        <tr>
                                            <td>手机号</td>
                                            <td>${basicInfo.phone}</td>
                                            <td>号码归属地</td>
                                            <td>${basicInfo.phone_location}</td>
                                        </tr>
                                        <tr>
                                            <td>运营商编码</td>
                                            <td>${basicInfo.operator}</td>
                                            <td>运营商中文</td>
                                            <td>${basicInfo.operator_zh}</td>
                                        </tr>
                                        <tr>
                                            <td>平均月消费</td>
                                            <td>${basicInfo.ave_monthly_consumption}</td>
                                            <td>当前余额</td>
                                            <td>${basicInfo.current_balance}</td>
                                        </tr>
                                        <tr>
                                            <td>是否联系过第一联系人</td>
                                            <c:if test="${basicInfo.if_call_emergency1==1}">
                                                <td>是</td>
                                            </c:if>
                                            <c:if test="${basicInfo.if_call_emergency1==2}">
                                                <td>否</td>
                                            </c:if>
                                            <c:if test="${basicInfo.if_call_emergency1==3}">
                                                <td>无法验证</td>
                                            </c:if>
                                            <td>是否联系过第二联系人</td>
                                            <c:if test="${basicInfo.if_call_emergency2==1}">
                                                <td>是</td>
                                            </c:if>
                                            <c:if test="${basicInfo.if_call_emergency2==2}">
                                                <td>否</td>
                                            </c:if>
                                            <c:if test="${basicInfo.if_call_emergency2==3}">
                                                <td>无法验证</td>
                                            </c:if>
                                        </tr>

                                    </table>
                                </div>
                                <div class="table-box">
                                    <div class="table-title">通话记录</div>
                                    <p>通话记录</p>
                                    <table>
                                        <thead>
                                        <tr>
                                            <th>号码</th>
                                            <th>归属地</th>
                                            <th>互联网标识</th>
                                            <th>类别标签</th>
                                            <th>首次联系时间</th>
                                            <th>最后联系时间</th>
                                            <th>通话时长</th>
                                            <th>通话次数</th>
                                            <th>主叫时长</th>
                                            <th>主叫次数</th>
                                            <th>被叫时长</th>
                                            <th>被叫次数</th>
                                            <th>短信总数</th>
                                            <th>发送短信数</th>
                                            <th>接收短信数</th>
                                            <th>未识别状态短信数</th>
                                            <th>近一周联系次数</th>
                                            <th>近一个月联系次数</th>
                                            <th>近三个月联系次数</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${callLog}" var="callLog">
                                            <tr>
                                                <td>${callLog.phone }</td>
                                                <td>${callLog.phone_location }</td>
                                                <td>${callLog.phone_info }</td>
                                                <td>${callLog.phone_label }</td>
                                                <td>${callLog.first_contact_date }</td>
                                                <td>${callLog.last_contact_date }</td>
                                                <td>${callLog.talk_seconds }</td>
                                                <td>${callLog.talk_cnt }</td>
                                                <td>${callLog.call_seconds }</td>
                                                <td>${callLog.call_cnt }</td>
                                                <td>${callLog.called_seconds }</td>
                                                <td>${callLog.called_cnt }</td>
                                                <td>${callLog.msg_cnt }</td>
                                                <td>${callLog.send_cnt }</td>
                                                <td>${callLog.receive_cnt }</td>
                                                <td>${callLog.unknown_cnt }</td>
                                                <td>${callLog.contact_1w }</td>
                                                <td>${callLog.contact_1m }</td>
                                                <td>${callLog.contact_3m }</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

</div>
<script type="text/javascript">
    if("${message}"){
        alertMsg.error("${message}");
    }
</script>
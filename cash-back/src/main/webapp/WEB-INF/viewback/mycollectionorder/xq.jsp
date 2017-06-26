<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<div id="orderinfo">
	<div>
	  <table class="table" >
	 <thead>
	      <tr >
	        <th >订单详情</th>
	     </tr>
	    </thead>
	    <tbody>
	      <tr  >
	        <td>借款人姓名:李彦宏</td>
	        <td>借歀人手机：15888888888</td>
	        <td>身份证号：888888888888888888</td>
	        <td>银行卡号：9999999999999999999</td>
	        </tr>
	      <tr >
	        <td>联系人1姓名：周鸿祎</td>
	        <td>联系人1手机：通讯录管控</td>
	        <td>联系人2姓名：程维</td>
	        <td>联系人2手机：通讯录管控</td>
	        </tr>
	      <tr >
	        <td>身份证地址：上海市长宁区。。。</td>
	        <td>现居住地址：上海市青浦区。。。</td>
	        <td>借款人公司名称：滴滴打车</td>
	         <td>借款人公司地址：上海市普陀区。。。</td>
	       </tr>
	    </tbody>
	  </table>
</div>
</div>
<div id="daohanglan">
<!-- <ul id="myTab" class="nav nav-tabs">
	<li class="active"><a href="#gr" data-toggle="tab">个人信息</a></li>
	<li><a href="#ts" data-toggle="tab">催收记录报告</a></li>
	<li><a href="#jlx" data-toggle="tab">聚信立报告</a></li>
	<li><a href="#txl" data-toggle="tab">通讯录</a></li>
	<li><a href="#bq" data-toggle="tab">风控标签</a></li>
	<li><a href="#kk" data-toggle="tab">扣款</a></li>
	<li><a href="#jm" data-toggle="tab">减免</a></li>
	<li><a href="#rz" data-toggle="tab">流转日志</a></li>
	<li><a href="#zp" data-toggle="tab">转派</a></li>
</ul> -->
<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>
<div id="myTabContent" >
	<div >
	<p>基本信息</p>
		<table class="table">
			  <tbody>
			    <tr>
			      <td>性别：男</td>
			      <td>婚姻：未婚</td>
			       <td>学历：博士</td>
			      <td>现居时常：1年半</td>
			    </tr>
			 	<tr>
			      <td>欠款金额：1000.00</td>
			      <td>欠款本金：500.00</td>
			       <td>滞纳金： 20</td>
			      <td>已还金额：500.00</td>
			    </tr>
			    <tr>
			      <td>借款时间：2017 02-08  14:00:00</td>
			      <td>应还时间：2017 02-16  14:00:00</td>
			       <td>逾期天数：1</td>
			      <td>承诺还款时间：2017 02-18  14:00:00</td>
			    </tr>
			  </tbody>
		</table>
		<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>
		<p>图片信息</p>
		<table >
			  <tbody>
			    <tr>
			      <td ><img  height="400" width="400" src="http://images2015.cnblogs.com/blog/153475/201512/153475-20151222173139109-87271821.png"/></td>
			      <td ><img  height="400" width="400" src="http://images2015.cnblogs.com/blog/153475/201512/153475-20151222173139109-87271821.png"/></td>
			       <td ><img   height="400" width="400" src="http://images2015.cnblogs.com/blog/153475/201512/153475-20151222173139109-87271821.png"/></td>
			    </tr>
			  </tbody>
		</table>
	</div>
	<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>
	<div>
	<p>催收记录报告</p>
		<table >
		 	 <thead>
			    <tr>
			      <th>创建时间</th>
			      <th>催收组</th>
			      <th>订单组</th>
			      <th>催收员</th>
			      <th>查看</th>
			    </tr>
  			</thead>
			  <tbody>
			    <tr>
			      <td>2017-02-12 10:00:00</td>
			      <td>S2</td>
			      <td>s2</td>
			      <td>奶茶妹妹</td>
			      <td><button type="button" class="btn btn-info btn-xs">详情</button></td>
			    </tr>
			  </tbody>
		</table>
	</div>
	<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>
	<div>
		<p>聚信立报告</p>
				<div >
					  <table >
					    <caption ><h2>互联网用户资信报告</h2></caption>
					    <caption ><h4>此报告内容是根据王斌授权，整理汇总互联网数据 交叉验证产生</h4></caption>
					    <tbody>
					      <tr>
					        <td>报告编号：1111111111111111111111</td>
					        <td>报告生成时间：2023232233</td>
					        <td></td>
					      </tr>
					      <tr>
					        <td>姓名：王斌</td>
					        <td>性别：男</td>
					        <td>年龄：29岁</td>
					        </tr>
					      <tr>
					        <td>身份证号：32323232323233223</td>
					        <td></td>
					        <td></td>
					        </tr>
					    </tbody>
	 			 </table>
			</div>	
			<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>
			<p>用户黑名单信息</p>
			<table>
						  <thead>
						    <tr>
						      <th>检查项目</th>
						      <th>结果</th>
						    </tr>
						  </thead>
						  <tbody>
						    <tr>
						      <td>用户号码联系黑中介分数</td>
						      <td>82</td>
						    </tr>
						    <tr>
						      <td>直接联系人中黑名单人数</td>
						      <td>12</td>
						    </tr>
						    <tr>
						      <td>间接联系人中黑名单人数</td>
						      <td>12</td>
						    </tr>
						    <tr>
						      <td>直接联系人数</td>
						      <td>13</td>
						    </tr>
						    
						     <tr>
						      <td>引起间接黑名单人数</td>
						      <td>12</td>
						    </tr>
  							 <tr>
						      <td>直接联系人中引起间接黑名单数</td>
						      <td>1.0</td>
						    </tr>
						  </tbody>
			</table>
		
	</div>
	<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>
	<div >
	<p>联系人信息</p>
		<table >
		 	 <thead>
			    <tr>
			      <th>联系人姓名</th>
			      <th>联系人电话</th>
			      <th>是否紧急联系人</th>
			      <th>联系人类型</th>
			      <th>与本人关系</th>
			      <th>联系次数</th>
			      <th>主叫次数</th>
			       <th>被叫次数</th>
			        <th>归属地</th>
			    </tr>
  			</thead>
			  <tbody>
			    <tr>
			      <td>abc</td>
			      <td>S2</td>
			      <td>s2</td>
			      <td>奶茶妹妹</td>
			      <td>fsdfs</td>
			       <td>s2</td>
			      <td>奶茶妹妹</td>
			      <td>fsdfs</td>
			       <td>上海</td>
			    </tr>
			  </tbody>
		</table>
		<form>
			<p>催收类型：
				    <select class="form-control">
				      <option>电话催收</option>
				      <option>短信催收</option>
				    </select>
			</p>
			<p>
				跟进等级：
				    <select class="form-control">
				   	  <option>经济困难</option>
				      <option>有意代偿</option>
				      <option>朋友转告</option>
				       <option>继续跟进</option>
				      <option>失联查找</option>
				       <option>恶意拖欠</option>
				    </select>
			</p>
			<p>
				承诺还款时间：<input />
			</p>
			<p>
				备注：
					    <textarea  rows="3" ></textarea>
			</p>
			<button  type="button" >确认提交</button>
		</form>
	</div>
	<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>
	<p>催收建议</p>
	<div>
		<div style="height:50%;width:50%;margin:0px auto;">
			<p>
				   催收建议： <select class="form-control">
				   	  <option>无建议</option>
				      <option>拒绝</option>
				      <option>通过</option>
				    </select>
			</p>
			<p>
				   选择风控标签：
				    <input type="checkbox" name="optionsRadiosinline"  value="option1" >失联
				    <input type="checkbox" name="optionsRadiosinline"  value="option1" >难联
				   <input type="checkbox" name="optionsRadiosinline"  value="option1" >首通后屏蔽
				    <input type="checkbox" name="optionsRadiosinline"  value="option1" >首通前屏蔽
				    <input type="checkbox" name="optionsRadiosinline"  value="option1" >联系人不匹配
				    <input type="checkbox" name="optionsRadiosinline"  value="option1" >联系人重号
				    <input type="checkbox" name="optionsRadiosinline"  value="option1" >疑似黑中介办理
				    <input type="checkbox" name="optionsRadiosinline"  value="option1" >各平台欠款
				    <input type="checkbox" name="optionsRadiosinline"  value="option1" >各银行欠款
				    <input type="checkbox" name="optionsRadiosinline"  value="option1" >无赖不换
				    <input type="checkbox" name="optionsRadiosinline"  value="option1" >诚信度低
				    <input type="checkbox" name="optionsRadiosinline"  value="option1" >无偿还能力
			</p>
			<input  type="button"  value="确认提交"/>
		</div>
		
	</div>
	<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>
	<p>扣款</p>
	<div >
		<div>
			<p>
				   扣款金额： <input />
			</p>
			<button  type="button" >确认提交</button>
		</div>
	</div>
	<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>
	<p>减免</p>
	<div>
		<div style="height:50%;width:50%;margin:0px auto;">
			<p>
				   减免： <input />
			</p>
			<center><button  type="button" class="btn btn-link">确认提交</button></center>
		</div>
	</div>
	<div >
	<div >
		<form>
			<table>
				<tr>
					<td>创建时间:<input/>至</td>
					<td><input /></td>
				</tr>
				<tr>
					<td>借款人姓名： <input type="text" class="form-control"  placeholder="请输入借款人姓名"></td>
					<td>借款人电话： <input type="text" class="form-control"  placeholder="请输入借款人电话"></td>
				</tr>
				<tr>
					<td>借款编号： <input type="text" class="form-control"  placeholder="借款编号"></td>
					<td> 操作类型： <select class="form-control">
				   	  <option>全部</option>
				      <option>入催</option>
				      <option>逾期等级转换</option>
				      <option>转单</option>
				      <option>委外</option>
				      <option>催收完成</option>
				    </select></td>
				</tr>
			</table>
			<center><button  type="button" class="btn btn-link">查询</button></center>
		</form>
	</div>
	<table >
		 	 <thead>
			    <tr>
			      <th>创建时间</th>
			      <th>借款编号</th>
			      <th>借款人姓名</th>
			      <th>借款人电话</th>
			      <th>催收员姓名</th>
			      <th>操作类型</th>
			      <th>操作人</th>
			      <th>备注</th>
			    </tr>
  			</thead>
			  <tbody>
			    <tr>
			      <td>2017-02-07 20:00:00</td>
			      <td>88888888888888888</td>
			      <td>奶茶妹妹</td>
			      <td>15888888888</td>
			      <td>15888888888</td>
			       <td>催收完成</td>
			      <td>奶茶妹妹</td>
			      <td>你好</td>
			    </tr>
			  </tbody>
		</table>
	</div>
	<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>
	<p>转派</p>
	<div >
		<div style="height:50%;width:50%;margin:0px auto;" id="gongsi">
			 选择催收公司： <select class="form-control">
				   	  <option>aaa公司</option>
				      <option>不不不公司</option>
				      <option>通踩踩踩公司</option>
				    </select>
			<button type="button" class="btn btn-primary" onclick="showpeople();">下一步</button>
		</div>
		<div style="height:50%;width:50%;margin:0px auto;display:none;" id="renyuan">
			<p>
				<button type="button" class="btn btn-link" onclick="showgs();">返回</button>  选公司
			</p>
			 选择人员： <select class="form-control">
				   	  <option>啊啊啊</option>
				      <option>啊啊啊啊</option>
				      <option>啊啊啊啊</option>
				    </select>
			<button type="button" class="btn btn-primary">确认提交</button>
		</div>
	</div>
</div>
<script>
	/* $(function () {
		$('#myTab li:eq(0) a').tab('show');
	}); */
	
	function showpeople(){
		 $("#gongsi").hide();
		 $("#renyuan").show();
	};
	function showgs(){
		 $("#gongsi").show();
		 $("#renyuan").hide();
	};
</script>

</div>


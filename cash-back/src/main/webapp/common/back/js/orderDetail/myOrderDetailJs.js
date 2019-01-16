$(function () {
    getRecordLists();
    $("#promiseRepayTime").hide();
    $("#communicate").hide();
    $("#collectionWithMsg").hide();
});

// 点击图片放大效果
$(PostbirdImgGlass.init({
    domSelector: ".img-container",
    animation: true
}));

// 获取用户聚信立报告信息
function getJxlContent() {
    $("#jxl").empty();
    var orderId = $("#orderId").val();
    $.ajax({
        type: "GET",
        url: "/back/mmanUserInfo/jxlReport",
        data: {
            id: orderId
        },
        success: function (data) {
            if (data.code = 200) {
                $("#jxl").append(data);
            }
        }
    });
    $("#collectionRecord").hide();
    // $("#userPhoto").parent("td").addClass("photoDivStyle");
}
//获取所有共债手机号的拼接字符串
function getCallLogsPhonesStr(call_logs) {
    var phonesStr = "";
    for (var cl in call_logs) {
        phonesStr += cl;
    }
    return phonesStr;
}
//将补充手机号字符串转化为数组返回
function getAdditPhonesArrary(str) {
    // debugger;
    var additPhonesArrary = new Array();
    if (str == "") {
        return additPhonesArrary;
    } else if (str != "" && str.indexOf(",") < 0) {
        additPhonesArrary[0] = str;
        return additPhonesArrary;
    } else {
        additPhonesArrary = str.split(",");
        return additPhonesArrary;
    }
}


// 获取用户通话记录信息（所有共债手机通话记录信息）
function getContactRecords() {
    $("#recordListContent").empty();
    $("#userRealContent").empty();
    $("#collectionRecordId").val("");
    $("#contactId").val("");
    var idNumber = $("#idNumber").val();
    var orderId = $("#orderId").val();
    var userName = $("#userName").val();
    var firstContactName = $("#firstContactName").val();
    var firstContactPhone = $("#firstContactPhone").val();
    var secondContactName = $("#secondContactName").val();
    var secondContactPhone = $("#secondContactPhone").val();
    var additionalPhones = $("#additionalPhones").val();
    var additPhonesArrary = getAdditPhonesArrary(additionalPhones);
    $('#contactRecordsXJX').empty();
    $('#contactRecordsGZ').empty();
    for (var i = 1; i <= additPhonesArrary.length; i++) {
        $("#contactRecordsGZ" + i).empty();
    }
    $.ajax({
        type: "GET",
        url: "/back/collectionOrder/getContactRecords",
        contentType: "application/json; charset=UTF-8",
        data: {
            idNumber: idNumber,
            orderId: orderId
        },
        dataType: "json",
        success: function (data) {
            //催收成功，不允许查看通话记录
            if (data == null) {
                alertMsg.warn("催收成功订单不允许查看通话记录！")
                return;
            }
            //对应身份证号返回通话记录信息为空时：
            var obj = eval("(" + data + ")");//转换为json对象
            var call_logsCopy = obj["data"]["call_logs"];//获取通话记录数据call_logs
            if (call_logsCopy.length == 0) {
                var res = '<table><tr>';
                //第一紧急联系人
                res += '<td style="width: 85px;height: 24px;"><input type="radio" name="concatInfo" onchange="getSelectedCallRecordJinji(this);" ' + '" isCloseRelation="' + "1" + '"' +
                    'userPhone="' + firstContactPhone + '"/>' + '&nbsp&nbsp' + firstContactName + '</td><td style="width: 90px;height: 24px;">' + firstContactPhone + '</td>' +
                    '<td style="width: 40px;height: 24px;"></td>';
                //第二紧急联系人
                res += '<td style="width: 85px;height: 24px;"><input type="radio" name="concatInfo" onchange="getSelectedCallRecordJinji(this);" ' + '" isCloseRelation="' + "2" + '"' +
                    'userPhone="' + secondContactPhone + '"/>' + '&nbsp&nbsp' + secondContactName + '</td><td style="width: 90px;height: 24px;">' + secondContactPhone +
                    '</td><td colspan="4" style="color: red">此行为共债用户紧急联系人</td>';
                res += '</tr><table>';
                $('#contactRecordsXJX').append(res);
                return;
            }

            var objCopy = eval("(" + data + ")");//转换为json对象
            var call_logs = objCopy["data"]["call_logs"];//获取通话记录数据call_logs
            var xjxCallLogs = '';
            //平台注册手机号
            if (getCallLogsPhonesStr(call_logsCopy).indexOf(userName) == -1 || call_logs[userName].length == 0) {
                xjxCallLogs += '<table><tr>';
                //第一紧急联系人
                xjxCallLogs += '<td style="width: 85px;height: 24px;"><input type="radio" name="concatInfo" onchange="getSelectedCallRecordJinji(this);" ' + '" isCloseRelation="' + "1" + '"' +
                    'userPhone="' + firstContactPhone + '"/>' + '&nbsp&nbsp' + firstContactName + '</td><td style="width: 90px;height: 24px;">' + firstContactPhone + '</td>' +
                    '<td style="width: 40px;height: 24px;"></td>';
                //第二紧急联系人
                xjxCallLogs += '<td style="width: 85px;height: 24px;"><input type="radio" name="concatInfo" onchange="getSelectedCallRecordJinji(this);" ' + '" isCloseRelation="' + "2" + '"' +
                    'userPhone="' + secondContactPhone + '"/>' + '&nbsp&nbsp' + secondContactName + '</td><td style="width: 90px;height: 24px;">' + secondContactPhone +
                    '</td><td colspan="4" style="color: red">此行为共债用户紧急联系人</td>';
                xjxCallLogs += '</tr><table>';
            } else {
                xjxCallLogs += '<table><tr>';
                //第一紧急联系人
                xjxCallLogs += '<td style="width: 85px;height: 24px;"><input type="radio" name="concatInfo" onchange="getSelectedCallRecordJinji(this);" ' + '" isCloseRelation="' + "1" + '"' +
                    'userPhone="' + firstContactPhone + '"/>' + '&nbsp&nbsp' + firstContactName + '</td><td style="width: 90px;height: 24px;">' + firstContactPhone + '</td>' +
                    '<td style="width: 40px;height: 24px;"></td>';
                //第二紧急联系人
                xjxCallLogs += '<td style="width: 85px;height: 24px;"><input type="radio" name="concatInfo" onchange="getSelectedCallRecordJinji(this);" ' + '" isCloseRelation="' + "2" + '"' +
                    'userPhone="' + secondContactPhone + '"/>' + '&nbsp&nbsp' + secondContactName + '</td><td style="width: 90px;height: 24px;">' + secondContactPhone + '</td><td colspan="4" style="color: red">此行为共债用户紧急联系人</td>';
                xjxCallLogs += '</tr><tr>';
                for (var i = 1; i <= call_logs[userName].length; i++) {
                    //判断用户名是否有值，没有则显示未知
                    var name = call_logs[userName][i - 1]["contact_name"].toString();
                    if (name == null || name == "" || name == "undefined") {
                        name = "未知";
                    } else {
                        name = call_logs[userName][i - 1]["contact_name"];
                    }

                    xjxCallLogs += '<td style="width: 85px;height: 24px;"><input type="radio" name="concatInfo" onchange="getSelectedCallLogVal(this);" value="' + call_logs[userName][i - 1]["contact_mobile"] + '" ' +
                        'userPhone="' + call_logs[userName][i - 1]["contact_mobile"] + '" callUserName="' + name + '"/>' + '&nbsp&nbsp' + name + '</td><td style="width: 9px;height: 24px;">' +
                        call_logs[userName][i - 1]["contact_mobile"] + '</td><td style="width: 120px;height: 24px;">' + call_logs[userName][i - 1]["contact_times"] + '次</td>' + '&nbsp&nbsp&nbsp';
                    if (i % 5 == 0) {
                        xjxCallLogs += '</tr>';
                    }
                }
                xjxCallLogs += '</table>';
            }
            //补充手机号
            additPhonesArrary = getAdditPhonesArrary(additionalPhones);
            //补充手机号
            var res = new Array();
            for (var i = 0; i < additPhonesArrary.length; i++) {
                // debugger;
                res[i] = '';
                if (getCallLogsPhonesStr(call_logs).indexOf(additPhonesArrary[i]) == -1 || call_logs[additPhonesArrary[i]].length == 0) {
                    res[i] += '<table><tr><td>';
                    res[i] += '<h2>此手机号暂无通话记录！！！</h2>';
                    res[i] += '</td></tr></table>';
                } else {
                    res[i] += '<table><tr>';
                    for (var j = 1; j <= call_logs[additPhonesArrary[i]].length; j++) {
                        //判断用户名是否有值，没有则显示未知
                        var name = call_logs[additPhonesArrary[i]][j - 1]["contact_name"].toString();
                        if (name == null || name == "" || name == "undefined") {
                            name = "未知";
                        } else {
                            name = call_logs[userName][i - 1]["contact_name"];
                        }

                        res[i] += '<td style="width: 85px;height: 24px;"><input type="radio" name="concatInfo" onchange="getSelectedCallLogVal(this);" value="' + call_logs[additPhonesArrary[i]][j - 1]["contact_mobile"] + '" ' +
                            'userPhone="' + call_logs[additPhonesArrary[i]][j - 1]["contact_mobile"] + ' "callUserName="' + name + '"/>' + '&nbsp&nbsp' + name + '</td><td style="width: 90px;height: 24px;">' +
                            call_logs[additPhonesArrary[i]][j - 1]["contact_mobile"] + '</td><td style="width: 120px;height: 24px;">' + call_logs[additPhonesArrary[i]][j - 1]["contact_times"] + '次</td>';
                        if (j % 5 == 0) {
                            res[i] += '</tr>';
                        }
                    }
                    res[i] += '</table>';
                }
            }
            $('#contactRecordsXJX').append(xjxCallLogs);
            for (var i = 1; i <= res.length; i++) {
                $("#contactRecordsGZ" + i).append(res[i - 1]);
            }

            $("#sss").click();//点击刷新第一个tab页
            console.log("*******************");
        }, error: function () {
        }, error: function () {
            alertMsg.error("调用出错！");
        }
    });
}

// 获取借款人联系人信息
function getUserRealContent() {
    $("#collectionRecordId").val("");
    $("#phoneNumber").val("");
    $("#selectCallRecordFlag").val("");
    var orderId = $("#orderId").val();
    $("#collectionRecord").show();
    $('#userRealContent').empty();
    $.ajax({
        type: "GET",
        url: "/back/mmanUserRela/getMmanUserRelaPage",
        data: {
            id: orderId
        },
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: function (data) {
            if (data == null) {
                alertMsg.warn("催收成功订单不允许查看通讯录！")
                return;
            }
            var res = '<table class="table" style="width: 100%;"><tr>';
            var count = data.length;
            for (var i = 1; i <= count; i++) {
                if (i < 3) {
                    res += '<td style="width: 105px;height: 24px;"><input type="radio" name="concatInfo" onchange="getSelectedVal(this);" value="' + data[i - 1].id + '" isCloseRelation="' + i + '"' +
                        'userPhone="' + data[i - 1].infoValue + '"/>' + '&nbsp&nbsp' + data[i - 1].infoName + '</td><td style="width: 120px;height: 24px;">' + data[i - 1].infoValue + '</td>';
                    if (i == 2) {
                        res += '<td colspan="4" style="color: red">此行为共债用户紧急联系人</td></tr>';
                    }
                } else {
                    res += '<td style="width: 105px;height: 24px;"><input type="radio" name="concatInfo" onchange="getSelectedVal(this);" value="' + data[i - 1].id + '" ' +
                        'userPhone="' + data[i - 1].infoValue + '"/>' + '&nbsp&nbsp' + data[i - 1].infoName + '</td><td style="width: 120px;height: 24px;">' + data[i - 1].infoValue + '</td>';
                    if ((i - 2) % 5 == 0) {
                        res += '</tr>';
                    }
                }
            }
            res += '</table>';
            $('#userRealContent').append(res);
        }
    });
}

// 获取借款人借款、还款信息
function getUserRepayInfo() {
    $("#repayInfo").empty();
    $("#payDetail").empty();
    $("#withholdDetail").empty();
    var orderId = $("#orderId").val();
    $("#collectionRecord").show();
    $.ajax({
        type: "GET",
        url: "/back/collectionOrder/getUserRepayInfo",
        data: {
            id: orderId
        },
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: function (data) {
            var payResult = "";
            var payDetailResult = "";
            var withholdDetailResult = "";
            payResult += '<tr><td class="htd">借款编号:</td>';
            payResult += '<td class="ttd">' + data.collectionOrder.loanId + '</td>';
            payResult += getProductNameColumnResult(data.userLoan.borrowingType) + '</tr>';
            payResult += '<tr><td class="htd">借款时间:</td><td class="ttd">' + getFormatDate(data.userLoan.loanStartTime) + '</td>';
            payResult += '<td class="htd">到期本金:</td><td class="ttd">' + data.userLoan.loanMoney + '</td>';
            payResult += getOrderInfoColumnResult(data);
            payResult += '<td class="htd">滞&nbsp&nbsp纳&nbsp&nbsp金:</td><td class="ttd">' + data.userLoan.loanPenalty + '</td>';
            payResult += '<td class="htd">逾期天数:</td><td class="ttd">' + data.collectionOrder.overdueDays + '</td></tr>';
            payResult += '<td class="htd">应还时间:</td><td class="ttd">' + getFormatDate(data.userLoan.loanEndTime) + '</td>';
            payResult += '<td class="htd">应还总额:</td><td class="ttd" colspan="7">' + data.totalAmount + '</td></tr>';
            payResult += '<tr> <td class="hhtd">扣款银行:</td><td class="tttd">' + data.bankCard.depositBank + '</td><td class="hhtd">银行卡号:</td><td class="tttd">' + data.bankCard.bankCard + '</td>';
            payResult += getPayInfoColumnResult(data);
            payResult += '</tr>';
            $("#repayInfo").append(payResult);

            // 还款详情列表
            $.each(data.payDetail, function (index, detail) {
                payDetailResult += '<tr>';
                payDetailResult += '<td>' + (index + 1) + '</td>';
                payDetailResult += '<td>' + detail.realMoney + '</td>';
                payDetailResult += '<td>' + detail.realPenlty + '</td>';
                payDetailResult += '<td>' + getValues(detail.realgetAccrual) + '</td>';
                payDetailResult += '<td>' + detail.realPrinciple + '</td>';
                payDetailResult += '<td>' + detail.realInterest + '</td>';
                payDetailResult += '<td>' + getValues(detail.remainAccrual) + '</td>';
                payDetailResult += '<td>' + getReturnType(detail.returnType) + '</td>';
                payDetailResult += '<td>' + getFormatDate(detail.updateDate) + '</td>';
                payDetailResult += '</tr>';
            });
            $("#payDetail").append(payDetailResult);

            // 代扣记录列表
            $.each(data.withholdList, function (index, withhold) {
                withholdDetailResult += '<tr>';
                withholdDetailResult += '<td>' + getFormatDate(withhold.createDate) + '</td>';
                withholdDetailResult += '<td>' + withhold.loanUserName + '</td>';
                withholdDetailResult += '<td>' + withhold.loanUserPhone + '</td>';
                withholdDetailResult += '<td>' + getCollectionStatus(withhold.orderStatus) + '</td>';
                withholdDetailResult += '<td>' + getValues(withhold.arrearsMoney) + '</td>';
                withholdDetailResult += '<td>' + getValues(withhold.hasalsoMoney) + '</td>';
                withholdDetailResult += '<td>' + getValues(withhold.deductionsMoney) + '</td>';
                withholdDetailResult += '<td>' + getWithholdStatus(withhold.status) + '</td>';
                withholdDetailResult += '<td>' + getReason(withhold.remark) + '</td>';
                withholdDetailResult += '<td>' + getFormatDate(withhold.updateDate) + '</td>';
                withholdDetailResult += '</tr>';
            });
            $("#withholdDetail").append(withholdDetailResult);
        }
    });
}

// 格式化时间函数
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

function getFormatDate(now) {
    if (now == null) {
        return "";
    }
    var d = new Date(now);
    return d.Format("yyyy-MM-dd hh:mm:ss");
}

function getValues(data) {
    if (data == null) {
        return 0.00;
    } else {
        return data;
    }
}

function getReason(data) {
    if (data == null) {
        return "";
    } else {
        return data;
    }
}

function getCollectionStatus(status) {
    if (status == '1') {
        return "催收中";
    } else if (status == '2') {
        return "承诺还款";
    } else if (status == '3') {
        return "待催收（委外）";
    } else if (status == '4') {
        return "催收成功";
    } else if (status == '5') {
        return "续期";
    } else {
        return "待催收";
    }
}

function getWithholdStatus(status) {
    if (status == '0') {
        return "申请中";
    } else if (status == '1') {
        return "成功";
    } else if (status == '2') {
        return "失败";
    }
}


function getReturnType(type) {
    if (type == '1') {
        return "支付宝还款";
    } else if (type == '2') {
        return "银行卡主动还款";
    } else if (type == '3') {
        return "催收代扣";
    } else if (type == '4') {
        return "对公银行卡转账";
    } else if (type == '5') {
        return "线下还款";
    } else if (type == '6') {
        return "小额减免";
    } else if (type == '8') {
        return "扫码还款自动推送";
    } else if (type == '10') {
        return "优惠券还款抵用";
    } else if (type == '12') {
        return "催收代扣";
    } else if (type == '13') {
        return "用户主动还款";
    } else if (type == '99') {
        return "大额减免";
    } else {
        return "未知还款方式";
    }
}

function getProductNameColumnResult(data) {
    if (data == '2') {
        return '<td class="ttd" colspan="8"></td>';
    } else if (data == '1') {
        return '<td class="htd">分期类型:</td><td class="ttd" colspan="7">现金分期</td>';
    } else if (data == '3') {
        return '<td class="htd">分期类型:</td><td class="ttd">商品分期</td><td class="htd">分期产品:</td><td colspan="5" class="ttd">' + data.order.productName + '</td>';
    }
}

function getOrderInfoColumnResult(data) {
    if (data.userLoan.borrowingType == '2') {
        return '<td class="htd">服&nbsp&nbsp务&nbsp&nbsp费:</td> <td class="ttd">' + data.userLoan.serviceCharge + '</td>';
    } else {
        return '<td class="htd">到期利息:</td><td class="ttd">' + data.userLoan.accrual + '</td>';
    }
}


function getPayInfoColumnResult(data) {
    var loan = data.userLoan;
    if (loan.borrowingType == '2') {
        return '<td class="hhtd">已还金额:</td><td class="tttd"><font color="red">' + data.payMonery + '</font></td><td class="hhtd">剩余应还:</td>' +
            '<td class="tttd" colspan="3"><font color="red">' + data.remainAmount + '</font></td>';
    } else {
        return '<td class="hhtd">逾期期数:</td><td class="tttd"><font color="red">' + loan.termNumber + '</font></td> <td class="hhtd">已还金额:</td>' +
            '<td class="tttd"><font color="red">' + data.payMonery + '</font></td> <td class="hhtd">剩余应还:</td><td class="tttd"><font color="red">' + data.remainAmount + '</font></td>';
    }
}


$("input[name='promiseRepay']").change(function () {
    var val = $('input:radio[name="promiseRepay"]:checked').val();
    if (val == '0') {
        $("#promiseRepayTime").hide();
    } else {
        $("#promiseRepayTime").show();
    }
});

$("input[name='isConnected']").change(function () {
    var val = $('input:radio[name="isConnected"]:checked').val();
    if (val == '1') {
        $("#communicate").show();
    } else {
        $("#communicate").hide();
    }
});

// 获取短信内容
$("#msgTemplate").change(function () {
    var msgTemplate = $("#msgTemplate").val();
    if (msgTemplate == "") {
        $("#msgContent").val("请选择短信模板！");
        return;
    }
    $.ajax({
        url: "collectionOrder/refreshMsg",
        type: "GET",
        data: {
            "id": $("#id").val(),
            "msgId": msgTemplate
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success: function (data) {
            $("#msgContent").val(data.msgContent);
            $("#msgId").val(data.msgId);
        },
        error: function () {
            $("#msgContent").val("请关闭催收页面，重新选择催收订单，点击'催收'进入页面！");
        }
    })
});


$("select[name='collectionMode']").change(function () {
    var val = $("select[name='collectionMode'] option:selected").val();
    if (val == '1') {
        $("#collectionWithPhone").show();
        $("#collectionWithMsg").hide();
    } else {
        $("#collectionWithPhone").hide();
        $("#collectionWithMsg").show();
    }
});


$("#addCollectionRecord").click(function () {
    var val = $("select[name='collectionMode'] option:selected").val();
    if (val == '1') {
        // alertMsg.warn("打电话...电话催收");
        // 保存对应的催收记录
        saveCollectionRecord();
    } else {
        // alertMsg.info("发送短信...短信催收");
        // 发送短信
        sendMsg();
    }
});

// 获取订单的所有催收记录
function getRecordLists() {
    var orderId = $("#orderId").val();
    $("#recordListContent").empty();
    $("#contactId").val("");
    // $("#phoneNumber").val("");
    $("#selectCallRecordFlag").val("");
    $("#collectionRecordId").val("");
    $.ajax({
        type: "GET",
        url: "/back/collectionRecord/getRecordListsByOrderId",
        data: {
            id: orderId
        },
        dataType: "html",
        success: function (data) {
            if (data.code = 200) {
                $("#recordListContent").append(data);
            }
        }
    });
};

// 依据条件动态查询订单对应催收记录
$("#searchOrderCollectionRecord").click(function () {
    var overdueLevel = $("select[name='overdueLevel'] option:checked").val();
    var collectionStatus = $("select[name='collectionStatus'] option:checked").val();
    var collectionType = $("select[name='collectionType'] option:checked").val();
    var communicationSituation = $("select[name='communicationSituation'] option:checked").val();
    var collectionName = $("input[name='collectionName']").val();
    var orderId = $("#orderId").val();
    $("#recordListContent").empty();
    $.ajax({
        type: "GET",
        url: "/back/collectionRecord/getRecordListsByOrderId",
        data: {
            id: orderId,
            overdueLevel: overdueLevel,
            collectionStatus: collectionStatus,
            collectionType: collectionType,
            communicationSituation: communicationSituation,
            collectionName: collectionName
        },
        dataType: "html",
        success: function (data) {
            if (data.code = 200) {
                $("#recordListContent").append(data);
            }
        },
        error: function () {
            alertMsg.error("系统开小差了,请稍后再试...");
        }
    });
});

// 添加催收记录
function saveCollectionRecord() {
    var phoneNumber = $("#phoneNumber").val();
    var callUserName = $("#callUserName").val();
    var selectCallRecordFlag = $("#selectCallRecordFlag").val();
    var collectionMode = $("select[name='collectionMode'] option:checked").val();
    var repaymentTime = $("#repaymentTime").val();
    var communication = $("input[name='communication']:checked").val();
    var collectionContent = $("#collectionContent").val();
    var orderId = $("#orderId").val();
    // 借款人用户id
    var userId = $("#userId").val();
    var loanId = $("#orderLoanId").val();
    var orderStatus = $("#orderStatus").val();
    if (orderStatus == 4) {
        alertMsg.warn("催收完成订单不允许添加催收记录！");
        return;
    }
    var contactId = $("#contactId").val();
    var collectionRecordId = $("#collectionRecordId").val();
    var collectionContent = $("textarea[name='content']").val();
    var promiseRepay = $("input[name='promiseRepay']:checked").val();
    var isConnected = $("input[name='isConnected']:checked").val();

    // 是否是紧急联系人
    var isCloseRelation = $("#isCloseRelation").val();
    if (promiseRepay == 1) {
        if (repaymentTime == null || repaymentTime == '') {
            $("#repaymentTime").addClass("required");
            alertMsg.warn("用户承诺还款，则承诺还款时间必填。");
            return;
        }
    }
    if (isConnected == 1) {
        if (communication == null) {
            $("input[name='communication']").addClass("required");
            alertMsg.warn("电话接通，则沟通情况必选。");
            return;
        }
    }

    var fengKongIds = getFengKongIds();
    var advice = $("select[name='collectionAdvice'] option:selected").val();
    $.ajax({
        type: "POST",
        url: "/back/collectionRecord/saveRecord",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: {
            selectCallRecordFlag: selectCallRecordFlag,
            callUserName: callUserName,
            phoneNumber: phoneNumber,
            collectionMode: collectionMode,
            repaymentTime: repaymentTime,
            communication: communication,
            collectionContent: collectionContent,
            orderId: orderId,
            contactId: contactId,
            userId: userId,
            loanId: loanId,
            orderStatus: orderStatus,
            collectionRecordId: collectionRecordId,
            isCloseRelation: isCloseRelation,
            fengKongIds: fengKongIds,
            advice: advice
        },
        success: function () {
            alertMsg.correct("添加催收记录成功！");
            // 刷新催收记录
            getRecordLists();
            // 清空之前的选择记录
            clearRecord();
        },
        error: function () {
            alertMsg.error("系统错误，请稍后再试！")
        }
    })
}

// 发送短信
function sendMsg() {
    var orderId = $("#orderId").val();
    var parentId = $("#parentId").val();
    var phoneNumber = $("#phoneNumber").val();
    var promiseRepay = $("input[name='promiseRepay']:checked").val();
    var repaymentTime = $("#repaymentTime").val();
    var collectionRecordId = $("#collectionRecordId").val();
    if (promiseRepay == 1) {
        if (repaymentTime == null || repaymentTime == '') {
            $("#repaymentTime").addClass("required");
            alertMsg.warn("用户承诺还款，则承诺还款时间必填。");
            return;
        }
    }

    var orderStatus = $("#orderStatus").val();
    if (orderStatus == 4) {
        alertMsg.warn("催收完成订单不允许发送短信！");
        return;
    }
    var msgId = getMsgId();
    $.ajax({
        type: "GET",
        url: "/back/collectionOrder/sendMsg",
        data: {
            orderId: orderId,
            msgId: msgId,
            parentId: parentId,
            phoneNumber: phoneNumber,
            collectionRecordId: collectionRecordId
        },
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                alertMsg.correct(data.msg);
                // 保存催收记录
                saveCollectionRecord();
            } else {
                alertMsg.warn(data.msg);
            }
        }
    });
}

// 获取短信id
function getMsgId() {
    var msgId = $("select[name='msgTemplate'] option:checked").val();
    if (msgId == null || msgId == "") {
        return 0;
    }
    return msgId;
}

// 设置选中联系人的id值
function getSelectedVal(i) {
    if ($(i).index() < 3) {
        $("#isCloseRelation").val($(i).attr("isCloseRelation"));
    }
    $("#contactId").val($(i).val());
    var userPhone = $(i).attr("userPhone");
    $("#phoneNumber").val(userPhone);
}

// 通话记录页面紧急联系人选中时，设置isCloseRelation，不设置contactId
function getSelectedCallRecordJinji(i) {
    if ($(i).index() < 3) {
        $("#isCloseRelation").val($(i).attr("isCloseRelation"));
    }
    var userPhone = $(i).attr("userPhone");
    $("#phoneNumber").val(userPhone);
}

// 设置选中通话记录的id值
function getSelectedCallLogVal(i) {
    if ($(i).index() < 3) {
        $("#isCloseRelation").val($(i).attr("isCloseRelation"));
    }
    var userPhone = $(i).attr("userPhone");
    var callUserName = $(i).attr("callUserName");
    $("#phoneNumber").val(userPhone);
    $("#callUserName").val(callUserName);
    $("#selectCallRecordFlag").val("selected");
}

// 清空之前添加催收记录所填写的数据
function clearRecord() {
    $("textarea[name='content']").val("");
    $("input[name='communication']").removeAttr("checked");
    $("input[name='promiseRepay']").removeAttr("checked");
    $("input[name='isConnected']").removeAttr("checked");
    $("#repaymentTime").val("");
    $("#contactId").val("");
    $("#selectCallRecordFlag").val("");
    $("#callUserName").val("");
    $("#phoneNumber").val("");
    $("#collectionRecordId").val("");
    $("#notPromiseRepay").attr("checked", "checked");
    $("#promiseRepayTime").hide();
    // 催收建议相关
    $("input[name='fengkongLable']:checked").removeAttr("checked");
    $("select[name='collectionAdvice'] option:selected").removeAttr("selected");
    $("#defaultSelect").attr("selected", "selected");
}


// 获取催收记录
function getCollectionLists() {
    $("#contactId").val("");
    $("#collectionRecord").show();
    getRecordLists();
}

// 设置选中催收记录的id,传递到后台便于查询数据
function getChooseVal(data) {
    $("#collectionRecordId").val(data.id);
}

//获取订单流转日志
function getStatusChangeLogContent() {
    var orderId = $("#orderId").val();
    $("#statusChangeLog").empty();
    if (orderId == null || orderId == '') {
        alertMsg.warn("参数错误，请稍后再试！");
        return;
    }
    $.ajax({
        type: "GET",
        url: "/back/collectionRecordStatusChangeLog/getListLog",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        data: {
            id: orderId
        },
        success: function (data) {
            var logResult = '<table class="table" style="width: 100%;text-align: center;">';
            logResult += '<thead><tr style="height: 24px;"><th align="center" width="40">序号</th>';
            logResult += '<th align="center" width="50">操作前状态</th><th align="center" width="50">操作后状态 </th>';
            logResult += '<th align="center" width="50">操作类型</th><th align="center" width="50">当前催收员</th>';
            logResult += '<th align="center" width="50">催收组 </th><th align="center" width="50">订单组</th>';
            logResult += '<th align="center" width="100">创建时间</th><th align="center" width="50">操作人</th>';
            logResult += '<th align="center" width="200">操作备注</th></tr></thead><tbody>';
            $.each(data.logList, function (index, log) {
                logResult += generateLog(log, index);
            });
            logResult += '</tbody></table>';

            $("#statusChangeLog").append(logResult);
        },
        error: function () {
            alertMsg.error("系统开小差了,请稍后再试...");
        }
    })
}


// 生成流转日志
function generateLog(log, index) {
    var res = "";
    res += '<tr style="height: 24px;"><td>' + (index + 1) + '</td><td>' + getCollectionStatus(log.beforeStatus) + '</td>';
    res += '<td>' + getCollectionStatus(log.afterStatus) + '</td><td>' + getLogType(log.type) + '</td>';
    res += '<td>' + log.currentCollectionUserId + '</td><td>' + getOrderLevel(log.currentCollectionUserLevel) + '</td>';
    res += '<td>' + getOrderLevel(log.currentCollectionOrderLevel) + '</td><td>' + getFormatDate(log.createDate) + '</td>';
    res += '<td>' + log.operatorName + '</td><td>' + log.remark + '</td></tr>';
    return res;
}

// 获取用户gxb报告
function getEcommerceInfo() {
    $("#ecommerce").empty();
    var phone = $("#phoneNumber").val();
    var userId = $("#userId").val();
    if (phone == null && userId == '') {
        alertMsg.warn("参数错误，请稍后再试！");
        return;
    }
    var orderStatus = $("#orderStatus").val();
    if (orderStatus == 4) {
        alertMsg.warn("催收完成订单不允许查看淘宝交易信息！");
        return;
    }
    var ecommerceInfo = "";
    $.ajax({
        type: "GET",
        url: "/back/collectionOrder/get-user-ecommerce-info",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        data: {
            phone: phone,
            userId: userId
        },
        success: function (data) {
            debugger;
            if (data == null) {
                alertMsg.info("暂无此用户相关报告或查询失败！");
                return;
            };
            $.each(data, function (index,info) {
                ecommerceInfo += generateEcommerceInfo(info)
            });
            $("#ecommerce").append(ecommerceInfo);
        },
        error: function () {
            alertMsg("暂无此用户相关报告或查询失败！")
        }
    });
}
function generateEcommerceInfo(info) {
    var ecommerceInfo = "<table class='ecommerceTable'><tbody>";
    ecommerceInfo += '<tr><td class="xhtd" style="width:150px;">收&nbsp&nbsp货&nbsp&nbsp人:</td><td class="ettd" style="width:100px;">' + info.receiveName + '</td>';
    ecommerceInfo += '<td class="ehtd" style="width:150px;">手&nbsp&nbsp机&nbsp&nbsp号:</td><td class="ettd" style="width:50px;">' + info.telNumber + '</td>';
    ecommerceInfo += '<td class="ehtd" style="width:150px;">交易订单总数:</td><td class="ettd">' + info.tradeCount + '</td></tr>';

    ecommerceInfo += '<tr><td class="xhtd" style="width:150px;">近3个月交易订单总数:</td><td class="ettd">' + info.tradeCountOf3m + '</td>';
    ecommerceInfo += '<td class="xhtd" style="width:150px;">收货地址:</td><td class="ettd" style="width:500px;">' + info.address + '</td>';
    ecommerceInfo += '</tr></tbody></table></p><div class="divider"></div>';
    return ecommerceInfo;
}

function getLogType(type) {
    if (type == null || type == '') {
        return "";
    } else if (type == 1) {
        return "入催";
    } else if (type == 2) {
        return "逾期等级转换";
    } else if (type == 3) {
        return "转单";
    } else if (type == 4) {
        return "委外";
    } else if (type == 5) {
        return "催收成功";
    } else {
        return "";
    }
}


function getOrderLevel(level) {
    if (level == null || level == '') {
        return "";
    } else if (level == 3) {
        return "S1";
    } else if (level == 4) {
        return "S2";
    } else if (level == 5) {
        return "M1-M2";
    } else if (level == 6) {
        return "M2-M3";
    } else if (level == 7) {
        return "M3+";
    } else if (level == 8) {
        return "M6+";
    } else if (level == 11) {
        return "F-M1";
    } else if (level == 12) {
        return "F-M2";
    } else if (level == 13) {
        return "F-M3";
    } else if (level == 16) {
        return "F-M6";
    } else {
        return "";
    }
}

function getFengKongIds() {
    var ids = "";
    $("input[name='fengkongLable']:checked").each(function () {
        ids += $(this).val() + ",";
    });
    ids = ids.substring(0, ids.length - 1);
    return ids;
}

$("input[name='isConnected']").click(function () {
    $("input[name='communication']").removeAttr("checked");
});
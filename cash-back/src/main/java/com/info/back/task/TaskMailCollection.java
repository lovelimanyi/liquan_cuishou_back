package com.info.back.task;

import com.info.back.mail.MailCenterConstant;
import com.info.back.mail.MailInfo;
import com.info.back.mail.MessageSender;
import com.info.back.service.IMailCollectionService;
import com.info.back.utils.MerchantNoUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class TaskMailCollection {
    protected Logger logger = Logger.getLogger(TaskMailCollection.class);
    private static   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private IMailCollectionService iMailCollectionService;

    //发送派单结果到邮件
    public void mailSendResult() {
    List<Map<String,Object>> list= iMailCollectionService.selectSendResult();
        if (list!=null&&list.size()>0) {
            logger.info("mailSendResult-list-count:"+list.size());
            MailInfo mailInfo = new MailInfo(dateFormat.format(new Date()) + " 派单结果", MerchantNoUtils.getMerchantName(),sendResultlistHtml(list), MailCenterConstant.SEND_RESULE_MAIL, null);
            MessageSender.sendHtmlMail(mailInfo);
        }
    }


    //发送3日无催收告警到邮件
    public void mailBeyondWarn() {
        List<Map<String,Object>> list= iMailCollectionService.selectBeyondWarn();
        if (list!=null&&list.size()>0){
            MailInfo mailInfo = new MailInfo(dateFormat.format(new Date())+" 3日无催告警邮件", MerchantNoUtils.getMerchantName(), beyondWarnlistHtml(list), MailCenterConstant.BEYOND_WARN, null);
            MessageSender.sendHtmlMail(mailInfo);
        }

    }





    //list转html拼接
    public static String beyondWarnlistHtml(List<Map<String,Object>> list){
        StringBuilder p=new StringBuilder();
        p.append("<p>截止到今日"+ MerchantNoUtils.getMerchantName()+"，各公司3日无催记情况:</p>");
        for (int i = 0; i <list.size() ; i++) {
            p.append("<p>");
            p.append("公司:"+list.get(i).get("公司"));
            p.append(",催收员:"+list.get(i).get("催收员"));
            p.append(",账龄:"+list.get(i).get("账龄"));
            p.append(",单数:"+list.get(i).get("单数"));
            p.append(";</p>");
        }
        return p.toString();
    }


    public static String sendResultlistHtml(List<Map<String,Object>> list){
        StringBuilder p=new StringBuilder();
        p.append("<p>以下是:"+ MerchantNoUtils.getMerchantName()+"派单结果数据:</p>");
        for (int i = 0; i <list.size() ; i++) {
            p.append("<p>");
            p.append("公司:"+list.get(i).get("公司"));
            p.append(",分组:"+list.get(i).get("分组"));
            p.append(",派单数量:"+list.get(i).get("派单数量"));
            p.append("(人数:"+list.get(i).get("人数"));
            p.append(",人均派单量:"+list.get(i).get("人均派单量"));
            p.append(");</p>");

  /*          String p="<p>"+"公司:"+list.get(i).get("公司")+",催收员:"+list.get(i).get("催收员")
                    +",账龄:"+list.get(i).get("账龄")+",单数:"+list.get(i).get("单数")+";</p>";*/
        }
        return p.toString();
    }

}

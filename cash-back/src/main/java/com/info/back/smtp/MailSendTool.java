package com.info.back.smtp;

import com.info.back.utils.ServiceResult;
import com.info.back.utils.SysCacheUtils;
import com.info.web.pojo.BackConfigParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MailSendTool {
    private static MailSendTool mailSendTool;

    public static MailSendTool getInstance() {
        if (mailSendTool == null) {
            mailSendTool = new MailSendTool();
        }
        return mailSendTool;
    }

    Logger logger = LoggerFactory.getLogger(getClass());
    // //邮件中的图片，为空时无图片。map中的key为图片ID，value为图片地址
    private Map<String, String> pictures;
    // //邮件中的附件，为空时无附件。map中的key为附件ID，value为附件地址
    private Map<String, String> attachments;

    /**
     * 发送邮件
     *
     * @param title   邮件标题
     * @param content 邮件内容
     * @param object  接收邮件的地址，可以是数组、list或者逗号隔开的字符串
     * @return
     */
    public ServiceResult sendEmail(String title, String content, Object object) {
        ServiceResult serviceResult = new ServiceResult("500", "未知异常！");
        try {
            Map<String, String> smtp = SysCacheUtils
                    .getConfigParams(BackConfigParams.SMTP);
            String host = smtp.get("mail_server");
            String from = smtp.get("mail_address");
            String name = smtp.get("mail_name");
            String sender = smtp.get("mail_sender");
            String pwd = smtp.get("mail_password");
            Integer port = Integer.valueOf(smtp.get("mail_port"));
            if (StringUtils.isBlank(host) || StringUtils.isBlank(from)
                    || StringUtils.isBlank(name) || StringUtils.isBlank(pwd)) {
                serviceResult = new ServiceResult("501", "发件人信息不完全，请确认发件人信息！");
            } else {
                JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

                // 设定mail server
                senderImpl.setHost(host);
                senderImpl.setPort(port);
                // 建立邮件消息
                MimeMessage mailMessage = senderImpl.createMimeMessage();

                MimeMessageHelper messageHelper = null;
                messageHelper = new MimeMessageHelper(mailMessage, true,
                        "UTF-8");
                // 设置发件人邮箱
                logger.info("设置发件人邮箱:" + from);
                messageHelper.setFrom(new InternetAddress(from,
                        javax.mail.internet.MimeUtility.encodeText(sender)));
                // messageHelper.setFrom(from);

                // 设置收件人邮箱
                if (object == null) {
                    serviceResult = new ServiceResult("502", "收件人邮箱不得为空！");
                } else {
                    String[] toEmailArray = null;
                    if (object instanceof String) {
                        String phoneString = String.valueOf(object).replaceAll(
                                "，", ",");
                        int lastIndex = phoneString.length() - 1;
                        if (",".equals(phoneString.indexOf(lastIndex))) {
                            phoneString = phoneString.substring(0, lastIndex);
                        }
                        toEmailArray = new String[]{phoneString};
                    } else if (object instanceof String[]) {
                        toEmailArray = (String[]) object;
                    } else if (object instanceof List) {
                        List<String> list = (List<String>) object;
                        toEmailArray = (String[]) list.toArray(new String[list
                                .size()]);
                    }
                    messageHelper.setTo(toEmailArray);

                    // 邮件主题
                    messageHelper.setSubject(title);

                    // true 表示启动HTML格式的邮件
                    messageHelper.setText(content, true);

                    // 添加图片
                    if (null != pictures) {
                        for (Iterator<Map.Entry<String, String>> it = pictures
                                .entrySet().iterator(); it.hasNext(); ) {
                            Map.Entry<String, String> entry = it.next();
                            String cid = entry.getKey();
                            String filePath = entry.getValue();
                            if (null == cid || null == filePath) {
                                serviceResult = new ServiceResult("503",
                                        "请确认每张图片的ID和图片地址是否齐全！");
                                break;
                            }
                            File file = new File(filePath);
                            if (!file.exists()) {
                                serviceResult = new ServiceResult("504", "图片"
                                        + filePath + "不存在！");
                                break;
                            }

                            FileSystemResource img = new FileSystemResource(
                                    file);
                            messageHelper.addInline(cid, img);
                        }
                    }

                    // 添加附件
                    if (null != attachments) {
                        for (Iterator<Map.Entry<String, String>> it = attachments
                                .entrySet().iterator(); it.hasNext(); ) {
                            Map.Entry<String, String> entry = it.next();
                            String cid = entry.getKey();
                            String filePath = entry.getValue();
                            if (null == cid || null == filePath) {
                                serviceResult = new ServiceResult("504",
                                        "请确认每个附件的ID和地址是否齐全！");
                                break;
                            }

                            File file = new File(filePath);
                            if (!file.exists()) {
                                serviceResult = new ServiceResult("505", "附件"
                                        + filePath + "不存在！");
                                break;

                            }

                            FileSystemResource fileResource = new FileSystemResource(
                                    file);
                            messageHelper.addAttachment(cid, fileResource);
                        }
                    }

                    Properties prop = new Properties();
                    prop.put("mail.smtp.auth", "0".equals(smtp
                            .get("mail_needauth")) ? "true" : "false"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
                    prop.put("mail.smtp.timeout", smtp.get("mail_out"));
                    // 添加验证
                    EmailAutherticator auth = new EmailAutherticator(name, pwd);

                    Session session = Session.getDefaultInstance(prop, auth);
                    senderImpl.setSession(session);
                    try {
                        senderImpl.send(mailMessage);
                        serviceResult = new ServiceResult(
                                ServiceResult.SUCCESS, "全部发送成功!");
                    } catch (Exception e) {
                        logger.error("sendEmail error mailMessage="
                                + mailMessage, e);
                    }
                }

            }

        } catch (Exception e) {
            logger.error("sendEmail error title=" + title + " , content="
                    + content + ",object=" + object + "", e);
        }
        return serviceResult;
    }
}

package com.lanrenspace.assistant.lottery.service;

import com.lanrenspace.assistant.lottery.model.MailModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @ClassName: MailService
 * @Author: helongjun
 * @Date: 2022/12/13 11:47
 * @Description: TODO
 **/
@Slf4j
@Service
public class MailService {

    @Autowired
    private JavaMailSenderImpl mailSender;//注入邮箱工具类

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发邮件
     *
     * @param mailVo
     * @return
     */
    public MailModel sendMail(MailModel mailVo) {
        try {
            checkMail(mailVo);//1.检测邮件
            sendMimeMail(mailVo);//2.发送邮件
            saveMail(mailVo);//3.保存邮件
        } catch (Exception e) {
            log.error("发送邮件失败:", e);//打印错误信息
            mailVo.setStatus("fail");
            mailVo.setError(e.getMessage());
            return mailVo;
        }

        return null;
    }

    /**
     * 检测邮件信息类
     *
     * @param mailVo
     */
    private void checkMail(MailModel mailVo) {
        if (StringUtils.isEmpty(mailVo.getTo())) {
            throw new RuntimeException("邮件收件人不能为空");
        }
        if (StringUtils.isEmpty(mailVo.getSubject())) {
            throw new RuntimeException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(mailVo.getText())) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }

    /**
     * 构建复杂邮件信息类
     *
     * @param mailVo
     */
    private void sendMimeMail(MailModel mailVo) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            mailVo.setFrom(getMailSendFrom());//邮件发信人从配置项读取
            messageHelper.setFrom(mailVo.getFrom());//邮件发信人
            messageHelper.setTo(mailVo.getTo().split(","));//邮件收件人
            messageHelper.setSubject(mailVo.getSubject());//邮件主题
            messageHelper.setText(mailVo.getText());//邮件内容
//            FileSystemResource res = new FileSystemResource(new File("C:\\Users\\sinosoft\\Desktop\\akali.JPG"));
//            messageHelper.addInline("p01",res);
            if (!StringUtils.isEmpty(mailVo.getCc())) {//抄送
                messageHelper.setCc(mailVo.getCc().split(","));
            }
            if (!StringUtils.isEmpty(mailVo.getBcc())) {//密送
                messageHelper.setBcc(mailVo.getBcc().split(","));
            }
            if (mailVo.getMultipartFiles() != null) {//添加邮件附件
                for (MultipartFile multipartFile : mailVo.getMultipartFiles()) {
                    messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
                }
            }
            if (StringUtils.isEmpty((CharSequence) mailVo.getSentDate())) {//发送时间
                mailVo.setSentDate(new Date());
                messageHelper.setSentDate(mailVo.getSentDate());
            }
            mailSender.send(messageHelper.getMimeMessage());//发送邮件
            mailVo.setStatus("ok");
            log.info("发送邮件成功: {}->{}", mailVo.getFrom(), mailVo.getTo());

        } catch (Exception e) {
            throw new RuntimeException(e);//发送失败
        }

    }

    /**
     * 发送模板邮件
     *
     * @param mailVo
     */
    public MailModel sendTemplateMail(MailModel mailVo) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            mailVo.setFrom(getMailSendFrom());//邮件发信人从配置项读取
            messageHelper.setFrom(mailVo.getFrom());//邮件发信人
            messageHelper.setTo(mailVo.getTo().split(","));//邮件收件人
            messageHelper.setSubject(mailVo.getSubject());//邮件主题
            if (!StringUtils.isEmpty(mailVo.getCc())) {//抄送
                messageHelper.setCc(mailVo.getCc().split(","));
            }
            if (!StringUtils.isEmpty(mailVo.getBcc())) {//密送
                messageHelper.setBcc(mailVo.getBcc().split(","));
            }
            if (mailVo.getMultipartFiles() != null) {//添加邮件附件
                for (MultipartFile multipartFile : mailVo.getMultipartFiles()) {
                    messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
                }
            }
            if (StringUtils.isEmpty((CharSequence) mailVo.getSentDate())) {//发送时间
                mailVo.setSentDate(new Date());
                messageHelper.setSentDate(mailVo.getSentDate());
            }
            //使用模板thymeleaf
            //Context是导这个包import org.thymeleaf.context.Context;
            Context context = new Context();
            //定义模板数据
            context.setVariables(mailVo.getAttachment());
            //获取thymeleaf的html模板
            String emailContent = templateEngine.process("/mail", context); //指定模板路径
            messageHelper.setText(emailContent, true);
            //发送邮件
            javaMailSender.send(mimeMessage);
            mailVo.setStatus("ok");
            log.info("发送邮件成功: {}->{}", mailVo.getFrom(), mailVo.getTo());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("模板邮件发送失败->message:{}", e.getMessage());
            throw new RuntimeException("邮件发送失败");
        }
        return mailVo;
    }

    /**
     * 保存邮件
     *
     * @param mailVo
     * @return
     */
    public MailModel saveMail(MailModel mailVo) {
        //将邮件存入数据库


        return mailVo;
    }

    /**
     * 获取邮件发信人
     *
     * @return
     */
    public String getMailSendFrom() {
        return mailSender.getJavaMailProperties().getProperty("from");
    }
}

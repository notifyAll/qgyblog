package com.qiugaoyang.qgyblog.common.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/27.
 *
 *邮箱的工具类 发送激活码的
 */
public class MailUtils {

    public static void sendMail(String to,String code){
        /**
         * 1获得session对象 邮箱的session   javax.mail.Session;
         * 2创建 邮件对向
         * 3发送  Transport
         */

        //1 获得连接对象
        Properties props=new Properties();
        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host","localhost");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        //这个是发送的连接对象
        Session session=Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("service@vivid.com","123");
//                return new PasswordAuthentication("user01@vivid.com","123");
            }
        });

        //2 创建邮件对象
        Message message=new MimeMessage(session);


        try {
            //发件人
            message.setFrom(new InternetAddress("service@vivid.com"));
            //设置收件人
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
                                      //抄送cc 密送bcc

            //设置标题
            message.setSubject("来自地狱的邀请");

            //设置正文
            message.setContent(
                    "<h1>购物天堂传智商城官方激活邮件!点下面链接完成激活操作!" +
                            "</h1>" +
                            "   <h3>" +
                            "  <a href='http://192.168.36.103:8080/shop/user_active.action?code="+code+"'>http://192.168.36.103:8080/shop/user_active.action?code="+code+"</a>" +
                            "</h3>",
                    "text/html;charset=UTF-8");
//            message.setContent("测试 邮箱成功 "+code,"text/html;charset=UTF-8");
            //3发送
           Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        sendMail("user02@vivid.com","11111111111111");
//        sendMail("2281437550@qq.com","11111111111111");
//        sendMail("notifulAll@163.com","11111111111111");
    }
}



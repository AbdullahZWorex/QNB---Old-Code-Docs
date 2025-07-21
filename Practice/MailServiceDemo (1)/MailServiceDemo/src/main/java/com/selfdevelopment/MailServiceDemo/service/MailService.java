package com.selfdevelopment.MailServiceDemo.service;


import java.util.List;

public interface MailService {

    void sendSimpleMail(String to, String subject, String content);

    void sendMailWithAttachment(String to, String subject, String content, List<String> attachments);

    void sendHtmlMail(String to, String subject, String content);
}

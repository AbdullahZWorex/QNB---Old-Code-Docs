package com.selfdevelopment.MailServiceDemo.controller;

import com.selfdevelopment.MailServiceDemo.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public String sendMail(@RequestBody MailRequestDTO mailRequestDTO) {
        mailService.sendSimpleMail(mailRequestDTO.getTo(), mailRequestDTO.getSubject(), mailRequestDTO.getBody());
        return "Mail Sent Successfully!";
    }
}

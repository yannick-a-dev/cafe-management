package com.cafemanagementproject.cafemanagementproject.utils;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailUtils {

	@Autowired
	private JavaMailSender emailSender;
	
	public void sendSimpleMessage(String to,String subject,String text, String string, List<String> list) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("essola@yahoo.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		if(list!=null && list.size()> 0) 
			message.setCc(getCcArray(list));	
		emailSender.send(message);
	}
	
	private String[] getCcArray(List<String> ccList) {
		String[] cc = new String[ccList.size()];
		for(int i = 0; i < ccList.size();i++) {
			cc[i]=ccList.get(i);
		}
		return cc;
	}
	
	public void forgotMail(String to,String subject,String password)throws  MessagingException{
		MimeMessage message = emailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message,true);
	    helper.setFrom("amitkumarx31@gmail.com");
	    helper.setTo(to);
	    helper.setSubject(subject);
	    String htmlMsg = "<p><b>You login details for Cafe Management System</b><br><b>Email: </b> " + password + "<br><a href=\"http://localhost:4200/ \">Click here to login</a></p>";
        message.setContent(htmlMsg,"text/html");
        emailSender.send(message);
	}
}

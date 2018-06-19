package at.qe.sepm.skeleton.services;

import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

// Mail-Handling
@Component
@Scope("application")
public class MailService {

	// sub = subject
	// msg = message
	// to = receiving mail-address
    public static boolean sendMail(String sub, String msg, String to) {
        return sendMail(sub, msg, to, "kCareMailService@gmail.com", "smtp.gmail.com", "sepm2017");
    }

	// from = sending mail-address
	// host = smtp-url
	// passw = plain-text of password
    public static boolean sendMail(String sub, String msg, String to, String from, String host, String passw) {

        // define sender
        JavaMailSenderImpl sndr = new JavaMailSenderImpl();
        sndr.setHost(host);
        sndr.setUsername(from);
        sndr.setPassword(passw);
        sndr.setPort(587);    // 465 or 25 or 587

        // set properties
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // or false
        props.put("mail.smtps.ssl.checkserveridentity", "true");
        props.put("mail.smtps.ssl.trust", "*");
        props.put("mail.debug", "true");
        sndr.setJavaMailProperties(props);

        // set message and receiver
        SimpleMailMessage smmsg = new SimpleMailMessage();
        smmsg.setTo(to);
        smmsg.setSubject(sub);
        smmsg.setText(msg);

        try {
//            sndr.send(smmsg);
System.out.println("DEBUG: Sending mail with \"" + msg + "\" to \"" + to + "\" from \"" + from + "\" over \"" + host + "\"" );
        } catch (MailException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return true;

	}

}

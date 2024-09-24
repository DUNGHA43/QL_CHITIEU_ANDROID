package com.hatiendung.quanlitaichinh.dal;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class dal_gmailxacthuc {
    private String gmail_admin;
    private String passmail;
    private Session session;

    public dal_gmailxacthuc(String gmail_admin, String passmail) {
        this.gmail_admin = gmail_admin;
        this.passmail = passmail;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gmail_admin, passmail);
            }
        });
    }

    public void guiMail(String gmailnhan, String tieude, String noidung) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(gmail_admin));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(gmailnhan));
        message.setSubject(tieude);
        message.setText(noidung);
        Transport.send(message);
    }

    public String taoMa(){
        int random = (int) (Math.random() * 90000) + 1000;
        return String.valueOf(random);
    }

    public boolean isGmail(String gmail){
        String gmailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        return gmail.matches(gmailPattern);
    }
}

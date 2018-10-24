package ar.edu.unq.inscripcionunq.spring.model;

import java.io.File;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class Mail {

	private static Mail instance = null;
	private MultiPartEmail mail = new MultiPartEmail();
	private String file;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Mail() throws EmailException {
		mailConfiguration();

	}

	private void mailConfiguration() throws EmailException {
		mail.setHostName("smtp.gmail.com");
		// mail.setTLS(true);
		mail.setStartTLSEnabled(true);
		mail.setSmtpPort(587);
		// mail.setSSL(true);
		mail.setSSLOnConnect(true);
		mail.setAuthentication("MorfiYa2017@gmail.com", "2017Morf");
		mail.setFrom("MorfiYa2017@gmail.com");
	}

	public Mail(MultiPartEmail email) throws EmailException {
		this.mail = email;
		mailConfiguration();

	}

	public Mail getInstance() throws EmailException {
		if (instance == null) {
			instance = new Mail();
		}
		return instance;
	}

	public void send(String to, String subject, String mensagge) throws EmailException {
       if(file != null) {
    	   File fileScreenshot = new File(file);
	       EmailAttachment attachment = new EmailAttachment();
	   	   attachment.setPath(fileScreenshot.getPath());
	   	   attachment.setDisposition(EmailAttachment.ATTACHMENT);
	   	   attachment.setDescription("Attachment");
	   	   attachment.setName(fileScreenshot.getName());

   	      mail.attach(attachment);   
       }
		mail.addTo(to);
		mail.setSubject(subject);
		mail.setMsg(mensagge);
		mail.send();
	}
	
	
}

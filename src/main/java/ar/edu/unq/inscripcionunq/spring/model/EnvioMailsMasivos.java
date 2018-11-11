package ar.edu.unq.inscripcionunq.spring.model;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.EmailException;

public class EnvioMailsMasivos extends Thread {
	
	private List<String> emails;
	private Email email;
	private String asunto;
	private String mensaje;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public void run() {
		try {
			this.envio();
		} catch (EmailException | InterruptedException e) {
			// notificar al log
		}
	}

	private void envio() throws EmailException, InterruptedException {
		email = new Email();
		for (int i = 0; i < emails.size(); i = i + 50) {
			List<String> emailsSend = emails.subList(i, emails.size() - i < 50 ? emails.size() - i : 50);
			email.sendMasivo(emailsSend, asunto, mensaje);
			TimeUnit.MINUTES.sleep(2);
		}
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
}
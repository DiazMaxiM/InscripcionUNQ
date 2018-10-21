package ar.edu.unq.inscripcionunq.spring.model;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.persistence.Column;
import javax.persistence.Entity;

import ar.edu.unq.inscripcionunq.spring.exception.EncryptionDecryptionAESException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;

@Entity(name = "Usuario")
public class Usuario extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	private String email; 
	private String password;
	private SecretKey clave;
	
	public Usuario() {
		super();
	}
	
	public Usuario(String email, String password){
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void validarPassword(String password) throws PasswordInvalidoException, EncryptionDecryptionAESException {
		this.decodificarPassword();
		if (!this.password.equals(password)) {
			throw new PasswordInvalidoException();
		};
	}
	
	public void codificarPassword() throws EncryptionDecryptionAESException {
		try {
			String passwordOriginal = this.password;
			this.clave = EncryptionDecryptionAES.generarClave();
			this.password = EncryptionDecryptionAES.encrypt(passwordOriginal, this.clave);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			throw new EncryptionDecryptionAESException();
		}
	}
	
	public void decodificarPassword() throws EncryptionDecryptionAESException {
		try {
			this.password = EncryptionDecryptionAES.decrypt(this.password, this.clave);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			throw new EncryptionDecryptionAESException();
		}
	}
}

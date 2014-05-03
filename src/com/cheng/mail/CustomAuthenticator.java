package com.cheng.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * ������֤
 * 
 * @author chengkai
 * 
 */
public class CustomAuthenticator extends Authenticator {
	String userName = null;
	String password = null;

	public CustomAuthenticator() {
	}

	public CustomAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}

}

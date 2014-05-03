package com.cheng.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * �ʼ�������
 * 
 * @author Administrator
 * 
 */
public class MailSender {
	private static Properties props = new Properties();
	private static boolean validate = true;
	static {
		props.put("mail.smtp.host", "smtp.qq.com");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", validate ? validate + "" : !validate + "");
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap
				.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);
	}

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		MailInfo mailInfo = new MailInfo();
		mailInfo.setUserName("1094226429@qq.com");
		mailInfo.setPassword("***");
		mailInfo.setFromAddress("1094226429@qq.com");
		mailInfo.setToAddress("chengkaizone@163.com");
		mailInfo.setSubject("������Ϣ");
		mailInfo.setContent("�ǺǺǺ�");
		mailInfo.setAttachFilePaths(new String[] { "c:\\temp\\HelloWorld.h" });
		// �������Ҫ�������ʼ�

		MailSender sms = new MailSender();
		boolean bool = sms.sendAttachMail(mailInfo);
		if (bool) {
			System.out.println("���ͳɹ�");
		} else {
			System.out.println("����ʧ��");
		}
	}

	/**
	 * �����ʼ�������
	 * 
	 * @param smtp
	 */
	public void setSmtpHost(String smtp) {
		props.put("mail.smtp.host", smtp);
	}

	/**
	 * �����ʼ��˿�
	 * 
	 * @param port
	 */
	public void setSmtpPort(String port) {
		props.put("mail.smtp.port", port);
	}

	/**
	 * �����Ƿ���Ҫ��֤
	 * 
	 * @param validate
	 */
	public void setValidate(boolean validate) {
		props.put("mail.smtp.auth", validate ? validate + "" : !validate + "");
	}

	/**
	 * ���ı���ʽ�����ʼ�
	 * 
	 * @param mailInfo
	 *            �����͵��ʼ�����Ϣ
	 */
	public boolean sendTextMail(MailInfo mailInfo) {
		// �ж��Ƿ���Ҫ�����֤
		CustomAuthenticator authenticator = null;
		if (validate) {
			// �����Ҫ�����֤���򴴽�һ��������֤��
			authenticator = new CustomAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
		Session sendMailSession = Session.getDefaultInstance(props,
				authenticator);
		try {
			// ����session����һ���ʼ���Ϣ
			Message mailMessage = new MimeMessage(sendMailSession);
			// �����ʼ������ߵ�ַ
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// �����ʼ���Ϣ�ķ�����
			mailMessage.setFrom(from);
			// �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// �����ʼ���Ϣ������
			mailMessage.setSubject(mailInfo.getSubject());
			// �����ʼ���Ϣ���͵�ʱ��
			mailMessage.setSentDate(new Date());
			// �����ʼ���Ϣ����Ҫ����
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// �����ʼ�
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/** */
	/**
	 * ��HTML��ʽ�����ʼ�
	 * 
	 * @param mailInfo
	 *            �����͵��ʼ���Ϣ
	 */
	public static boolean sendHtmlMail(MailInfo mailInfo) {
		// �ж��Ƿ���Ҫ�����֤
		CustomAuthenticator authenticator = null;
		// �����Ҫ�����֤���򴴽�һ��������֤��
		if (validate) {
			authenticator = new CustomAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
		Session sendMailSession = Session.getDefaultInstance(props,
				authenticator);
		try {
			// ����session����һ���ʼ���Ϣ
			Message mailMessage = new MimeMessage(sendMailSession);
			// �����ʼ������ߵ�ַ
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// �����ʼ���Ϣ�ķ�����
			mailMessage.setFrom(from);
			// �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��
			Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO���Ա�ʾ�����ߵ�����ΪTO
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// �����ʼ���Ϣ������
			mailMessage.setSubject(mailInfo.getSubject());
			// �����ʼ���Ϣ���͵�ʱ��
			mailMessage.setSentDate(new Date());
			// MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���
			Multipart mainPart = new MimeMultipart();
			// ����һ������HTML���ݵ�MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// ����HTML����
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// ��MiniMultipart��������Ϊ�ʼ�����
			mailMessage.setContent(mainPart);
			// �����ʼ�
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * �������ķ����ʼ�
	 * 
	 * @param info
	 */
	public boolean sendAttachMail(MailInfo mailInfo) {
		// �ж��Ƿ���Ҫ�����֤
		CustomAuthenticator authenticator = null;
		// �����Ҫ�����֤���򴴽�һ��������֤��
		if (validate) {
			authenticator = new CustomAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
		Session sendMailSession = Session.getDefaultInstance(props,
				authenticator);
		try {
			// ����session����һ���ʼ���Ϣ
			Message mailMessage = new MimeMessage(sendMailSession);
			// �����ʼ������ߵ�ַ
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// �����ʼ���Ϣ�ķ�����
			mailMessage.setFrom(from);
			// �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��
			Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO���Ա�ʾ�����ߵ�����ΪTO
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// �����ʼ���Ϣ������
			mailMessage.setSubject(mailInfo.getSubject());
			// �����ʼ���Ϣ���͵�ʱ��
			mailMessage.setSentDate(new Date());
			// MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���
			Multipart mainPart = new MimeMultipart();
			// ����һ������HTML���ݵ�MimeBodyPart
			BodyPart body = new MimeBodyPart();
			// ����HTML����
			body.setContent(mailInfo.getContent(), "text/*; charset=utf-8");
			mainPart.addBodyPart(body);
			String[] paths = mailInfo.getAttachFilePaths();
			for (String path : paths) {
				// ��Ӹ���
				body = new MimeBodyPart();
				FileDataSource source = new FileDataSource(path);
				body.setDataHandler(new DataHandler(source));
				body.setFileName(javax.mail.internet.MimeUtility
						.encodeWord(path)); // ���ĸ������ȱ���
				mainPart.addBodyPart(body);
			}
			// ��MiniMultipart��������Ϊ�ʼ�����
			mailMessage.setContent(mainPart);
			// �����ʼ�
			Transport.send(mailMessage);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}

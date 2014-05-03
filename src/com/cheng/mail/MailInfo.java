package com.cheng.mail;

/**
 * �ʼ���Ϣ---�ɴ�����
 * 
 * @author chengkai
 * 
 */
public class MailInfo {
	// �ʼ������ߵĵ�ַ
	private String fromAddress;
	// �ʼ������ߵĵ�ַ
	private String toAddress;
	// ��½�ʼ����ͷ��������û���������
	private String userName;
	private String password;
	// �ʼ�����
	private String subject;
	// �ʼ����ı�����
	private String content;
	// �ʼ��������ļ�·��
	private String[] attachFilePaths;

	public String[] getAttachFilePaths() {
		return attachFilePaths;
	}

	public void setAttachFilePaths(String[] attachFilePaths) {
		this.attachFilePaths = attachFilePaths;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}

}

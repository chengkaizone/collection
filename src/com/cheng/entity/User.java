package com.cheng.entity;

public class User implements java.io.Serializable {
	private static final long serialVersionUID = 5594526898136461812L;
	/**
	 * �û������ݿ��е�id
	 */
	private Integer id;
	/**
	 * ��¼�û�������
	 */
	private String userName;
	/**
	 * ��¼�û�������
	 */
	private String password;
	/**
	 * ��¼�û���ʱ��
	 */
	private String loginTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

}

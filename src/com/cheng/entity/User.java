package com.cheng.entity;

public class User implements java.io.Serializable {
	private static final long serialVersionUID = 5594526898136461812L;
	/**
	 * 用户在数据库中的id
	 */
	private Integer id;
	/**
	 * 登录用户的姓名
	 */
	private String userName;
	/**
	 * 登录用户的密码
	 */
	private String password;
	/**
	 * 登录用户的时间
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

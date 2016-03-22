package com.mumudev.timemanager.beans;

/**
 * 携带有登陆数据
 */

public class LoginInfo {
	// 账号
	private String account;
	// 密码
	private String password;
	//手机IMEI码（手机的唯一标记码）
	private String imei;
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

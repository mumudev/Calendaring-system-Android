package com.mumudev.timemanager.beans;

/**
 * 登陆后返回的数据
 */

public class LoginResult {
	/**
	 *个性签名
	 */
	private String signature;
	/**
	 *头像URL
	 */
	private String protraitURL;
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getProtraitURL() {
		return protraitURL;
	}
	public void setProtraitURL(String protraitURL) {
		this.protraitURL = protraitURL;
	}
	
}

package com.mumudev.timemanager.beans;

public class ResultDataInfo<T> {
	/**
	 * 返回信息
	 */
	private String retMsg;
	/**
	 * 返回码
	 */
	private int code;
	/**
	 * 返回数据
	 */
	private T data;

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public ResultDataInfo() {

	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultDataInfo [retMsg:" + retMsg + ", code:" + code + ", data:" + data + "]";
	}
}

package com.qiugaoyang.qgyblog.common.tools;

import java.io.Serializable;

public class JsonResult<T> implements Serializable {
	private static final long serialVersionUID = -4699713095477151086L;
	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 信息
	 */
	private String key;
	/**
	 * 信息
	 */
	private String message;
	/**
	 * 数据
	 */
	private String token;
	/**
	 * 数据
	 */
	private T data;

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public JsonResult() {
		super();
	}

	public JsonResult(T data, String message, boolean success) {
		this.data = data;
		this.message = message;
		this.success = success;
	}

	public JsonResult(T data, String message) {
		this.data = data;
		this.message = message;
		this.success = true;
	}

	public JsonResult(T data) {
		this.data = data;
		this.success = true;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}

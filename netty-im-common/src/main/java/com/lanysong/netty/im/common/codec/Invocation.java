package com.lanysong.netty.im.common.codec;

import com.alibaba.fastjson.JSON;
import com.lanysong.netty.im.common.dispatcher.Message;

/**
 * 通信协议的消息体
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-07-10 15:17
 */
public class Invocation {
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 消息，JSON 格式
	 */
	private String message;
	
	// 空构造方法
	public Invocation() {
	}
	
	public Invocation(String type, String message) {
		this.type = type;
		this.message = message;
	}
	
	public Invocation(String type, Message message) {
		this.type = type;
		this.message = JSON.toJSONString(message);
	}
	
	public String getType() {
		return type;
	}
	
	public Invocation setType(String type) {
		this.type = type;
		return this;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Invocation setMessage(String message) {
		this.message = message;
		return this;
	}
	
	@Override
	public String toString() {
		return "Invocation{" +
				"type='" + type + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}

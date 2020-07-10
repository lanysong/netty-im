package com.lanysong.netty.im.common.dispatcher;

import com.alibaba.fastjson.JSON;
import com.lanysong.netty.im.common.codec.Invocation;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-07-10 15:15
 */
@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<Invocation> {
	
	@Autowired
	private MessageHandlerContainer messageHandlerContainer;
	
	private final ExecutorService executor =  Executors.newFixedThreadPool(200);
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, Invocation invocation) throws Exception {
		// 获得 type 对应的 MessageHandler 处理器
		MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(invocation.getType());
		
		// 获得  MessageHandler 处理器 的消息类
		Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);
		
		// 解析消息
		Message message = JSON.parseObject(invocation.getMessage(), messageClass);
		// 执行逻辑
		executor.submit(new Runnable() {
			@Override
			public void run() {
				messageHandler.execute(channelHandlerContext.channel(),message);
			}
		});
		
	}
}

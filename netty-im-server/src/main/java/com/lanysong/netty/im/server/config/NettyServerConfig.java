package com.lanysong.netty.im.server.config;

import com.lanysong.netty.im.common.dispatcher.MessageDispatcher;
import com.lanysong.netty.im.common.dispatcher.MessageHandlerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-07-10 15:11
 */
@Configuration
public class NettyServerConfig {
	@Bean
	public MessageDispatcher messageDispatcher() {
		return new MessageDispatcher();
	}
	
	@Bean
	public MessageHandlerContainer messageHandlerContainer() {
		return new MessageHandlerContainer();
	}
}

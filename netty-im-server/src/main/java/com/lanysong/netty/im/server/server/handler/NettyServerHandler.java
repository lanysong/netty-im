package com.lanysong.netty.im.server.server.handler;

import com.lanysong.netty.im.server.server.NettyChannelManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 服务端 Channel 实现类，提供对客户端 Channel 建立连接、断开连接、异常时的处理
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-07-10 17:02
 */
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private NettyChannelManager nettyChannelManager;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 从管理器中添加
		nettyChannelManager.add(ctx.channel());
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		// 从管理器中移除
		nettyChannelManager.remove(ctx.channel());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("[exceptionCaught][连接({}) 发生异常]", ctx.channel().id(), cause);
		// 断开连接
		ctx.channel().close();
	}
}

package com.lanysong.netty.im.server.server;

import com.lanysong.netty.im.server.server.handler.NettyServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * netty 服务端
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-07-10 17:18
 */
@Component
public class NettyServer {private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${netty.port}")
	private Integer port;
	
	@Autowired
	private NettyServerHandlerInitializer nettyServerHandlerInitializer;
	
	/**
	 * boss 线程组，用于服务端接受客户端的连接
	 */
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	
	/**
	 * worker 线程组，用于服务端接受客户端的数据读写
	 */
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	
	
	/**
	 * Netty Server Channel
	 */
	private Channel channel;
	
	/**
	 * 启动 Netty Server
	 */
	@PostConstruct
	public void start() throws InterruptedException {
		// 创建 ServerBootstrap 对象，用于 Netty Server 启动
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		// 设置 ServerBootstrap 的各种属性
		// 设置两个 EventLoopGroup 对象
		serverBootstrap.group(bossGroup, workerGroup)
				// 指定 Channel 为服务端 NioServerSocketChannel
				.channel(NioServerSocketChannel.class)
				// 设置 Netty Server 的端口
				.localAddress(new InetSocketAddress(port))
				// 服务端 accept 队列的大小
				.option(ChannelOption.SO_BACKLOG,1024)
				// TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				// 允许较小的数据包的发送，降低延迟
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childHandler(nettyServerHandlerInitializer);
		
		ChannelFuture channelFuture = serverBootstrap.bind().sync();
		if (channelFuture.isSuccess()){
			channel = channelFuture.channel();
			logger.info("[start][Netty Server 启动在 {} 端口]", port);
		}
		
	}
	
	
	/**
	 * 关闭 Netty Server
	 */
	@PreDestroy
	public void shutdown() {
		// 关闭 Netty Server
		if (channel != null) {
			channel.close();
		}
		// 优雅关闭两个 EventLoopGroup 对象
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
	
}

package com.lanysong.netty.im.client.messagehandler.auth;

import com.lanysong.netty.im.client.message.auth.AuthResponse;
import com.lanysong.netty.im.common.dispatcher.MessageHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthResponseHandler implements MessageHandler<AuthResponse> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(Channel channel, AuthResponse message) {
        logger.info("[execute][认证结果：{}]", message);
    }

    @Override
    public String getType() {
        return AuthResponse.TYPE;
    }

}

package com.lanysong.netty.im.client.message.heartbeat;

import com.lanysong.netty.im.common.dispatcher.Message;

/**
 * 消息 - 心跳响应
 */
public class HeartbeatResponse implements Message {

    /**
     * 类型 - 心跳响应
     */
    public static final String TYPE = "HEARTBEAT_RESPONSE";

    @Override
    public String toString() {
        return "HeartbeatResponse{}";
    }

}

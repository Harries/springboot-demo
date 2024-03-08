package com.et.netty.service;

import com.et.netty.config.NettyConfig;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;


import java.util.Objects;

/**
 * @author dongliang7
 * @projectName websocket-parent
 * @ClassName PushMsgServiceImpl.java
 * @description: 推送消息实现类
 * @createTime 2023年02月06日 16:45:00
 */
@Service
public class PushMsgServiceImpl implements PushMsgService {

    @Override
    public void pushMsgToOne(String userId, String msg) {
        Channel channel = NettyConfig.getChannel(userId);
        if (Objects.isNull(channel)) {
            throw new RuntimeException("未连接socket服务器");
        }

        channel.writeAndFlush(new TextWebSocketFrame(msg));
    }

    @Override
    public void pushMsgToAll(String msg) {
        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg));
    }
}
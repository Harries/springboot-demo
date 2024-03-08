package com.et.netty.service;

/**
 * @author dongliang7
 * @projectName websocket-parent
 * @ClassName PushMsgService.java
 * @description: 推送消息接口
 * @createTime 2023年02月06日 16:44:00
 */
public interface PushMsgService {
    /**
     * 推送给指定用户
     */
    void pushMsgToOne(String userId, String msg);

    /**
     * 推送给所有用户
     */
    void pushMsgToAll(String msg);
}
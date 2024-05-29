package com.et.sse.controller;

import cn.hutool.core.util.IdUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@RequestMapping("/chat")
public class ChatController {

    Map<String, String> msgMap = new ConcurrentHashMap<>();

    /**
     * send meaaage
     * @param msg
     * @return
     */
    @ResponseBody
    @PostMapping("/sendMsg")
    public String sendMsg(String msg) {
        String msgId = IdUtil.simpleUUID();
        msgMap.put(msgId, msg);
        return msgId;
    }

    /**
     * conversation
     * @param msgId mapper with sendmsg
     * @return
     */
    @GetMapping(value = "/conversation/{msgId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter conversation(@PathVariable("msgId") String msgId) {
        SseEmitter emitter = new SseEmitter();
        String msg = msgMap.remove(msgId);

        //mock chatgpt response
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    ChatMessage  chatMessage =  new ChatMessage("test", new String(i+""));
                    emitter.send(chatMessage);
                    Thread.sleep(1000);
                }
                emitter.send(SseEmitter.event().name("stop").data(""));
                emitter.complete(); // close connection
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e); // error finish
            }
        }).start();

        return emitter;
    }
}

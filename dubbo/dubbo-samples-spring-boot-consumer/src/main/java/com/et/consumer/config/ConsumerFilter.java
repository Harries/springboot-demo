package com.et.consumer.config;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
@Slf4j
@Activate(group = { Constants.CONSUMER })
public class ConsumerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Object token = MDC.get("TRACKING_LOG_SESSION_TOKEN_ID");
        if (token != null) {
            log.info("----cunsumer trace id:{}",token);
            RpcContext.getContext().setAttachment("TRACKING_LOG_SESSION_TOKEN_ID", token.toString());
        }
        Result result = invoker.invoke(invocation);
        return result;
    }
}

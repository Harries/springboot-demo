package com.et.provider.config;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
@Activate(group = { Constants.PROVIDER })
public class ProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

       String var = RpcContext.getContext().getAttachment("TRACKING_LOG_SESSION_TOKEN_ID");
        MDC.put("TRACKING_LOG_SESSION_TOKEN_ID", var);
        log.info("----->provider trance id:{}",var);
        Result result = invoker.invoke(invocation);
        MDC.remove("TRACKING_LOG_SESSION_TOKEN_ID");

        return result;
    }
}

package com.networkerr.core.http;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.lang.reflect.Method;

public class Handler extends AnnotationHandlerUtils {
    protected Handler() {
        super();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        this.initialize(msg, ctx);
        this.validateContext();
        try {
            DerivedEndpoint derivedEndpoint = this.deriveEndpoint();
            Object derivedHandler = deriveHandler(derivedEndpoint);
            Method derivedMethod = deriveMethod(derivedHandler, derivedEndpoint);
            derivedMethod.invoke(derivedHandler, ctx);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

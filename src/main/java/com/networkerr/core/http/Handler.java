package com.networkerr.core.http;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.lang.reflect.Method;

/**
 * Handler is the main class to process incoming requests. Extend this class to get annotation scanner functionality
 */
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

    protected FullHttpResponse respond(ChannelHandlerContext ctx, String outgoing) {
        ByteBuf responseBytes = ctx.alloc().buffer();
        responseBytes.writeBytes(outgoing.getBytes());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.ACCEPTED, responseBytes);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        return response;
    }
}

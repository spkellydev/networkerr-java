package com.networkerr.core.http;
import com.networkerr.core.dao.Model;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

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
            derivedMethod.invoke(derivedHandler, ctx, msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Response with JSON content
     * @param ctx current channel context from Netty
     * @param outgoing string to send to client
     * @return FullHttpResponse with JSON headers
     */
    protected FullHttpResponse respond(ChannelHandlerContext ctx, String outgoing) {
        ByteBuf responseBytes = ctx.alloc().buffer();
        responseBytes.writeBytes(outgoing.getBytes());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.ACCEPTED, responseBytes);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        return response;
    }

    protected String decodeMsg(FullHttpRequest msg) {
        return msg.content().toString(CharsetUtil.UTF_8);
    }
}

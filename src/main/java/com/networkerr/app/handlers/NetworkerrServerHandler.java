package com.networkerr.app.handlers;

import com.networkerr.core.annotations.HttpEndpoint;
import com.networkerr.core.http.AnnotationHandlerUtils;
import com.networkerr.core.http.Handler;
import com.networkerr.core.routers.Router;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

public class NetworkerrServerHandler extends Handler {
    private Router router;

    public NetworkerrServerHandler() {
        super();
    }

    @HttpEndpoint(route = "/api", method = "GET", statusCode = 200)
    public void getThis(ChannelHandlerContext ctx) {
        ByteBuf responseBytes = ctx.alloc().buffer();
        responseBytes.writeBytes("Hello World".getBytes());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.ACCEPTED, responseBytes);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        ctx.writeAndFlush(response);
    }
}

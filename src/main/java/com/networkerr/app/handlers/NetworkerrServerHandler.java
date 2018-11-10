package com.networkerr.app.handlers;

import com.networkerr.core.annotations.HttpEndpoint;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

public class NetworkerrServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private String route;
    private String method;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("Server hit");
        getThis(ctx);
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

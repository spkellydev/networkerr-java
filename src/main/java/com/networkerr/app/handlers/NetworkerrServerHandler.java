package com.networkerr.app.handlers;

import com.networkerr.core.annotations.HttpEndpoint;
import com.networkerr.core.http.Handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

public class NetworkerrServerHandler extends Handler {
    @HttpEndpoint(route = "/api", method = "GET", statusCode = 200)
    public void index(ChannelHandlerContext ctx, FullHttpRequest msg) {
        FullHttpResponse response = this.respond(ctx, "{ \"status\": \"success\" }");
        ctx.writeAndFlush(response);
    }

    @HttpEndpoint(route = "/api/data", method = "POST", statusCode = 200)
    public void postData(ChannelHandlerContext ctx, FullHttpRequest msg) {
        String message = this.decodeMsg(msg);
        FullHttpResponse response = this.respond(ctx, "{ \"status\": \"data\" }");
        ctx.writeAndFlush(response);
    }
}

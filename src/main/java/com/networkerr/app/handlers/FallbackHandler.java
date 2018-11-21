package com.networkerr.app.handlers;

import com.networkerr.core.annotations.HttpEndpoint;
import com.networkerr.core.http.Handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

public class FallbackHandler extends Handler {
    @HttpEndpoint(route = "/404", method = "GET", statusCode = 404)
    public void handle404(ChannelHandlerContext ctx, FullHttpRequest msg) {
        ctx.writeAndFlush(this.respond(ctx, "404"));
    }

    @HttpEndpoint(route = "/403", method = "GET", statusCode = 404)
    public void handle403(ChannelHandlerContext ctx, FullHttpRequest msg) {
        ctx.writeAndFlush(this.respond(ctx, "403"));
    }
}

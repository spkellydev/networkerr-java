package com.networkerr.app.handlers;

import com.networkerr.core.annotations.HttpEndpoint;
import com.networkerr.core.http.Handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class UptimeHandler extends Handler {
    @HttpEndpoint(route = "/uptime", method = "GET", statusCode = 200)
    public void monitorUptime(ChannelHandlerContext ctx, FullHttpRequest msg) {
        FullHttpResponse response = this.respond(ctx, "up");
        ctx.writeAndFlush(response);
    }
}

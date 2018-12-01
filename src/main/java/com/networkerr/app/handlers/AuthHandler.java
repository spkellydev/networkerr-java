package com.networkerr.app.handlers;

import com.networkerr.app.models.UserModel;
import com.networkerr.core.annotations.HttpEndpoint;
import com.networkerr.core.http.Handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class AuthHandler extends Handler {
    @HttpEndpoint(route = "/register", method = "POST", statusCode = 204)
    public void handleLogin(ChannelHandlerContext ctx, FullHttpRequest msg) {
        UserModel user = new UserModel(msg);
        user.save(user.schema());

        FullHttpResponse response = this.respond(ctx, user.writeAsString());
        ctx.writeAndFlush(response);
    }
}

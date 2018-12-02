package com.networkerr.app.handlers;

import com.networkerr.app.models.UserModel;
import com.networkerr.app.models.UserSchema;
import com.networkerr.core.annotations.HttpEndpoint;
import com.networkerr.core.crypto.PasswordUtils;
import com.networkerr.core.http.Handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.Optional;

public class AuthHandler extends Handler {
    @HttpEndpoint(route = "/register", method = "POST", statusCode = 204)
    public void handleRegisterUser(ChannelHandlerContext ctx, FullHttpRequest msg) {
        String salt = PasswordUtils.getSalt(30);
        UserModel user = new UserModel(msg);
        user.schema().setSalt(salt);
        String encPw = PasswordUtils.generateSecurePassword(user.schema().getPassword(), salt);
        user.schema().setPassword(encPw);
        user.save(user.schema());

        FullHttpResponse response = this.respond(ctx, user.writeAsString());
        ctx.writeAndFlush(response);
    }


    @HttpEndpoint(route = "/login", method = "POST", statusCode = 204)
    public void handleLoginUser(ChannelHandlerContext ctx, FullHttpRequest msg) {
        UserModel user = new UserModel(msg);
        final String providedPw = user.schema().getPassword();
        final String providedEmail = user.schema().getEmail();
        String result;
        Optional<UserSchema> foundUser = user.get(providedEmail);
        if(foundUser.isPresent()) {
            user.setUserSchema(foundUser.get());
            final String encPw = user.schema().getPassword();
            boolean match = PasswordUtils.verifyUserPassword(providedPw, encPw, user.schema().getSalt());
            if(match) {
                result = "user found";
            } else {
                result = "password not matching";
            }
        } else {
            result = "trouble finding user";
        }
        
        FullHttpResponse response = this.respond(ctx, result);
        ctx.writeAndFlush(response);
    }
}

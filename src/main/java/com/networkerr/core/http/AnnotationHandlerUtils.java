package com.networkerr.core.http;
import com.networkerr.core.routers.Router;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpRequest;
import java.lang.reflect.Method;

public abstract class AnnotationHandlerUtils extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final Router router = new Router();
    private FullHttpRequest msg;
    private ChannelHandlerContext ctx;

    protected void initialize(FullHttpRequest msg, ChannelHandlerContext ctx) {
        this.msg = msg;
        this.ctx = ctx;
    }

    protected void validateContext() {
        if(this.msg.decoderResult() != DecoderResult.SUCCESS) {
            this.ctx.close();
        }
    }

    protected Method deriveMethod(Object handler, DerivedEndpoint endpoint) {
        Method method = null;
        try {
            method = (handler.getClass()).getMethod(endpoint.getHandlerMethod(), ChannelHandlerContext.class);
        } catch (NoSuchMethodException e) {
            System.out.println("Could not find method");
            System.out.println(e.getMessage());
        }
        return method;
    }

    protected Object deriveHandler(DerivedEndpoint endpoint) {
        Class<?> c = null;
        Object derivedHandler = null;
        try {
            System.out.println(endpoint.getClazz());
            c = Class.forName(endpoint.getClazz());
            derivedHandler = c.newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found in classpath");
            System.out.println(e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("Cannot instantiate class, is there a missing constructor?");
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        return derivedHandler;
    }

    protected DerivedEndpoint deriveEndpoint() {
        return this.router.getRoute(this.msg.uri(), this.msg.method().toString());
    }
}

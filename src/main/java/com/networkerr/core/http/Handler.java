package com.networkerr.core.http;
import com.networkerr.core.routers.Router;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Handler {
    private Router router;
    private FullHttpRequest msg;
    private ChannelHandlerContext ctx;

    public Handler(Router router, FullHttpRequest msg, ChannelHandlerContext ctx) {
        this.router = router;
        this.msg = msg;
        this.ctx = ctx;
    }

    public void handleIncomingRequest() {
        try {
            DerivedEndpoint derivedEndpoint = this.deriveEndpoint();
            Object derivedHandler = deriveHandler(derivedEndpoint);
            Method derivedMethod = deriveMethod(derivedHandler, derivedEndpoint);
            derivedMethod.invoke(derivedHandler, this.ctx);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Method deriveMethod(Object handler, DerivedEndpoint endpoint) {
        Method method = null;
        try {
            method = (handler.getClass()).getMethod(endpoint.getHandlerMethod(), ChannelHandlerContext.class);
        } catch (NoSuchMethodException e) {
            System.out.println("Could not find method");
            System.out.println(e.getMessage());
        }
        return method;
    }

    private Object deriveHandler(DerivedEndpoint endpoint) {
        Class<?> c = null;
        Object derivedHandler = null;
        try {
            c = Class.forName(endpoint.getClazz());
            derivedHandler = c.getConstructor(Router.class).newInstance(router);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found in classpath");
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println("No such method found on derived handler");
            System.out.println(e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("Cannot instantiate class, is there a missing constructor?");
            System.out.println(e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }
        return derivedHandler;
    }

    private DerivedEndpoint deriveEndpoint() {
        return this.router.getRoute(this.msg.uri(), this.msg.method().toString());
    }
}

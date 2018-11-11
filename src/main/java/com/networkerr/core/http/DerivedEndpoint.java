package com.networkerr.core.http;

public class DerivedEndpoint {
    String route = null;
    String method = null;
    int statusCode = 0;
    String clazz = null;
    String handlerMethod = null;

    public DerivedEndpoint(){}

    public DerivedEndpoint(String route, String method, int statusCode, String clazz, String handlerMethod) {
        this.route = route;
        this.method = method;
        this.statusCode = statusCode;
        this.clazz = clazz;
        this.handlerMethod = handlerMethod;
    }

    public String getRoute() {
        return this.route;
    }

    public String toString() {
        return this.route + " " + this.method + " " + this.statusCode + " " + this.clazz + " " + this.handlerMethod;
    }

    public String getMethod() {
        return this.method;
    }

    public int getCode() {
        return this.statusCode;
    }

    public String getClazz() {
        return this.clazz.replace("class ", "");
    }

    public String getHandlerMethod() {
        return this.handlerMethod;
    }
}

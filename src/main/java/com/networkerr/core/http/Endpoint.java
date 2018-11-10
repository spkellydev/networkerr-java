package com.networkerr.core.http;

public class Endpoint {
    String route = null;
    String method = null;
    int statusCode = 0;
    String clazz = null;
    String m = null;

    public Endpoint(){}

    public Endpoint(String route, String method, int statusCode, String clazz, String m) {
        this.route = route;
        this.method = method;
        this.statusCode = statusCode;
        this.clazz = clazz;
        this.m = m;
    }

    public String getRoute() {
        return this.route;
    }

    public String toString() {
        return this.route + " " + this.method + " " + this.statusCode + " " + this.clazz + " " + this.m;
    }

    public String getMethod() {
        return this.method;
    }
}

package com.networkerr.core.routers;

import com.networkerr.core.http.DerivedEndpoint;

public class Router extends AnnotationRouter {
    private static final Router instance = new Router();
    private Router() {
        this.getMapFromMethods();
    }

    public DerivedEndpoint getRoute(String route, String method) {
        return this.queryEndpoints(route, method);
    }

    public static Router getInstance() {
        return instance;
    }

    public void initialize() {
        this.getMapFromMethods();
    }
}

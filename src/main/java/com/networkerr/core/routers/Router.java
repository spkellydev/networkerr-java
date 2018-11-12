package com.networkerr.core.routers;

import com.networkerr.core.http.DerivedEndpoint;

public class Router extends AnnotationRouter {
    public Router() {
        this.scanAnnotations().getRouteMapFromMethods();
    }

    public DerivedEndpoint getRoute(String route, String method) {
        return this.queryEndpoints(route, method);
    }
}

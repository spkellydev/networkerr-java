package com.networkerr.core.routers;

import com.networkerr.core.http.Endpoint;
import com.networkerr.core.http.FallbackEndpoint;

public class Router extends AnnotationRouter {

    public Router() {
        this.findAnnotation().getRouteMapFromMethods();
    }

    public Endpoint getRoute(String route, String method) {
        return this.queryEndpoints(route, method);
    }
}

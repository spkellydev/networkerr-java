package com.networkerr.core.http;

public class FallbackEndpoint extends Endpoint {
    private int statusCode;
    private String route;
    private String method;

    public FallbackEndpoint(String $route, String $method, int $statusCode) {
        super($route, $method, $statusCode, null, null);
    }
}

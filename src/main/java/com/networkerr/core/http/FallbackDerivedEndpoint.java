package com.networkerr.core.http;

public class FallbackDerivedEndpoint extends DerivedEndpoint {
    private int statusCode;
    private String route;
    private String method;

    public FallbackDerivedEndpoint(String $route, String $method, int $statusCode) {
        super($route, $method, $statusCode, null, null);
    }
}

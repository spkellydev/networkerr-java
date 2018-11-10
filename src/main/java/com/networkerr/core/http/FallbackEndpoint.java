package com.networkerr.core.http;

public class FallbackEndpoint extends Endpoint {
    private static int statusCode;
    private static String route;
    private static String method;
    private static final FallbackEndpoint $404 = new FallbackEndpoint("/404", "GET", 404);
    private static final FallbackEndpoint $500 = new FallbackEndpoint("/500", "GET", 500);
    private static final FallbackEndpoint $302 = new FallbackEndpoint("/302", "GET", 302);

    private FallbackEndpoint(String $route, String $method, int $statusCode) {
        route = $route;
        method = $method;
        statusCode = $statusCode;
    }

    public static FallbackEndpoint getInstance(int code) {
        switch (code) {
            case 404: {
                return $404;
            }
            case 500: {
                return $500;
            }
            default: {
                return $302;
            }
        }
    }
}

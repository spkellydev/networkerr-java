package com.networkerr.core.http;

public class NotFoundEndpoint extends Endpoint {
    public NotFoundEndpoint() {
        super();
        this.route = "/404";
        this.method = "GET";
        this.statusCode = 404;
    }
}

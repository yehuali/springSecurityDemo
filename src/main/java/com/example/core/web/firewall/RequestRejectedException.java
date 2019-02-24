package com.example.core.web.firewall;

public class RequestRejectedException extends RuntimeException {
    public RequestRejectedException(String message) {
        super(message);
    }
}

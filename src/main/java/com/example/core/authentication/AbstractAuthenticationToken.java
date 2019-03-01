package com.example.core.authentication;

public abstract class AbstractAuthenticationToken implements Authentication {
    private boolean authenticated = false;


    private Object details;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }
}

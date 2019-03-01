package com.example.core.authentication;

public interface AuthenticationDetailsSource<C,T> {
    T buildDetails(C context);
}

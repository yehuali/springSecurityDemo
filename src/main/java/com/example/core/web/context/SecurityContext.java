package com.example.core.web.context;

import com.example.core.authentication.Authentication;

import java.io.Serializable;

public interface SecurityContext extends Serializable {

    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);
}

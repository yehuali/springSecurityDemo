package com.example.core.web.firewall;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class FirewalledResponse extends HttpServletResponseWrapper {

    public FirewalledResponse(HttpServletResponse response) {
        super(response);
    }

}

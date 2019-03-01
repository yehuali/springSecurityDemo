package com.example.core.web.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

public class WebAuthenticationDetails implements Serializable {

    private final String remoteAddress;
    private final String sessionId;

    public WebAuthenticationDetails(HttpServletRequest request) {
        this.remoteAddress = request.getRemoteAddr();

        HttpSession session = request.getSession(false);
        this.sessionId = (session != null) ? session.getId() : null;
    }

}

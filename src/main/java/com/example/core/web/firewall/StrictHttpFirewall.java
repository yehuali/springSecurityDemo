package com.example.core.web.firewall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

public class StrictHttpFirewall implements HttpFirewall {

    private Set<String> encodedUrlBlacklist = new HashSet<String>();

    private Set<String> decodedUrlBlacklist = new HashSet<String>();

    @Override
    public FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException {
        rejectedBlacklistedUrls(request);

        if (!isNormalized(request)) {
            throw new RequestRejectedException("The request was rejected because the URL was not normalized.");
        }
        String requestUri = request.getRequestURI();
        if (!containsOnlyPrintableAsciiCharacters(requestUri)) {
            throw new RequestRejectedException("The requestURI was rejected because it can only contain printable ASCII characters.");
        }
        return new FirewalledRequest(request) {
            @Override
            public void reset() {
            }
        };
    }

    @Override
    public HttpServletResponse getFirewalledResponse(HttpServletResponse response) {
        return new FirewalledResponse(response);
    }

    private static boolean containsOnlyPrintableAsciiCharacters(String uri) {
        int length = uri.length();
        for (int i = 0; i < length; i++) {
            char c = uri.charAt(i);
            if (c < '\u0020' || c > '\u007e') {
                return false;
            }
        }

        return true;
    }

    private static boolean isNormalized(HttpServletRequest request) {
        if (!isNormalized(request.getRequestURI())) {
            return false;
        }
        if (!isNormalized(request.getContextPath())) {
            return false;
        }
        if (!isNormalized(request.getServletPath())) {
            return false;
        }
        if (!isNormalized(request.getPathInfo())) {
            return false;
        }
        return true;
    }

    private static boolean isNormalized(String path) {
        if (path == null) {
            return true;
        }

        if (path.indexOf("//") > -1) {
            return false;
        }

        for (int j = path.length(); j > 0;) {
            int i = path.lastIndexOf('/', j - 1);
            int gap = j - i;

            if (gap == 2 && path.charAt(i + 1) == '.') {
                // ".", "/./" or "/."
                return false;
            } else if (gap == 3 && path.charAt(i + 1) == '.' && path.charAt(i + 2) == '.') {
                return false;
            }

            j = i;
        }

        return true;
    }


    private void rejectedBlacklistedUrls(HttpServletRequest request) {
        for (String forbidden : this.encodedUrlBlacklist) {
            if (encodedUrlContains(request, forbidden)) {
                throw new RequestRejectedException("The request was rejected because the URL contained a potentially malicious String \"" + forbidden + "\"");
            }
        }
        for (String forbidden : this.decodedUrlBlacklist) {
            if (decodedUrlContains(request, forbidden)) {
                throw new RequestRejectedException("The request was rejected because the URL contained a potentially malicious String \"" + forbidden + "\"");
            }
        }
    }

    private static boolean encodedUrlContains(HttpServletRequest request, String value) {
        if (valueContains(request.getContextPath(), value)) {
            return true;
        }
        return valueContains(request.getRequestURI(), value);
    }

    private static boolean decodedUrlContains(HttpServletRequest request, String value) {
        if (valueContains(request.getServletPath(), value)) {
            return true;
        }
        if (valueContains(request.getPathInfo(), value)) {
            return true;
        }
        return false;
    }

    private static boolean valueContains(String value, String contains) {
        return value != null && value.contains(contains);
    }
}

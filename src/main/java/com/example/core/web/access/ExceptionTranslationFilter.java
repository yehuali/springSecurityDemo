package com.example.core.web.access;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 *  http://blog.didispace.com/springbootexception/
 *  springboot基本处理：BasicErrorController
 */
public class ExceptionTranslationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
            chain.doFilter(request, response);
            logger.debug("Chain processed normally");
        }
        catch (IOException ex) {
            throw ex;
        }
        catch (Exception ex) {
            System.out.println("捕获到异常："+ ex.getMessage());
            response.addHeader("WWW-Authenticate", "Basic realm=\" realName \"");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    ex.getMessage());


        }
    }

    private void handleSpringSecurityException(HttpServletRequest request,
                                               HttpServletResponse response, FilterChain chain, RuntimeException exception)
            throws IOException, ServletException {

    }
}

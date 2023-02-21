package com.meiken.springbootmeiknefilter.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author glf
 * @Date 2021/9/16
 */
@WebFilter(filterName = "authFilter", urlPatterns = { "/*" })
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("[AuthFilter] - doFilter");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}

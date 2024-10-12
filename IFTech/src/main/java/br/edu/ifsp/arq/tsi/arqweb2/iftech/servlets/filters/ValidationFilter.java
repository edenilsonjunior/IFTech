package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(
        urlPatterns = {"", ""},
        filterName = "Authorization")
public class ValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        var httpRequest = (HttpServletRequest) request;
        var session = httpRequest.getSession(false);
        if(session == null || session.getAttribute("customer") == null) {
            var httpResponse = (HttpServletResponse)response;
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
        else {
            chain.doFilter(request, response);
        }
    }
}

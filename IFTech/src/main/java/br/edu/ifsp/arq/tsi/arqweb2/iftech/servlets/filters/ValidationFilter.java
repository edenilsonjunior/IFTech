package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.filters;

import java.io.IOException;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.Utils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {
        "/api/customer/logout",
        "/api/customer/update",
        "/views/customer/profile.html",
        "/api/order/create",
        "/api/order/delete",
        "/api/order/retrieve",
        "/api/order/update",
        "/views/order/service-order-register.html",
        "/views/order/service-order-edit.html",
        "/views/order/service-order-list.html"
}, filterName = "Authorization")
public class ValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        var httpRequest = (HttpServletRequest) request;

        if (Utils.getCustomer(httpRequest) == null) {
            var httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/views/error/401.html");
        } else {
            chain.doFilter(request, response);
        }
    }

}

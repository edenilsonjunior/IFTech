package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/api/customer/logout")
public class LogoutCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LogoutCustomer() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        var session = request.getSession(false);
        session.invalidate();
        response.sendRedirect(request.getContextPath() +"/index.html");
    }
}

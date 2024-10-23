package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/logout")
public class LogoutCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LogoutCustomer() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        var session = req.getSession(false);
        session.invalidate();
        req.getRequestDispatcher("index.html").forward(req, resp);
    }
}

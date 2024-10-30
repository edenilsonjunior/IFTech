package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.customer;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.CustomerDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.Utils;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/api/customer/login")
@MultipartConfig
public class LoginCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginCustomer() {
        super();
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = PasswordEncoder.encode(request.getParameter("password"));

            if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                Utils.writeJsonResponse(response, "error", "Email ou senha não podem estar vazios.");
                return;
            }

            var customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
            var customer = customerDao.findCustomerByEmail(email);

            if (!customer.checkPassword(password)) {
                Utils.writeJsonResponse(response, "error", "Não foi possível realizar Login, verifique a senha");
                return;
            }

            var session = request.getSession();
            session.setMaxInactiveInterval(600);
            session.setAttribute("customer", customer);
            response.sendRedirect(request.getContextPath() +"/index.html");

        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonResponse(response,"error", e.getMessage());
        }
    }
}

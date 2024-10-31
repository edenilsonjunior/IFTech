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

@MultipartConfig
@WebServlet("/api/customer/login")
public class LoginCustomer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final CustomerDao customerDao;

    public LoginCustomer() {
        super();
        this.customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            var email = request.getParameter("email");
            var password = PasswordEncoder.encode(request.getParameter("password"));

            if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                Utils.writeJsonResponse(response, "error", "Email ou senha não podem estar vazios.");
                return;
            }

            var customer = customerDao.findCustomerByEmail(email);

            if (!customer.checkPassword(password)) {
                Utils.writeJsonResponse(response, "error", "Não foi possível realizar Login, verifique a senha");
                return;
            }

            var session = request.getSession();
            session.setMaxInactiveInterval(600);
            session.setAttribute("customer", customer);
            response.sendRedirect(request.getContextPath() + "/index.html");

        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonResponse(response, "error", e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            Utils.writeJsonResponse(response, "error", e.getMessage());
        }
    }

}

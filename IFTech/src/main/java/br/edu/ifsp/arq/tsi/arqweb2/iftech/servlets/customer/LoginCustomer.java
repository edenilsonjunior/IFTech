package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.customer;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.CustomerDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/login")
public class LoginCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginCustomer() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
        String email = req.getParameter("email");
        String password = PasswordEncoder.encode(req.getParameter("password"));
        String url;

        var optional = customerDao.getCustomerByEmail(email);

        if(optional.isEmpty()) {
            req.setAttribute("error", "Não existe cliente com esse email");
            url = "customer-login.jsp";
            req.getRequestDispatcher(url).forward(req, resp);
            return;
        }

        var customer = optional.get();
        if (!customer.checkPassword(password)) {
            req.setAttribute("error", "Não foi possível realizar Login, verifique Email e Senha");
            url = "customer-login.jsp";
            req.getRequestDispatcher(url).forward(req, resp);
            return;
        }

        var session = req.getSession();
        session.setMaxInactiveInterval(600);
        session.setAttribute("customer", customer);
        url = "index.jsp";

        var dispatcher = req.getRequestDispatcher(url);
        dispatcher.forward(req, resp);
    }
}

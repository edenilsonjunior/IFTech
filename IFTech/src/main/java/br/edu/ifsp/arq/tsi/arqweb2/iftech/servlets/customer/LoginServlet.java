package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.customer;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Address;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.CustomerDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String url;

        var customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());

        Optional<Customer> optional = customerDao.getCustomerByEmailAndPassword(email, password);
        if(optional.isPresent()) {
            var customer = optional.get();

            var session = req.getSession();
            session.setMaxInactiveInterval(600);
            session.setAttribute("customer", customer);
            url = "/home";
        }else {
            req.setAttribute("result", "loginError");
            url = "/login.jsp";
        }

        var dispatcher = req.getRequestDispatcher(url);
        dispatcher.forward(req, resp);
    }
}

package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.customer;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.CustomerDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/createCustomer")
public class CreateCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateCustomerServlet() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String cpf = request.getParameter("cpf").replaceAll("[.-]", "");

        var customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setCpf(cpf);

        RequestDispatcher dispatcher = null;
        var customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());

        if(customerDao.create(customer)) {
            dispatcher = request.getRequestDispatcher("/index.jsp");
        }else {
            request.setAttribute("result", "notRegistered");
            dispatcher = request.getRequestDispatcher("/customer-register.jsp");
        }

        dispatcher.forward(request, response);

    }
}

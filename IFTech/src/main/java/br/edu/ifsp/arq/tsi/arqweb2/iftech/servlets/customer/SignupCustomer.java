package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.customer;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Address;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.CustomerDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/signup")
public class SignupCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SignupCustomer() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = PasswordEncoder.encode(request.getParameter("password"));

        String street = request.getParameter("street");
        String number = request.getParameter("number");
        String complement = request.getParameter("complement");
        String district = request.getParameter("district");
        String zipCode = request.getParameter("zipCode");
        String city = request.getParameter("city");
        String state = request.getParameter("state");

        var customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setCpf(cpf);
        customer.setPassword(password);

        var address = new Address();
        address.setStreet(street);
        address.setNumber(number);
        address.setComplement(complement);
        address.setDistrict(district);
        address.setZipCode(zipCode);
        address.setCity(city);
        address.setState(state);

        customer.setAddress(address);

        var customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());

        if(customerDao.create(customer)) {
            response.sendRedirect("customer-login.jsp");
        }else {
            request.setAttribute("result", "notRegistered");
            request.getRequestDispatcher("customer-signup.jsp").forward(request, response);;
        }
    }
}

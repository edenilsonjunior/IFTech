package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.customer;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Address;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
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


@WebServlet("/api/customer/signup")
@MultipartConfig
public class SignupCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SignupCustomer() {
        super();
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            var customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
            customerDao.create(createCustomer(request));
            response.sendRedirect(request.getContextPath() +"/views/customer/login.html");

        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonResponse(response, "error", e.getMessage());
        }
    }

    private Customer createCustomer(HttpServletRequest request){
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

        return customer;
    }

}

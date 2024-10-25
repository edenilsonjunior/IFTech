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

        var customer = new Customer();
        customer.setName(request.getParameter("name"));
        customer.setEmail(request.getParameter("email"));
        customer.setPhone(request.getParameter("phone"));
        customer.setCpf(request.getParameter("cpf"));
        customer.setPassword(PasswordEncoder.encode(request.getParameter("password")));

        var address = new Address();
        address.setStreet(request.getParameter("street"));
        address.setNumber(request.getParameter("number"));
        address.setComplement(request.getParameter("complement"));
        address.setDistrict(request.getParameter("district"));
        address.setZipCode(request.getParameter("zipCode"));
        address.setCity(request.getParameter("city"));
        address.setState(request.getParameter("state"));

        customer.setAddress(address);

        return customer;
    }

}

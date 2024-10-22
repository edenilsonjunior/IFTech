package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.customer;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Address;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.CustomerDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.Utils;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


@WebServlet("/signup")
@MultipartConfig
public class SignupCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SignupCustomer() {
        super();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "views/customer/signup.html";
        response.sendRedirect(url);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
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

            var content = new HashMap<String, Object>();

            if(!customerDao.existsByEmail(email)){

                if(customerDao.create(customer)){
                    response.sendRedirect("views/customer/login.html");
                }else{
                    System.out.println("Erro ao inserir customer");
                }
            }else{
                content.put("error", "Já existe um registro com esse email");
            }
            Utils.writeJsonResponse(response, content);
        } catch (Exception e) {

            var content = new HashMap<String, Object>();
            content.put("error", e.getMessage());
            Utils.writeJsonResponse(response, content);
        }
    }
}

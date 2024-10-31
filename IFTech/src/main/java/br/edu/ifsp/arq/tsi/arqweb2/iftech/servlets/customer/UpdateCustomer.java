package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.customer;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.CustomerDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.DataSourceSearcher;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.PasswordEncoder;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig
@WebServlet("/api/customer/update")
public class UpdateCustomer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final CustomerDao customerDao;

    public UpdateCustomer() {
        super();
        this.customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        var customer = Utils.getCustomer(request);
        Utils.writeJsonResponse(response, "customer", customer);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            var customer = Utils.getCustomer(request);
            if (customer == null) {
                response.sendRedirect(request.getContextPath() + "/views/customer/login.html");
                return;
            }

            changePassword(customer, request);
            changePhone(customer, request);
            changeAddress(customer, request);

            customerDao.update(customer);

            response.sendRedirect(request.getContextPath() + "/views/customer/profile.html");
        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonResponse(response, "error", e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            Utils.writeJsonResponse(response, "error", e.getMessage());
        }
    }

    private void changePassword(Customer customer, HttpServletRequest request) {

        String password = request.getParameter("password");
        if (password == null)
            throw new CustomHttpException(HttpServletResponse.SC_BAD_REQUEST, "password is null.");

        password = PasswordEncoder.encode(password);
        customer.setPassword(password);
    }

    private void changePhone(Customer customer, HttpServletRequest request) {
        customer.setPhone(request.getParameter("phone"));
    }

    private void changeAddress(Customer customer, HttpServletRequest request) {

        var address = customer.getAddress();

        var street = request.getParameter("street");
        var number = request.getParameter("number");
        var complement = request.getParameter("complement");
        var district = request.getParameter("district");
        var zipCode = request.getParameter("zipCode");
        var city = request.getParameter("city");
        var state = request.getParameter("state");

        if (street != null)
            address.setStreet(street);

        if (number != null)
            address.setNumber(number);

        if (complement != null)
            address.setComplement(complement);

        if (district != null)
            address.setDistrict(district);

        if (zipCode != null)
            address.setZipCode(zipCode);

        if (city != null)
            address.setCity(city);

        if (state != null)
            address.setState(state);
    }

}

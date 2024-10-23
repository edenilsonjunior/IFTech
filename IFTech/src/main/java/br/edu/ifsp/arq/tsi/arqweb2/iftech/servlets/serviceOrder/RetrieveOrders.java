package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.serviceOrder;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.ServiceOrderDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.Utils;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.DataSourceSearcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;


@WebServlet("/retrieveOrders")
public class RetrieveOrders extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RetrieveOrders() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            var content = new HashMap<String, Object>();
            var customer = (Customer) request.getSession().getAttribute("customer");

            var orderDao = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource());
            var orders = orderDao.getOrdersByCustomer(customer);

            content.put("orders", orders);
            Utils.writeJsonResponse(response, content);

        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonErrorResponse(response, e.getMessage());
        }
    }
}

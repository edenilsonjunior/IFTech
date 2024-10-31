package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.serviceOrder;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.ServiceOrderDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.ServiceOrder;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.Utils;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.DataSourceSearcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;

@WebServlet("/api/order/retrieve")
public class RetrieveOrders extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private final ServiceOrderDao orderDao;

    public RetrieveOrders() {
        super();
        this.orderDao = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            var content = new HashMap<String, Object>();
            var customer = Utils.getCustomer(request);

            var orders = orderDao.getOrdersByCustomer(customer).stream()
                    .sorted(Comparator.comparingLong(ServiceOrder::getId))
                    .toList();

            content.put("orders", orders);
            Utils.writeJsonResponse(response, content);

        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonResponse(response, "error", e.getMessage());
        }
    }

}

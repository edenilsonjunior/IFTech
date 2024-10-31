package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.serviceOrder;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.ServiceOrderDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.OrderStatus;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.PaymentMethod;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.ServiceOrder;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.Utils;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.DataSourceSearcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/api/order/create")
@MultipartConfig
public class CreateServiceOrder extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ServiceOrderDao serviceOrderDao;

    public CreateServiceOrder() {
        super();
        this.serviceOrderDao = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Utils.writeJsonResponse(response, "paymentMethods", serviceOrderDao.getPaymentMethods());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            var customer = Utils.getCustomer(request);
            var order = createServiceOrder(request);

            serviceOrderDao.create(order, customer);
            response.sendRedirect(request.getContextPath() + "/views/order/service-order-list.html");
        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonResponse(response, "error", e.getMessage());
        }
    }

    private ServiceOrder createServiceOrder(HttpServletRequest request) {

        var order = new ServiceOrder();

        var pm = new PaymentMethod();
        pm.setName(request.getParameter("paymentMethod"));
        order.setPaymentMethod(pm);

        order.setDescription(request.getParameter("description"));
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setObservation(request.getParameter("observation"));
        order.setPrice(new BigDecimal(request.getParameter("price")));

        order.setIssueDate(LocalDate.parse(request.getParameter("issueDate")));
        order.setEndDate(LocalDate.now().plusDays(7));

        return order;
    }

}

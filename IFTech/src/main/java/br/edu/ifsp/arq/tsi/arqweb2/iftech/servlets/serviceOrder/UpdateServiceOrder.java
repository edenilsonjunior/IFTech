package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.serviceOrder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.ServiceOrderDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.OrderStatus;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.PaymentMethod;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.ServiceOrder;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.DataSourceSearcher;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.Utils;

@WebServlet("/api/order/update")
@MultipartConfig
public class UpdateServiceOrder extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ServiceOrderDao serviceOrderDao;

    public UpdateServiceOrder() {
        super();
        this.serviceOrderDao = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        var content = new HashMap<String, Object>();
        var id = Long.parseLong(request.getParameter("id"));

        content.put("order", serviceOrderDao.getOrderById(id));
        content.put("status", OrderStatus.values());
        content.put("paymentMethods", serviceOrderDao.getPaymentMethods());

        Utils.writeJsonResponse(response, content);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            var id = Long.parseLong(request.getParameter("id"));
            var order = serviceOrderDao.getOrderById(id);

            if (order == null) {
                Utils.writeJsonResponse(response, "error", "Ordem de serviço não encontrada");
                return;
            }

            updateFields(request, order);
            serviceOrderDao.update(order);

            response.sendRedirect(request.getContextPath() + "/views/order/service-order-list.html");
        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonResponse(response, "error", e.getMessage());
        }
    }

    private void updateFields(HttpServletRequest request, ServiceOrder order) {

        var description = request.getParameter("description");
        var status = request.getParameter("status");
        var price = new BigDecimal(request.getParameter("price"));
        var issueDate = request.getParameter("issueDate");
        var observation = request.getParameter("observation");
        var paymentMethod = request.getParameter("paymentMethod");

        order.setDescription(description);
        order.setStatus(OrderStatus.valueOf(status));
        order.setPrice(price);
        order.setIssueDate(LocalDate.parse(issueDate));
        order.setEndDate(order.getIssueDate().plusDays(7));
        order.setObservation(observation);

        var pm = new PaymentMethod();
        pm.setName(paymentMethod);
        order.setPaymentMethod(pm);
    }

}

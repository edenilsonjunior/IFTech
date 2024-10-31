package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.serviceOrder;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.ServiceOrderDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.PaymentMethod;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.DataSourceSearcher;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig
@WebServlet("/api/order/create-payment-method")
public class CreatePaymentMethod extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ServiceOrderDao serviceOrderDao;

    public CreatePaymentMethod() {
        super();
        this.serviceOrderDao = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            var paymentMethod = new PaymentMethod();
            paymentMethod.setName(request.getParameter("payment-method-name"));

            serviceOrderDao.createPaymentMethod(paymentMethod);
            response.sendRedirect(request.getContextPath() + "/index.html");

        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonResponse(response, "error", e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            Utils.writeJsonResponse(response, "error", e.getMessage());
        }

    }

}

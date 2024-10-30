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


@WebServlet("/api/order/create-payment-method")
@MultipartConfig
public class CreatePaymentMethod extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreatePaymentMethod() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var name = request.getParameter("payment-method-name");

            var paymentMethod = new PaymentMethod();
            paymentMethod.setName(name);

            var dao = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource());

            dao.createPaymentMethod(paymentMethod);
            response.sendRedirect(request.getContextPath() +"/index.html");

        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonResponse(response, "error", e.getMessage());
        }

    }
}
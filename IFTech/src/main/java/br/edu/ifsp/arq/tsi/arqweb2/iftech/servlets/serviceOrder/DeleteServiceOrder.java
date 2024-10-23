package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.serviceOrder;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.ServiceOrderDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.DataSourceSearcher;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;


@WebServlet("/api/order/delete")
public class DeleteServiceOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteServiceOrder() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            var content = new HashMap<String, Object>();
            var customer = Utils.getCustomer(request);
            if (customer == null)
                return;

            var serviceOrderId = Long.parseLong(request.getParameter("id"));
            var serviceOrderDao = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource());

            serviceOrderDao.delete(serviceOrderId);
            content.put("success", "sucesso ao cancelar a ordem de serviço");
            Utils.writeJsonResponse(response, content);

        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonResponse(response,"error", e.getMessage());
        }
    }
}

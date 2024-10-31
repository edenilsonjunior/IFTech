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

@WebServlet("/api/order/delete")
public class DeleteServiceOrder extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ServiceOrderDao serviceOrderDao;

    public DeleteServiceOrder() {
        super();
        this.serviceOrderDao = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            var serviceOrderId = Long.parseLong(request.getParameter("id"));
            serviceOrderDao.delete(serviceOrderId);

            Utils.writeJsonResponse(response, "success", "sucesso ao cancelar a ordem de serviço");

        } catch (CustomHttpException e) {
            response.setStatus(e.getStatusCode());
            Utils.writeJsonResponse(response, "error", e.getMessage());
        }
    }

}

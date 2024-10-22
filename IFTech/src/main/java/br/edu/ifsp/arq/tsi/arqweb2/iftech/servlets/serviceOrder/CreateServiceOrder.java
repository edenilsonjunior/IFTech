package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.serviceOrder;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.ServiceOrderDao;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.OrderStatus;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.PaymentMethod;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.ServiceOrder;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.DataSourceSearcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;


@WebServlet("/createOrder")
public class CreateServiceOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateServiceOrder() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var orderDao = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource());
        var order = new ServiceOrder();

        var customer = (Customer) request.getSession().getAttribute("customer");
        order.setCustomer(customer);

        var pm = new PaymentMethod();
        pm.setName(request.getParameter("paymentMethod"));
        order.setPaymentMethod(pm);

        order.setDescription(request.getParameter("description"));
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setObservation(request.getParameter("observation"));
        order.setPrice(new BigDecimal(request.getParameter("price")));

        order.setIssueDate(LocalDate.parse(request.getParameter("issueDate")));
        order.setEndDate(LocalDate.now().plusDays(7));

        if(orderDao.create(order, customer)) {
            response.sendRedirect("service-order-list.jsp");
        }else {
            request.setAttribute("error", "Houve um erro ao cadastrar a ordem");
            request.getRequestDispatcher("service-order-register.jsp").forward(request, response);;
        }
    }
}

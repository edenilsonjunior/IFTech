package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.queries.ServiceOrderQueries;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.OrderStatus;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.PaymentMethod;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.ServiceOrder;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceOrderDao {

    private final DataSource dataSource;

    public ServiceOrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ServiceOrder create(ServiceOrder order, Customer customer) {

        if (!existsPaymentMethodByName(order.getPaymentMethod().getName()))
            order.setPaymentMethod(createPaymentMethod(order.getPaymentMethod()));
        else
            order.setPaymentMethod(getPaymentMethodByName(order.getPaymentMethod().getName()));

        try (var conn = dataSource.getConnection();
                var psServiceOrder = conn.prepareStatement(ServiceOrderQueries.INSERT,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            psServiceOrder.setLong(1, customer.getId());
            psServiceOrder.setString(2, order.getDescription());
            psServiceOrder.setString(3, order.getStatus().toString());
            psServiceOrder.setBigDecimal(4, order.getPrice());
            psServiceOrder.setDate(5, Date.valueOf(order.getIssueDate()));
            psServiceOrder.setDate(6, Date.valueOf(order.getEndDate()));
            psServiceOrder.setString(7, order.getObservation());
            psServiceOrder.setLong(8, order.getPaymentMethod().getId());

            psServiceOrder.executeUpdate();

            var rs2 = psServiceOrder.getGeneratedKeys();
            if (rs2.next()) {
                order.setId(rs2.getLong(1));
            }

            return order;
        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
    }

    public boolean update(ServiceOrder order) {

        if (!existsPaymentMethodByName(order.getPaymentMethod().getName()))
            order.setPaymentMethod(createPaymentMethod(order.getPaymentMethod()));
        else
            order.setPaymentMethod(getPaymentMethodByName(order.getPaymentMethod().getName()));

        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(ServiceOrderQueries.UPDATE)) {

            ps.setString(1, order.getDescription());
            ps.setString(2, order.getStatus().toString());
            ps.setBigDecimal(3, order.getPrice());
            ps.setDate(4, Date.valueOf(order.getIssueDate()));
            ps.setDate(5, Date.valueOf(order.getEndDate()));
            ps.setString(6, order.getObservation());
            ps.setLong(7, order.getPaymentMethod().getId());
            ps.setLong(8, order.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }

        return true;
    }

    public boolean delete(Long orderId) {

        if (orderId == null)
            throw new CustomHttpException(HttpServletResponse.SC_BAD_REQUEST, "Id não pode ser nulo");

        if (!existsById(orderId))
            throw new CustomHttpException(HttpServletResponse.SC_NOT_FOUND, "Ordem de serviço não encontrada");

        var order = getOrderById(orderId);
        order.setStatus(OrderStatus.CANCELED);

        return update(order);
    }

    public List<ServiceOrder> getOrdersByCustomer(Customer customer) {

        var list = new ArrayList<ServiceOrder>();

        try (var con = dataSource.getConnection();
                var ps = con.prepareStatement(ServiceOrderQueries.SELECT_ORDER_BY_CUSTOMER_ID)) {

            ps.setLong(1, customer.getId());

            var rs = ps.executeQuery();
            while (rs.next()) {
                var order = new ServiceOrder();
                order.setId(rs.getLong("id"));
                order.setDescription(rs.getString("description"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setPrice(rs.getBigDecimal("price"));
                order.setIssueDate(LocalDate.parse(rs.getDate("issue_date").toString()));
                order.setEndDate(LocalDate.parse(rs.getDate("end_date").toString()));
                order.setObservation(rs.getString("observation"));

                var paymentMethod = new PaymentMethod();
                paymentMethod.setId(rs.getLong("payment_method_id"));
                paymentMethod.setName(rs.getString("payment_method_name"));

                order.setPaymentMethod(paymentMethod);
                list.add(order);
            }
            return list;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
        }
    }

    public boolean existsById(Long orderId) {

        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(ServiceOrderQueries.EXISTS_BY_ID)) {

            ps.setLong(1, orderId);
            var rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
    }

    public ServiceOrder getOrderById(Long orderId) {

        try (var con = dataSource.getConnection();
                var ps = con.prepareStatement(ServiceOrderQueries.SELECT_ORDER_BY_ID)) {

            ps.setLong(1, orderId);

            var rs = ps.executeQuery();
            if (rs.next()) {
                var order = new ServiceOrder();
                order.setId(rs.getLong("id"));
                order.setDescription(rs.getString("description"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setPrice(rs.getBigDecimal("price"));
                order.setIssueDate(LocalDate.parse(rs.getDate("issue_date").toString()));
                order.setEndDate(LocalDate.parse(rs.getDate("end_date").toString()));
                order.setObservation(rs.getString("observation"));

                var paymentMethod = new PaymentMethod();
                paymentMethod.setId(rs.getLong("payment_method_id"));
                paymentMethod.setName(rs.getString("payment_method_name"));

                order.setPaymentMethod(paymentMethod);
                return order;
            }
            return null;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
        }
    }

    public boolean existsPaymentMethodByName(String name) {

        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(ServiceOrderQueries.EXISTS_PAYMENT_METHOD_BY_NAME)) {

            ps.setString(1, name);
            var rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
    }

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {

        var paymentMethodExists = getPaymentMethodByName(paymentMethod.getName());
        if (paymentMethodExists != null)
            throw new CustomHttpException(HttpServletResponse.SC_CONFLICT, "Método de pagamento já existe");

        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(ServiceOrderQueries.INSERT_PAYMENT_METHOD,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, paymentMethod.getName());
            ps.executeUpdate();

            var rs = ps.getGeneratedKeys();
            if (rs.next()) {
                paymentMethod.setId(rs.getLong(1));
            }

            return paymentMethod;
        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
    }

    public PaymentMethod getPaymentMethodByName(String name) {

        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(ServiceOrderQueries.SELECT_PAYMENT_METHOD_BY_NAME)) {
            ps.setString(1, name);

            var rs = ps.executeQuery();
            if (rs.next()) {
                var paymentMethod = new PaymentMethod();
                paymentMethod.setId(rs.getLong("id"));
                paymentMethod.setName(name);
                return paymentMethod;
            }
            return null;
        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
    }

    public List<PaymentMethod> getPaymentMethods() {

        var list = new ArrayList<PaymentMethod>();

        try (var con = dataSource.getConnection();
                var ps = con.prepareStatement(ServiceOrderQueries.SELECT_PAYMENT_METHODS)) {

            var rs = ps.executeQuery();
            while (rs.next()) {
                var paymentMethod = new PaymentMethod();
                paymentMethod.setId(rs.getLong("id"));
                paymentMethod.setName(rs.getString("name"));
                list.add(paymentMethod);
            }
            return list;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
        }
    }

}

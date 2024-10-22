package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Address;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.OrderStatus;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.PaymentMethod;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.ServiceOrder;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.DataSourceSearcher;

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

    public boolean create(ServiceOrder order, Customer customer){

        var paymentMethodSQl = "INSERT INTO payment_method (name) VALUES (?);";

        var serviceOrderSql = """
                INSERT INTO service_order (customer_id, description, status, price, issue_date, end_date, observation, payment_method_id)
                VALUES
                (?,?,?,?,?,?,?, ?);
                """;

        try(
            var conn = dataSource.getConnection();
            var psPaymentMethod = conn.prepareStatement(paymentMethodSQl, PreparedStatement.RETURN_GENERATED_KEYS);
            var psServiceOrder = conn.prepareStatement(serviceOrderSql)
        ){
            psPaymentMethod.setString(1, order.getPaymentMethod().getName());

            psPaymentMethod.executeUpdate();

            var rs = psPaymentMethod.getGeneratedKeys();
            if(rs.next()){
                var payment = order.getPaymentMethod();
                payment.setId(rs.getLong(1));
            }

            psServiceOrder.setLong(1, customer.getId());
            psServiceOrder.setString(2, order.getDescription());
            psServiceOrder.setString(3, order.getStatus().toString());
            psServiceOrder.setBigDecimal(4, order.getPrice());
            psServiceOrder.setDate(5, Date.valueOf(order.getIssueDate()));
            psServiceOrder.setDate(6, Date.valueOf(order.getEndDate()));
            psServiceOrder.setString(7, order.getObservation());
            psServiceOrder.setLong(8, order.getPaymentMethod().getId());

            psServiceOrder.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException("Erro durante a escrita no BD", e);
        }
        return true;
    }

    public boolean updateStatus(long serviceOrderId, OrderStatus status){

        var updateSql = """
                UPDATE service_order
                SET status = ?
                WHERE id = ?;
                """;

        try(
            var conn = dataSource.getConnection();
            var ps = conn.prepareStatement(updateSql)
        ){
            ps.setString(1, status.toString());
            ps.setLong(2, serviceOrderId);
            ps.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException("Erro durante a escrita no BD", e);
        }
        return true;
    }

    public boolean delete(ServiceOrder order){
        return false;
    }

    public List<ServiceOrder> getOrdersByCustomer(Customer customer){

        String sql = """
            SELECT
                SO.id,
                SO.description,
                SO.status,
                SO.price,
                SO.issue_date,
                SO.end_date,
                SO.observation,
                PM.id,
                PM.name
            FROM service_order SO
            JOIN payment_method PM
                ON PM.id = SO.payment_method_id
            WHERE SO.customer_id = ?;
            """ ;

        var list = new ArrayList<ServiceOrder>();

        try (var con = dataSource.getConnection(); var ps = con.prepareStatement(sql)) {

            ps.setLong(1, customer.getId());

            var rs = ps.executeQuery();
            while (rs.next()) {
                var order = new ServiceOrder();
                order.setId(rs.getLong(1));
                order.setDescription(rs.getString(2));
                order.setStatus(OrderStatus.valueOf(rs.getString(3)));
                order.setPrice(rs.getBigDecimal(4));
                order.setIssueDate(LocalDate.parse(rs.getDate(5).toString()));
                order.setEndDate(LocalDate.parse(rs.getDate(6).toString()));
                order.setObservation(rs.getString(7));

                var paymentMethod = new PaymentMethod();
                paymentMethod.setId(rs.getLong(8));
                paymentMethod.setName(rs.getString(9));

                order.setPaymentMethod(paymentMethod);
                order.setCustomer(customer);

                list.add(order);

            }
            return list;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
        }
    }
}

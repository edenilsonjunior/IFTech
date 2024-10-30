package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.queries.CustomerQueries;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDao {

    private final DataSource dataSource;
    private final AddressDao addressDao;
    private final ServiceOrderDao serviceOrderDao;

    public CustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.addressDao = new AddressDao(dataSource);
        this.serviceOrderDao = new ServiceOrderDao(dataSource);
    }

    public Customer create(Customer customer) {

        if (exists("cpf", customer.getCpf()))
            throw new CustomHttpException(HttpServletResponse.SC_BAD_REQUEST, "Já existe um cliente com esse cpf");

        if (exists("email", customer.getEmail()))
            throw new CustomHttpException(HttpServletResponse.SC_BAD_REQUEST, "Já existe um cliente com esse email");


        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(CustomerQueries.INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getCpf());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPassword());
            ps.setString(5, customer.getPhone());
            ps.setBoolean(6, customer.isActive());
            ps.executeUpdate();

            var rs = ps.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                customer.setId(id);
                customer.getAddress().setId(id);
            }

            addressDao.create(customer.getAddress());

            return customer;
        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
    }

    public Customer update(Customer customer) {

        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(CustomerQueries.UPDATE);) {

            ps.setString(1, customer.getPassword());
            ps.setString(2, customer.getPhone());
            ps.setLong(3, customer.getId());

            ps.executeUpdate();
            addressDao.update(customer.getAddress());

            return customer;
        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
    }

    public Customer findCustomerByEmail(String email) {

        if (!exists("email", email))
            throw new CustomHttpException(HttpServletResponse.SC_NOT_FOUND, "Não existe cliente com esse email");

        
        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(CustomerQueries.SELECT_BY_EMAIL);) {

            ps.setString(1, email);
            var rs = ps.executeQuery();

            var customer = new Customer();

            if (rs.next()) {
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setCpf(rs.getString("cpf"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setPhone(rs.getString("phone"));
                customer.setActive(rs.getBoolean("active"));

                var orders = serviceOrderDao.getOrdersByCustomer(customer);
                var address = addressDao.findByCustomer(customer);

                customer.setOrders(orders);
                customer.setAddress(address);
            }

            return customer;

        } catch (SQLException ex) {
            throw new CustomHttpException(ex.getErrorCode(), "Error SQL: " + ex.getMessage());
        }
    }

    private boolean exists(String field, String value) {

        var sql = String.format(CustomerQueries.EXISTS, field);

        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(sql)) {

            ps.setString(1, value);
            
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
        return false;
    }
}

package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Address;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerDao {

    private final DataSource dataSource;

    public CustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Customer create(Customer customer) {

        if(existsByCPF(customer.getCpf()))
            throw new CustomHttpException(HttpServletResponse.SC_BAD_REQUEST, "Já existe um cliente com esse cpf");

        if(existsByEmail(customer.getEmail()))
            throw new CustomHttpException(HttpServletResponse.SC_BAD_REQUEST, "Já existe um cliente com esse email");

        String customerSql = """
                INSERT INTO customer (name, cpf, email, password, phone, active) VALUES
                (?, ?, ?, ?, ?, ?);
            """;

        String addressSql = """
                INSERT INTO address (id, street, number, complement, district, zip_code, city, state)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;

        try(
            var conn = dataSource.getConnection();
            var psCustomer = conn.prepareStatement(customerSql, PreparedStatement.RETURN_GENERATED_KEYS);
            var psAddress = conn.prepareStatement(addressSql);
        ){
            psCustomer.setString(1, customer.getName());
            psCustomer.setString(2, customer.getCpf());
            psCustomer.setString(3, customer.getEmail());
            psCustomer.setString(4, customer.getPassword());
            psCustomer.setString(5, customer.getPhone());
            psCustomer.setBoolean(6, customer.isActive());
            psCustomer.executeUpdate();

            var rs = psCustomer.getGeneratedKeys();
            if(rs.next()){
                customer.setId(rs.getLong(1));
                customer.getAddress().setId(rs.getLong(1));
            }

            var address = customer.getAddress();
            psAddress.setLong(1, address.getId());
            psAddress.setString(2, address.getStreet());
            psAddress.setString(3, address.getNumber());
            psAddress.setString(4, address.getComplement());
            psAddress.setString(5, address.getDistrict());
            psAddress.setString(6, address.getZipCode());
            psAddress.setString(7, address.getCity());
            psAddress.setString(8, address.getState());
            psAddress.executeUpdate();

            return customer;
        }catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
    }

    public List<Customer> getCustomers() {

        String sql = """
                SELECT
                	C.id,
                	C.name,
                    C.cpf,
                    C.email,
                    C.password,
                    C.phone,
                    C.active,
                    A.street,
                    A.number,
                    A.complement,
                    A.district,
                    A.zip_code,
                    A.city,
                    A.state
                FROM customer C
                JOIN address A
                	ON A.id = C.id;
                """ ;

        var list = new ArrayList<Customer>();

        try (var con = dataSource.getConnection(); var ps = con.prepareStatement(sql)) {

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var customer = new Customer();
                    customer.setId(rs.getLong(1));
                    customer.setName(rs.getString(2));
                    customer.setCpf(rs.getString(3));
                    customer.setEmail(rs.getString(4));
                    customer.setPassword(rs.getString(5));
                    customer.setPhone(rs.getString(6));
                    customer.setActive(rs.getBoolean(7));

                    var address = new Address();
                    address.setStreet(rs.getString(8));
                    address.setNumber(rs.getString(9));
                    address.setComplement(rs.getString(10));
                    address.setDistrict(rs.getString(11));
                    address.setZipCode(rs.getString(12));
                    address.setCity(rs.getString(13));
                    address.setState(rs.getString(14));

                    customer.setAddress(address);

                    var orders = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource()).getOrdersByCustomer(customer);
                    customer.setOrders(orders);

                    list.add(customer);
                }
            }
            return list;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
        }
    }

    public Customer getCustomerByEmail(String email) {

        if(!existsByEmail(email))
            throw new CustomHttpException(HttpServletResponse.SC_NOT_FOUND, "Não existe cliente com esse email");

        String sql = """
                SELECT
                	C.id,
                	C.name,
                    C.cpf,
                    C.email,
                    C.password,
                    C.phone,
                    C.active,
                    A.street,
                    A.number,
                    A.complement,
                    A.district,
                    A.zip_code,
                    A.city,
                    A.state
                FROM customer C
                JOIN address A
                	ON A.id = C.id
                WHERE C.email = ?;
                """ ;

        try (var con = dataSource.getConnection(); var ps = con.prepareStatement(sql)) {
            ps.setString(1, email);

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    var customer = new Customer();
                    customer.setId(rs.getLong(1));
                    customer.setName(rs.getString(2));
                    customer.setCpf(rs.getString(3));
                    customer.setEmail(rs.getString(4));
                    customer.setPassword(rs.getString(5));
                    customer.setPhone(rs.getString(6));
                    customer.setActive(rs.getBoolean(7));

                    var address = new Address();
                    address.setStreet(rs.getString(8));
                    address.setNumber(rs.getString(9));
                    address.setComplement(rs.getString(10));
                    address.setDistrict(rs.getString(11));
                    address.setZipCode(rs.getString(12));
                    address.setCity(rs.getString(13));
                    address.setState(rs.getString(14));

                    customer.setAddress(address);

                    var orders = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource()).getOrdersByCustomer(customer);
                    customer.setOrders(orders);

                    return customer;
                }
                throw new CustomHttpException(HttpServletResponse.SC_NOT_FOUND, "Não existe cliente com esse email");
            }
        } catch (SQLException ex) {
            throw new CustomHttpException(ex.getErrorCode(), "Error SQL: " + ex.getMessage());
        }
    }

    public boolean existsByCPF(String cpf){
        String sql = "SELECT COUNT(*) FROM customer WHERE cpf = ?";

        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro durante a consulta no BD", e);
        }

        return false;
    }

    public boolean existsByEmail(String email){
        String sql = "SELECT COUNT(*) FROM customer WHERE email = ?";

        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro durante a consulta no BD", e);
        }
        return false;
    }

}

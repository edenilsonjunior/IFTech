package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Address;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CustomerDao {

    private final DataSource dataSource;

    public CustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean create(Customer customer) {

        if(existsByCPF(customer.getCpf()) || existsByEmail(customer.getEmail()))
            return false;


        String addressSql = """
                INSERT INTO address (street, number, complement, district, zip_code, city, state)
                VALUES (?, ?, ?, ?, ?, ?, ?);
            """;

        String customerSql = """
                INSERT INTO customer (name, cpf, email, password, phone, active, address_id) VALUES
                (?, ?, ?, ?, ?, ?, ?);
            """;

        try(
            var conn = dataSource.getConnection();
            var psAddress = conn.prepareStatement(addressSql, PreparedStatement.RETURN_GENERATED_KEYS);
            var psCustomer = conn.prepareStatement(customerSql)){

            psAddress.executeUpdate();
            var rs = psAddress.getGeneratedKeys();
            if(rs.next())
                customer.getAddress().setId(rs.getLong(0));


            psCustomer.setString(1, customer.getName());
            psCustomer.setString(2, customer.getCpf());
            psCustomer.setString(3, customer.getEmail());
            psCustomer.setString(4, customer.getPassword());
            psCustomer.setString(4, customer.getPhone());
            psCustomer.setBoolean(4, customer.isActive());
            psCustomer.setLong(4, customer.getAddress().getId());
            psCustomer.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException("Erro durante a escrita no BD", e);
        }
        return true;
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
                	ON A.id = C.address_id;
                """ ;

        var list = new ArrayList<Customer>();

        try (var con = dataSource.getConnection(); var ps = con.prepareStatement(sql)) {

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var customer = new Customer();
                    customer.setId(rs.getLong(0));
                    customer.setName(rs.getString(1));
                    customer.setCpf(rs.getString(2));
                    customer.setEmail(rs.getString(3));
                    customer.setPassword(rs.getString(4));
                    customer.setPhone(rs.getString(5));
                    customer.setActive(rs.getBoolean(6));

                    var address = new Address();
                    address.setStreet(rs.getString(7));
                    address.setNumber(rs.getString(8));
                    address.setComplement(rs.getString(9));
                    address.setDistrict(rs.getString(10));
                    address.setZipCode(rs.getString(11));
                    address.setCity(rs.getString(12));
                    address.setState(rs.getString(13));

                    customer.setAddress(address);

                    list.add(customer);
                }
            }
            return list;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
        }
    }

    public Optional<Customer> getCustomerByEmailAndPassword(String email, String password) {

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
                	ON A.id = C.address_id
                WHERE C.email = ? AND c.password ?;
                """ ;

        Optional<Customer> optional = Optional.empty();

        try (var con = dataSource.getConnection(); var ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    var customer = new Customer();
                    customer.setId(rs.getLong(0));
                    customer.setName(rs.getString(1));
                    customer.setCpf(rs.getString(2));
                    customer.setEmail(rs.getString(3));
                    customer.setPassword(rs.getString(4));
                    customer.setPhone(rs.getString(5));
                    customer.setActive(rs.getBoolean(6));

                    var address = new Address();
                    address.setStreet(rs.getString(7));
                    address.setNumber(rs.getString(8));
                    address.setComplement(rs.getString(9));
                    address.setDistrict(rs.getString(10));
                    address.setZipCode(rs.getString(11));
                    address.setCity(rs.getString(12));
                    address.setState(rs.getString(13));

                    customer.setAddress(address);

                    optional = Optional.of(customer);
                }
            }
            return optional;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
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

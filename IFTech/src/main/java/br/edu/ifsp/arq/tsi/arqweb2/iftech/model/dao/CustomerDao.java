package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Address;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.PasswordEncoder;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;

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

    public Boolean create(Customer customer) {

        if(existsByCPF(customer.getCpf()) || existsByEmail(customer.getEmail()))
            return false;

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

                    list.add(customer);
                }
            }
            return list;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
        }
    }

    public Optional<Customer> getCustomerByEmail(String email) {

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

        Optional<Customer> optional = Optional.empty();

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
                    address.setStreet(rs.getString(1));
                    address.setNumber(rs.getString(2));
                    address.setComplement(rs.getString(3));
                    address.setDistrict(rs.getString(4));
                    address.setZipCode(rs.getString(5));
                    address.setCity(rs.getString(6));
                    address.setState(rs.getString(7));

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

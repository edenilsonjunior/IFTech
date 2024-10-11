package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.customer.Customer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDao {

    private DataSource dataSource;

    public CustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean create(Customer customer) {

        var optional = getCustomerByCPF(customer.getCpf());

        if(optional.isPresent())
            return false;

        String sql = "INSERT INTO customer (name, email, phone, cpf) values (?,?,?,?)";

        try(var conn = dataSource.getConnection();
            var ps = conn.prepareStatement(sql)){
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getCpf());
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Erro durante a escrita no BD", e);
        }
        return true;
    }

    public Optional<Customer> getCustomerByCPF(String cpf) {

        String sql = "SELECT id, name, email, phone, cpf from customer where cpf=?";
        Optional<Customer> optional = Optional.empty();

        try (var con = dataSource.getConnection(); var ps = con.prepareStatement(sql)) {
            ps.setString(1, cpf);

            try (var resultSet = ps.executeQuery()) {
                if (resultSet.next()) {

                    var customer = new Customer();
                    customer.setId(resultSet.getInt(1));
                    customer.setName(resultSet.getString(2));
                    customer.setEmail(resultSet.getString(3));
                    customer.setPhone(resultSet.getString(4));
                    customer.setCpf(resultSet.getString(5));

                    optional = Optional.of(customer);
                }
            }
            return optional;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
        }
    }

    public List<Customer> getCustomers() {

        String sql = "SELECT id, name, email, phone, cpf from customer";
        var list = new ArrayList<Customer>();

        try (var con = dataSource.getConnection(); var ps = con.prepareStatement(sql)) {
            try (var resultSet = ps.executeQuery()) {

                while (resultSet.next()) {
                    var customer = new Customer();
                    customer.setId(resultSet.getInt(1));
                    customer.setName(resultSet.getString(2));
                    customer.setEmail(resultSet.getString(3));
                    customer.setPhone(resultSet.getString(4));
                    customer.setCpf(resultSet.getString(5));
                    list.add(customer);
                }
            }
            return list;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
        }
    }


}

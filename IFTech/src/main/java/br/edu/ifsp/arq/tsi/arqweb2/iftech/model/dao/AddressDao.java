package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao;

import java.sql.SQLException;
import javax.sql.DataSource;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.exception.CustomHttpException;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.queries.AddressQueries;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Address;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;

public class AddressDao {

    private final DataSource dataSource;

    public AddressDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(Address address) {

        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(AddressQueries.INSERT)) {

            ps.setLong(1, address.getId());
            ps.setString(2, address.getStreet());
            ps.setString(3, address.getNumber());
            ps.setString(4, address.getComplement());
            ps.setString(5, address.getDistrict());
            ps.setString(6, address.getZipCode());
            ps.setString(7, address.getCity());
            ps.setString(8, address.getState());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
    }

    public void update(Address address) {

        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(AddressQueries.UPDATE)) {

            ps.setString(1, address.getStreet());
            ps.setString(2, address.getNumber());
            ps.setString(3, address.getComplement());
            ps.setString(4, address.getDistrict());
            ps.setString(5, address.getZipCode());
            ps.setString(6, address.getCity());
            ps.setString(7, address.getState());
            ps.setLong(8, address.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
    }

    public Address findByCustomer(Customer customer) {

        try (var conn = dataSource.getConnection();
                var ps = conn.prepareStatement(AddressQueries.SELECT_BY_ID)) {

            ps.setLong(1, customer.getId());
            var rs = ps.executeQuery();

            var address = new Address();

            if (rs.next()) {
                address.setId(rs.getLong("id"));
                address.setStreet(rs.getString("street"));
                address.setNumber(rs.getString("number"));
                address.setComplement(rs.getString("complement"));
                address.setDistrict(rs.getString("district"));
                address.setZipCode(rs.getString("zip_code"));
                address.setCity(rs.getString("city"));
                address.setState(rs.getString("state"));
            }
            return address;

        } catch (SQLException e) {
            throw new CustomHttpException(e.getErrorCode(), "Erro SQL: " + e.getMessage());
        }
    }
}

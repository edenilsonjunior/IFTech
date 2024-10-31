package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.queries;

public interface AddressQueries {
    String INSERT = """
                INSERT INTO address (id, street, number, complement, district, zip_code, city, state)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;

    String UPDATE = """
                UPDATE address
                SET street = ?, number = ?, complement = ?, district = ?, zip_code = ?, city = ?, state = ?
                WHERE id = ?;
            """;

    String SELECT_BY_ID = """
            SELECT
                A.id,
                A.street,
                A.number,
                A.complement,
                A.district,
                A.zip_code,
                A.city,
                A.state
            FROM address A
            WHERE A.id = ?;
            """;

}

package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.queries;

public interface CustomerQueries {

    String INSERT = """
            INSERT INTO customer (name, cpf, email, password, phone, active)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    String UPDATE = """
            UPDATE customer
            SET password = ?, phone = ?
            WHERE id = ?;
            """;

    String SELECT_BY_EMAIL = """
            SELECT C.id, 
                   C.name, 
                   C.cpf, 
                   C.email, 
                   C.password, 
                   C.phone, 
                   C.active
            FROM customer C
            WHERE C.email = ?;
            """;

    String EXISTS = "SELECT COUNT(*) FROM customer WHERE %s = ?;";

}

package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao.queries;

public interface ServiceOrderQueries {
    
    String INSERT = """
        INSERT INTO service_order (customer_id, description, status, price, issue_date, end_date, observation, payment_method_id)
        VALUES (?,?,?,?,?,?,?, ?);
    """;

    String UPDATE = """
        UPDATE service_order
        SET description = ?,
            status = ?,
            price = ?,
            issue_date = ?,
            end_date = ?,
            observation = ?,
            payment_method_id = ?
        WHERE id = ?;
        """;

        String SELECT_ORDER_BY_CUSTOMER_ID = """
            SELECT
                SO.id,
                SO.description,
                SO.status,
                SO.price,
                SO.issue_date,
                SO.end_date,
                SO.observation,
                PM.id AS payment_method_id,
                PM.name AS payment_method_name
            FROM service_order SO
            JOIN payment_method PM
                ON PM.id = SO.payment_method_id
            WHERE SO.customer_id = ?;
            """;

            String SELECT_ORDER_BY_ID = """
                SELECT
                    SO.id,
                    SO.description,
                    SO.status,
                    SO.price,
                    SO.issue_date,
                    SO.end_date,
                    SO.observation,
                    PM.id AS payment_method_id,
                    PM.name AS payment_method_name
                FROM service_order SO
                JOIN payment_method PM
                    ON PM.id = SO.payment_method_id
                WHERE SO.id = ?;
                """;


        String EXISTS_BY_ID = " SELECT COUNT(*) FROM service_order WHERE id = ?;";

        String EXISTS_PAYMENT_METHOD_BY_NAME = " SELECT COUNT(*) FROM payment_method WHERE name = ?;";

        String INSERT_PAYMENT_METHOD = " INSERT INTO payment_method (name) VALUES (?);";

        String SELECT_PAYMENT_METHOD_BY_NAME = " SELECT id FROM payment_method WHERE name = ?;";

        String SELECT_PAYMENT_METHODS = " SELECT id, name FROM payment_method;";
}

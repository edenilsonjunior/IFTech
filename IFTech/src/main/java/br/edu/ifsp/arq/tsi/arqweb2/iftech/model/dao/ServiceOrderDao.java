package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.dao;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.OrderStatus;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.ServiceOrder;

import javax.sql.DataSource;
import java.util.List;

public class ServiceOrderDao {

    private final DataSource dataSource;

    public ServiceOrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean create(ServiceOrder order, Customer customer){
        return false;
    }

    public boolean updateStatus(long serviceOrderId, OrderStatus status){
        return false;
    }

    public boolean delete(ServiceOrder order){
        return false;
    }

    public List<ServiceOrder> getOrdersByCustomer(Customer customer){
        return List.of();
    }
}

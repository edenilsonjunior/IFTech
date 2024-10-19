package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order.ServiceOrder;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.PasswordEncoder;

import java.util.List;

public class Customer {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private String phone;
    private boolean active;
    private Address address;
    private List<ServiceOrder> orders;

    public Customer(){}

    public boolean checkPassword(String password){
        var encoded = PasswordEncoder.encode(password);
        return this.password.equals(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ServiceOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ServiceOrder> orders) {
        this.orders = orders;
    }
}

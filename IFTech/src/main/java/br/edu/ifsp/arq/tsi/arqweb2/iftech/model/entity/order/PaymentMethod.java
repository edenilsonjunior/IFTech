package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order;

public class PaymentMethod {

    private long id;
    private String name;

    public PaymentMethod() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

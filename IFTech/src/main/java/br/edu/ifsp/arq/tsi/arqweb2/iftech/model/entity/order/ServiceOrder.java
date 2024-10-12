package br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.order;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServiceOrder {

    private long id;
    private String description;
    private LocalDate issueDate;
    private LocalDate endDate;
    private BigDecimal price;
    private String observation;

    public ServiceOrder(){}

    public ServiceOrder(long id, String description, LocalDate issueDate, LocalDate endDate, BigDecimal price, String observation) {
        this.id = id;
        this.description = description;
        this.issueDate = issueDate;
        this.endDate = endDate;
        this.price = price;
        this.observation = observation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}

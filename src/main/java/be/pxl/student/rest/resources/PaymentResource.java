package be.pxl.student.rest.resources;

import java.time.LocalDateTime;

public class PaymentResource {
    private Long id;
    private String counterAccount;
    private LocalDateTime date;
    private float amount;
    private String currency;
    private String detail;

    public Long getId() {
        return id;
    }

    public String getCounterAccount() {
        return counterAccount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public float getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDetail() {
        return detail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCounterAccount(String counterAccount) {
        this.counterAccount = counterAccount;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

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
}

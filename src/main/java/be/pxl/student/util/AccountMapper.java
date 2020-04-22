package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccountMapper {
    public static List<Account> accountList = new ArrayList<>();
    public static List<Payment> payments = new ArrayList<>();

    public Account mapToAccount(String line) throws InvalidPaymentException {
        String[] paymentInfo = line.split(",");
        if(paymentInfo.length!=7){
            throw new InvalidPaymentException("Invalid number of fields in line");
        }
        var account = new Account();
        account.setIBAN(paymentInfo[1]);
        account.setName(paymentInfo[0]);
        return account;
    }
/*
nu in de paymentMapper en counterAccountMapper
    public static List<Payment> getPaymentsFromLine(String line) {
        List<Payment> payments = new ArrayList<>();
        String[] paymentInfo = line.split(",");
        LocalDateTime date = LocalDateTime.parse(paymentInfo[3], DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US));
        payments.add(new Payment(date, Float.parseFloat(paymentInfo[4]), paymentInfo[5], paymentInfo[6]));
        return payments;
    }*/
}

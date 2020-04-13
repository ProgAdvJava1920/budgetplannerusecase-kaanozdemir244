package be.pxl.student.service;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.impli.AccountDaoImpli;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.EntityManagerUtil;

import javax.ejb.Stateless;
import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class AccountService {
    private AccountDaoImpli accountDao;

    public AccountService() {
        accountDao = new AccountDaoImpli(EntityManagerUtil.createEntityManager());
    }

    public List<Payment> findPaymentsByAccountName(String name) throws AccountNotFoundException {
        var account = accountDao.findAccountByName(name);
        if (account == null) {
            throw new AccountNotFoundException(name);
        }
        return account.getPayments();
    }

    public void addPayments(String name, String counterAccountIBAN, float amout, String detail) throws AccountNotFoundException {
        var account = accountDao.findAccountByName(name);
        if (account == null) {
            throw new AccountNotFoundException(name + " acount not found");
        }
        var counterAccount = accountDao.findAccountByIban(counterAccountIBAN);
        if (counterAccount == null) {
            counterAccount = new Account();
            counterAccount.setIBAN(counterAccountIBAN);
            counterAccount = accountDao.createAccount(counterAccount);
        }
        var payment = new Payment();
        payment.setAccount(counterAccount);
        payment.setAmount(amout);
        payment.setCurrency("EUR");
        payment.setDate(LocalDateTime.now());
        payment.setDetail(detail);
        account.addPayment(payment);
        accountDao.updateAccount(account);
    }
}

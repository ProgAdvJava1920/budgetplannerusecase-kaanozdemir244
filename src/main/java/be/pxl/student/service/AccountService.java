package be.pxl.student.service;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.impli.AccountDaoImpli;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.EntityManagerUtil;

import javax.ejb.Stateless;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Stateless
public class AccountService {
    private AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDaoImpli(EntityManagerUtil.createEntityManager());
    }
    public List<Payment> findPaymentsByAccountName(String name) throws AccountNotFoundException{
        var account = accountDao.findAccountByName(name);
        if (account == null) {
            throw new AccountNotFoundException(name);
        }
        return account.getPayments();
    }
}

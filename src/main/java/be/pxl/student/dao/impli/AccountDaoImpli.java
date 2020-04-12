package be.pxl.student.dao.impli;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.entity.Account;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

public class AccountDaoImpli implements AccountDao {
    // private static final Logger LOGGER = LogManager.getLogger(AccountDao.class);
    private EntityManager entityManager;

    public AccountDaoImpli(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Account findAccountByName(String name) {
        TypedQuery<Account> query = entityManager.createNamedQuery("findByName", Account.class);
        LOGGER.info(String.format("query with name[%s]", name));
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Account findAccountByIban(String iban) {
        TypedQuery<Account> query = entityManager.createNamedQuery("findByIBAN", Account.class);
        LOGGER.info(String.format("query with IBAN [%s]", iban));
        query.setParameter("iban", iban);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Account updateAccount(Account account) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        account = entityManager.merge(account);
        transaction.commit();
        return account;
    }

    @Override
    public Account createAccount(Account account) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(account);
        transaction.commit();
        return account;
    }
}

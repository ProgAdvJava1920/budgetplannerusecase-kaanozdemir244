package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    private static final Logger LOGGER = LogManager.getLogger(BudgetPlannerImporter.class);
    private static PathMatcher cvsMatcher = FileSystems.getDefault().getPathMatcher("glob:**/**.csv");
    private AccountMapper accountMapper = new AccountMapper();
    private CounterAccountMapper counterAccountMapper = new CounterAccountMapper();
    private PaymentMapper paymentMapper = new PaymentMapper();
    private Map<String, Account> createdAccounts = new HashMap<>();
    private EntityManager entityManager;


    Path bankAfschriften = Path.of("C://Users//32488/IdeaProjects/budgetPlannerUseCase-kaanozdemir244/src/main/resources/account_payments.csv");


    public List<Account> accountList = new ArrayList<>();


    public BudgetPlannerImporter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void importCsv(Path path) {
        if (!cvsMatcher.matches(path)) {
            LOGGER.error("Invalid file: .csv expected. Provided {}", path);
            return;
        }
        if (!Files.exists(path)) {
            LOGGER.error("File {} does not exist", path);
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            String line = null;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                try {
                    Payment payment = paymentMapper.map(line);
                    payment.setAccount(getOrCreateAccount(accountMapper.mapToAccount(line)));
                    payment.setCounterAccount(getOrCreateAccount(counterAccountMapper.map(line)));
                    entityManager.persist(payment);
                } catch (InvalidPaymentException ex) {
                    LOGGER.error("Error while mapping line : {}", ex.getMessage());
                }
            }
            transaction.commit();
        } catch (IOException ex) {
            LOGGER.fatal("An error occured while reading : {}", path);
        }
    }

    /*
    if (cvsMatcher.matches(path)) {
        String line = null;
        boolean firstline = true;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            if (firstline) {
                line = reader.readLine();
                firstline = false;
            }
            while ((line = reader.readLine()) != null) {
                try {
                    LOGGER.debug(accountMapper.mapToAccount(line));
                    accountList.add(AccountMapper.mapToAccount(line));
                } catch (InvalidPaymentException e) {
                    LOGGER.error("Error while mapping line: {}", e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.fatal("fatal error occured:{}", path);
        }
    } else {
        LOGGER.error("invalid file .csv expected. Provided: {}", path);
    }
}
*/
    private Account getOrCreateAccount(Account account) {
        if (createdAccounts.containsKey(account.getIBAN())) {
            return createdAccounts.get(account.getIBAN());
        } else {
            entityManager.persist(account);
            createdAccounts.put(account.getIBAN(), account);
            return account;
        }
    }
}

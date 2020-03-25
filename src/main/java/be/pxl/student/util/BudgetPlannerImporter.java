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
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;


/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    private static final Logger LOGGER = LogManager.getLogger(BudgetPlannerImporter.class);
    private static PathMatcher cvsMatcher = FileSystems.getDefault().getPathMatcher("glob:**/**.csv");
    private AccountMapper accountMapper= new AccountMapper();
    private EntityManager entityManager;
    Path bankAfschriften = Path.of("C://Users//32488/IdeaProjects/budgetPlannerUseCase-kaanozdemir244/src/main/resources/account_payments.csv");

    public List<Account> accountList = new ArrayList<>();


    public void importCsv(Path path) {
        if (cvsMatcher.matches(path)) {
            String line = null;
            boolean firstline = true;
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                if (firstline) {
                    line = reader.readLine();
                    firstline = false;
                }
                while ((line = reader.readLine()) != null) {
                    try{
                        LOGGER.debug(accountMapper.mapToAccount(line));
                        accountList.add(AccountMapper.mapToAccount(line));
                    }catch (InvalidPaymentException e){
                        LOGGER.error("Error while mapping line: {}",e.getMessage());
                    }
                }
            } catch (IOException e) {
                LOGGER.fatal("fatal error occured:{}", path);
            }
        } else {
            LOGGER.error("invalid file .csv expected. Provided: {}",path);
        }
    }


}

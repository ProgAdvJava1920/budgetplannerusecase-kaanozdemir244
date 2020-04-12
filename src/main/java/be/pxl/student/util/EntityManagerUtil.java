package be.pxl.student.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Enumeration;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@WebListener
public class EntityManagerUtil implements ServletContextListener {
    private  static EntityManagerFactory entityManagerFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        entityManagerFactory= Persistence.createEntityManagerFactory("budgetplannerdb_pu");
        LOGGER.debug("Persistence started " + LocalDateTime.now());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while(drivers.hasMoreElements()){
            var driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                LOGGER.info(String.format("deregistering jdbc driver %s", driver));
            }catch (SQLException ex){
                LOGGER.fatal(String.format("Error deregistering jdbc driver %s", driver));
            }
            if(entityManagerFactory!=null){
                entityManagerFactory.close();
                LOGGER.info("Persistence finished " + LocalDateTime.now());
            }
        }
    }

    public static EntityManager createEntityManager() {
        if (entityManagerFactory != null) {
            return entityManagerFactory.createEntityManager();
        }
        return null;
    }
}

package org.example.util;

import com.github.fluent.hibernate.cfg.scanner.EntityScanner;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    public static SessionFactory getSessionFactory() {
        logger.info("Start get session factory");
        if (sessionFactory == null) {
            try {
                String[] modelPackages = {"org.example.model"};
                String dbDriver = System.getProperty("database.driver");
                String dbDialect = System.getProperty("database.dialect");
                String dbUrl = System.getProperty("database.url");
                String dbUser = System.getProperty("database.user");
                String dbPassword = System.getProperty("database.password");

                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, dbDriver);
                settings.put(Environment.DIALECT, dbDialect);
                settings.put(Environment.URL, dbUrl);
                settings.put(Environment.USER, dbUser);
                settings.put(Environment.PASS, dbPassword);
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "validate");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);

                EntityScanner.scanPackages(modelPackages).addTo(configuration);
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                ServiceRegistry serviceRegistry = registryBuilder.applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (HibernateException e) {
                logger.error("Unable to create sessionFactory", e);
            }
        }
        return sessionFactory;
    }

    public static void main(String[] args) {
//        HibernateUtil hibernateUtil = new HibernateUtil();
//        HibernateUtil hibernateUtil2 = new HibernateUtil();

        SessionFactory sf = HibernateUtil.getSessionFactory();
        logger.info("success generate sf {}", sf.hashCode());
        Session s = sf.openSession();
        logger.info("get a session to connect to db");
        logger.debug("test debug");
        s.close();
    }
}
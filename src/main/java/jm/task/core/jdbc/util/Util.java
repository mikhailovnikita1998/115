package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jm.task.core.jdbc.model.Users2;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;

public class Util {

    // реализуйте настройку соеденения с БД
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mytest";
    private static final String DB_USERNAME = "root123";
    private static final String DB_PASSWORD = "root";
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            // Настройка свойств базы данных
            Properties databaseProperties = new Properties();
            databaseProperties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            databaseProperties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/mytest");
            databaseProperties.put("hibernate.connection.username", "root123");
            databaseProperties.put("hibernate.connection.password", "root");
            databaseProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            configuration.setProperties(databaseProperties);

            // Добавление класса User
            configuration.addAnnotatedClass(Users2.class);

            // Создание ServiceRegistry и SessionFactory
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            // Логирование ошибки
            e.printStackTrace(); // Рекомендуется использовать логгер
            throw new ExceptionInInitializerError("Failed to initialize session factory: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME,DB_PASSWORD);
            System.out.println("Connected to database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to database");
        }
        return connection;
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

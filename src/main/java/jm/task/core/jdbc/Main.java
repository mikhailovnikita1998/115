package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Создайте соединение с базой данных
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mytest", "root123", "root");

            // Передайте соединение в класс UserDaoJDBCImpl
            UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();

            // Создание таблицы
            userDao.createUsersTable();

            // Добавление пользователя
            userDao.saveUser("John", "Doe", (byte) 25);

            // Удаление пользователя по идентификатору
            userDao.removeUserById(1);

            // Получение всех пользователей
            List<User> allUsers = userDao.getAllUsers();
            System.out.println("Список всех пользователей:");
            for (User user : allUsers) {
                System.out.println(user);
            }

            // Очистка таблицы пользователей
            userDao.cleanUsersTable();

            // Удаление таблицы пользователей
            userDao.dropUsersTable();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Закрываем соединение в конце программы
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


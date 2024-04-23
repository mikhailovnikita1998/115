package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.Users2;


import java.util.List;


public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();

         //Создание таблицы
        userDao.createUsersTable();

         //Добавление пользователя
        userDao.saveUser("John", "Doe", (byte) 25);
        userDao.saveUser("Alice", "Smith", (byte) 30);
        userDao.saveUser("Bob", "Johnson", (byte) 27);

        // Удаление пользователя по идентификатору
        userDao.removeUserById(1);

        // Получение всех пользователей
        List<Users2> allUsers = userDao.getAllUsers();
        System.out.println("Список всех пользователей:");
        for (Users2 user : allUsers) {
            System.out.println(user);
        }

        //Очистка таблицы пользователей
       userDao.cleanUsersTable();

         //Удаление таблицы пользователей
        userDao.dropUsersTable();

        // Закрытие фабрики сессий в конце программы
        userDao.close();
    }
}


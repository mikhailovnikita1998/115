package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.Users2;

import jm.task.core.jdbc.util.Util;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;



public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users2 (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(50), " +
                    "lastName VARCHAR(50), " +
                    "age TINYINT, " +
                    "PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            // Логирование исключения
            System.err.println("Ошибка при создании таблицы пользователей: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM Users2").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            // Логирование исключения
            System.err.println("Ошибка при удалении таблицы пользователей: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Users2 user = new Users2(name, lastName, age);
                session.save(user);
                transaction.commit();
            } catch (Exception e) {
                // Откат транзакции в случае исключения
                if (transaction != null) {
                    transaction.rollback();
                }
                // Логирование исключения
                System.err.println("Ошибка при сохранении пользователя: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Users2 user = session.get(Users2.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            // Логирование исключения
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Users2> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            // Получаем список пользователей из базы данных
            List<Users2> users = session.createQuery("FROM Users2", Users2.class).getResultList();

            // Возвращаем пустой список, если результат равен null
            if (users == null) {
                return Collections.emptyList();
            }

            // Возвращаем список пользователей
            return users;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении списка пользователей из базы данных", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM users2").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close() {
        // Закрываем фабрику сессий
        Util.close();
    }
}

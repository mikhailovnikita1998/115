package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.Users2;

import java.util.List;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable() ;

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<Users2> getAllUsers();

    void cleanUsersTable();
}

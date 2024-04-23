package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.Users2;

import java.sql.SQLException;
import java.util.List;

public interface UserService  {
    void createUsersTable() throws SQLException;

    void dropUsersTable() throws SQLException;

    void saveUser(String name, String lastName, byte age) throws SQLException;

    void removeUserById(long id) throws SQLException;

    List<Users2> getAllUsers() throws SQLException;

    void cleanUsersTable() throws SQLException;
}

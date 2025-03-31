package sk.stasko.service;

import sk.stasko.model.UserEntity;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void addUser(UserEntity user) throws SQLException;
    List<UserEntity> getAllUsers() throws SQLException;
    void deleteAllUsers() throws SQLException;
}

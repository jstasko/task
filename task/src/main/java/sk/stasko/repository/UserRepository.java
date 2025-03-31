package sk.stasko.repository;

import sk.stasko.model.UserEntity;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    void save(UserEntity user) throws SQLException;
    List<UserEntity> findAll() throws SQLException;
    void deleteAll() throws SQLException;
}

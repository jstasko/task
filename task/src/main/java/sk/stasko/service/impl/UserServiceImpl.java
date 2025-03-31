package sk.stasko.service.impl;

import sk.stasko.model.UserEntity;
import sk.stasko.repository.UserRepository;
import sk.stasko.service.UserService;
import sk.stasko.util.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public void addUser(UserEntity user) throws SQLException {
        repository.save(user);
        logger.fine("User added through service: " + user);
    }

    public List<UserEntity> getAllUsers() throws SQLException {
        return repository.findAll();
    }

    public void deleteAllUsers() throws SQLException {
        repository.deleteAll();
    }
}

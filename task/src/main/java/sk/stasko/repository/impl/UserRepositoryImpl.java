package sk.stasko.repository.impl;

import sk.stasko.config.AppConfig;
import sk.stasko.config.SqlScriptLoader;
import sk.stasko.exception.AppException;
import sk.stasko.model.UserEntity;
import sk.stasko.repository.UserRepository;
import sk.stasko.util.AppConstants;
import sk.stasko.util.LoggerFactory;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UserRepositoryImpl implements UserRepository {
    private final String url;
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    public UserRepositoryImpl() {
        this.url = AppConfig.get(AppConstants.JDBC_URL_PROPERTY);
        String createTableSql = SqlScriptLoader.loadInitScript();
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSql);
            logger.info("Database initialized.");
        } catch (SQLException e) {
            throw new AppException("Failed to initialize database", e);
        }
    }

    public void save(UserEntity user) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(AppConstants.SQL_INSERT_USER)) {
            stmt.setInt(1, user.id());
            stmt.setString(2, sanitize(user.guid()));
            stmt.setString(3, sanitize(user.name()));
            stmt.executeUpdate();
            logger.info("User saved: " + user);
        }
    }

    public List<UserEntity> findAll() throws SQLException {
        List<UserEntity> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(AppConstants.SQL_SELECT_ALL)) {
            while (rs.next()) {
                users.add(new UserEntity(
                        rs.getInt("USER_ID"),
                        rs.getString("USER_GUID"),
                        rs.getString("USER_NAME")));
            }
            logger.info("Retrieved all users");
        }
        return users;
    }

    public void deleteAll() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(AppConstants.SQL_DELETE_ALL);
            logger.info("All users deleted");
        }
    }

    private String sanitize(String input) {
        return input == null ? null : input.replaceAll("[^a-zA-Z0-9 ]", "").trim();
    }
}

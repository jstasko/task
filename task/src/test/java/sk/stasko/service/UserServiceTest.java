package sk.stasko.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stasko.model.UserEntity;
import sk.stasko.repository.UserRepository;
import sk.stasko.repository.impl.UserRepositoryImpl;
import sk.stasko.service.impl.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        UserRepository repository = new UserRepositoryImpl();
        userService = new UserServiceImpl(repository);
    }

    @Test
    public void testAddAndGetUser() throws SQLException {
        // Arrange
        UserEntity user = new UserEntity(101, "guid-101", "Alice");

        // Act
        userService.addUser(user);
        List<UserEntity> users = userService.getAllUsers();

        // Assert
        assertThat(users)
                .isNotEmpty()
                .anySatisfy(u -> {
                    assertThat(u.id()).isEqualTo(101);
                    assertThat(u.guid()).isEqualTo("guid101");
                    assertThat(u.name()).isEqualTo("Alice");
                });
    }
}

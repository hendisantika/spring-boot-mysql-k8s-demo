package id.my.hendisantika.mysqlk8sdemo.repository;

import id.my.hendisantika.mysqlk8sdemo.config.AbstractContainerBaseTest;
import id.my.hendisantika.mysqlk8sdemo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryIntegrationTest extends AbstractContainerBaseTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() {
        // Given
        User user = new User();
        user.setName("Itadori Yuji");
        user.setCountry("JPN");

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Itadori Yuji");
        assertThat(savedUser.getCountry()).isEqualTo("JPN");
    }

    @Test
    void shouldFindAllUsers() {
        // Given
        User user1 = new User();
        user1.setName("Itadori Yuji");
        user1.setCountry("JPN");

        User user2 = new User();
        user2.setName("Satoru Gojo");
        user2.setCountry("JPN");

        userRepository.save(user1);
        userRepository.save(user2);

        // When
        List<User> users = userRepository.findAll();

        // Then
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
    }

}
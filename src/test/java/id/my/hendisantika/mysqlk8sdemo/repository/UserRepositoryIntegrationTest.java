package id.my.hendisantika.mysqlk8sdemo.repository;

import id.my.hendisantika.mysqlk8sdemo.config.AbstractContainerBaseTest;
import id.my.hendisantika.mysqlk8sdemo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

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
}
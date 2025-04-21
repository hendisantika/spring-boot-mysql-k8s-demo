package id.my.hendisantika.mysqlk8sdemo.repository;

import id.my.hendisantika.mysqlk8sdemo.config.AbstractContainerBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryIntegrationTest extends AbstractContainerBaseTest {

    @Autowired
    private UserRepository userRepository;


}
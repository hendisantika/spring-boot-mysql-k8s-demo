package id.my.hendisantika.mysqlk8sdemo.controller;

import id.my.hendisantika.mysqlk8sdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-mysql-k8s-demo
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/04/25
 * Time: 22.33
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
}

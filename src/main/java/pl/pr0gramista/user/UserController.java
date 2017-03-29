package pl.pr0gramista.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping("/user/")
    public User getUser(User user) {
        return user;
    }
}

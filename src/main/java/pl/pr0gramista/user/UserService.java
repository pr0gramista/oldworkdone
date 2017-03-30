package pl.pr0gramista.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import pl.pr0gramista.exception.StrangeOAuthException;

import java.util.Map;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public synchronized User getUserForOAuthentication(OAuth2Authentication authentication) {
        if (authentication != null) {
            Map<String, String> details = (Map) authentication.getUserAuthentication().getDetails();

            if (details.containsKey("sub")) {
                String uid = details.get("sub");

                return userRepository.findByUid(uid).orElseGet(() -> createNewUser(uid));
            } else
                throw new StrangeOAuthException("Property 'sub' was not found, therefore there is lack of uid for user");
        }
        return null;
    }

    private User createNewUser(String uid) {
        User newUser = new User(uid);
        userRepository.save(newUser);
        return newUser;
    }

    public void addCoins(User user, int amount) {
        user.setCoins(user.getCoins() + amount);

        userRepository.save(user);
    }

    public void addExperience(User user, int amount) {
        user.setExperience(user.getExperience() + amount);

        //TODO: new level
        user.setLevel(user.getExperience() / 100);

        userRepository.save(user);
    }
}

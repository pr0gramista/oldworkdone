package pl.pr0gramista.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import pl.pr0gramista.exception.StrangeOAuthException;
import pl.pr0gramista.generators.LevelGenerator;

import java.util.Map;

@Service
public class UserService {

    private UserRepository userRepository;

    private LevelGenerator levelGenerator;

    public UserService(@Autowired UserRepository userRepository, @Autowired LevelGenerator levelGenerator) {
        this.userRepository = userRepository;
        this.levelGenerator = levelGenerator;
    }

    public synchronized User getUserForOAuthentication(OAuth2Authentication authentication) {
        if (authentication != null) {
            Map<String, String> details = (Map) authentication.getUserAuthentication().getDetails();

            if (details.containsKey("sub")) {
                String uid = details.get("sub");

                //TODO: think about the details
                User user = userRepository.findByUid(uid).orElseGet(() -> createNewUser(uid));
                user.setPhoto(details.get("picture"));
                user.setName(details.get("name"));
                return user;
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

        user.setLevel(levelGenerator.getLevel(user.getExperience()));

        userRepository.save(user);
    }
}

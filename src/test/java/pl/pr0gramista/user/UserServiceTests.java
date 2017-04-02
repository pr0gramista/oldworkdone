package pl.pr0gramista.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.pr0gramista.generators.LevelGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    @Mock
    UserRepository userRepository;

    @Mock
    LevelGenerator levelGenerator;

    UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserService(userRepository, levelGenerator);
    }

    @Test
    public void giveUserCoins() throws Exception {
        User user = new User();
        assertThat(user.getCoins(), is(0));

        userService.addCoins(user, 1000);

        assertThat(user.getCoins(), is(1000));
    }

    @Test
    public void giveUserExperience() throws Exception {
        when(levelGenerator.getLevel(anyInt())).thenReturn(5);

        User user = new User();
        assertThat(user.getExperience(), is(0));

        userService.addExperience(user, 1000);

        assertThat(user.getExperience(), is(1000));
    }
}

package pl.pr0gramista.habit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.pr0gramista.enums.CoinAmount;
import pl.pr0gramista.enums.Color;
import pl.pr0gramista.enums.ExperienceAmount;
import pl.pr0gramista.generators.CoinGenerator;
import pl.pr0gramista.generators.ExperienceGenerator;
import pl.pr0gramista.user.User;
import pl.pr0gramista.user.UserService;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HabitServiceTest {

    HabitService habitService;

    @Mock
    UserService userService;

    @Mock
    CoinGenerator coinGenerator;

    @Mock
    ExperienceGenerator experienceGenerator;

    @Before
    public void setUp() throws Exception {
        habitService = new HabitService(userService, coinGenerator, experienceGenerator);
    }

    @Test
    public void complete() throws Exception {
        User user = new User();

        Habit habitToComplete = new Habit.HabitBuilder("CompleteMe!")
                .expReward(ExperienceAmount.MEDIUM)
                .coinReward(CoinAmount.SMALL)
                .color(Color.BLUE)
                .tags("tag1", "tag2")
                .owner(user)
                .build();

        when(coinGenerator.generate(CoinAmount.SMALL)).thenReturn(500);
        when(experienceGenerator.generate(ExperienceAmount.MEDIUM)).thenReturn(1000);

        HabitCompletion habitCompletion = habitService.complete(habitToComplete);
        assertThat(habitCompletion.getExperience(), is(1000));
        assertThat(habitCompletion.getCoins(), is(500));

        //Assuming that we are not running true potato PC
        assertThat(habitCompletion.getDateTime().truncatedTo(ChronoUnit.SECONDS), is(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
        assertThat(habitCompletion.getHabit(), is(habitToComplete));

        verify(userService, atMost(1)).addCoins(user, 500);
        verify(userService, atMost(1)).addExperience(user, 1000);
    }
}

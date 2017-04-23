package pl.pr0gramista.todo;

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
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

    TodoService todoService;

    @Mock
    UserService userService;

    @Mock
    CoinGenerator coinGenerator;

    @Mock
    ExperienceGenerator experienceGenerator;

    @Before
    public void setUp() throws Exception {
        todoService = new TodoService(userService, coinGenerator, experienceGenerator);
    }

    @Test
    public void complete() throws Exception {
        User user = new User();

        Task task1 = new Task("Make a sandwich");
        Task task2 = new Task("Make a hotdog");

        Todo todoToComplete = new Todo.TodoBuilder("Wow")
                .expReward(ExperienceAmount.MEDIUM)
                .coinReward(CoinAmount.HIGH)
                .color(Color.BLUE)
                .tags("tag1")
                .owner(user)
                .withTask(task1)
                .withTask(task2)
                .build();

        when(coinGenerator.generate(CoinAmount.HIGH)).thenReturn(1500);
        when(experienceGenerator.generate(ExperienceAmount.MEDIUM)).thenReturn(1000);

        Optional<TodoCompletion> todoCompletionOptional = todoService.complete(todoToComplete);
        assertThat(todoCompletionOptional.isPresent(), is(true));

        TodoCompletion todoCompletion = todoCompletionOptional.get();
        assertThat(todoCompletion.getExperience(), is(1000));
        assertThat(todoCompletion.getCoins(), is(1500));

        //Assuming that we are not running true potato PC
        assertThat(todoCompletion.getDateTime().truncatedTo(ChronoUnit.SECONDS), is(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
        assertThat(todoCompletion.getTodo(), is(todoToComplete));

        verify(userService, atMost(1)).addCoins(user, 1500);
        verify(userService, atMost(1)).addExperience(user, 1000);
    }
}

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
public class TaskServiceTest {

    TaskService taskService;

    @Mock
    UserService userService;

    @Mock
    CoinGenerator coinGenerator;

    @Mock
    ExperienceGenerator experienceGenerator;

    @Before
    public void setUp() throws Exception {
        taskService = new TaskService(userService, coinGenerator, experienceGenerator);
    }

    @Test
    public void complete() throws Exception {
        User user = new User();

        Task taskToComplete = new Task("Make a sandwich");
        Task task2 = new Task("Make a hotdog");

        Todo todo = new Todo.TodoBuilder("Wow")
                .expReward(ExperienceAmount.MEDIUM)
                .coinReward(CoinAmount.MEDIUM)
                .color(Color.BLUE)
                .tags("tag1")
                .owner(user)
                .withTask(taskToComplete)
                .withTask(task2)
                .build();

        when(coinGenerator.generate(CoinAmount.MEDIUM)).thenReturn(1000);
        when(experienceGenerator.generate(ExperienceAmount.MEDIUM)).thenReturn(1000);

        Optional<TaskCompletion> taskCompletionOptional = taskService.completeTask(taskToComplete, todo);
        assertThat(taskCompletionOptional.isPresent(), is(true));

        TaskCompletion taskCompletion = taskCompletionOptional.get();
        assertThat(taskCompletion.getExperience(), is(1000));
        assertThat(taskCompletion.getCoins(), is(1000));

        //Assuming that we are not running true potato PC
        assertThat(taskCompletion.getDateTime().truncatedTo(ChronoUnit.SECONDS), is(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
        assertThat(taskCompletion.getTask(), is(taskToComplete));

        verify(userService, atMost(1)).addCoins(user, 1000);
        verify(userService, atMost(1)).addExperience(user, 1000);
    }
}

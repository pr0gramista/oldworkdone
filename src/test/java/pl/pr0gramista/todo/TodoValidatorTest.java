package pl.pr0gramista.todo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import pl.pr0gramista.enums.CoinAmount;
import pl.pr0gramista.enums.Color;
import pl.pr0gramista.enums.ExperienceAmount;
import pl.pr0gramista.user.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(JUnit4.class)
public class TodoValidatorTest {

    TodoValidator todoValidator;
    TaskValidator taskValidator;

    @Before
    public void setUp() throws Exception {
        taskValidator = new TaskValidator();
        todoValidator = new TodoValidator(taskValidator);
    }

    @Test
    public void textTooLong() throws Exception {
        Todo todo = new Todo.TodoBuilder(getLongString())
                .tags("tag1", "tag2")
                .color(Color.RED)
                .expReward(ExperienceAmount.HIGH)
                .coinReward(CoinAmount.HIGH)
                .withTask(new Task("make a sandwich"))
                .build();

        Errors errors = new BeanPropertyBindingResult(todo, "todo");

        todoValidator.validate(todo, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("title").getCode(), is("title.toolong"));
    }

    @Test
    public void nullCoinReward() throws Exception {
        Todo todo = new Todo.TodoBuilder("title")
                .tags("tag1", "tag2")
                .color(Color.RED)
                .expReward(ExperienceAmount.HIGH)
                .withTask(new Task("make a sandwich"))
                .build();

        Errors errors = new BeanPropertyBindingResult(todo, "todo");

        todoValidator.validate(todo, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("coinReward").getCode(), is("coinReward.empty"));
    }

    @Test
    public void nullExpReward() throws Exception {
        Todo todo = new Todo.TodoBuilder("title")
                .tags("tag1", "tag2")
                .color(Color.RED)
                .coinReward(CoinAmount.HIGH)
                .withTask(new Task("make a sandwich"))
                .build();

        Errors errors = new BeanPropertyBindingResult(todo, "todo");

        todoValidator.validate(todo, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("expReward").getCode(), is("expReward.empty"));
    }

    @Test
    public void nullColor() throws Exception {
        Todo todo = new Todo.TodoBuilder("title")
                .tags("tag1", "tag2")
                .expReward(ExperienceAmount.HIGH)
                .coinReward(CoinAmount.HIGH)
                .withTask(new Task("make a sandwich"))
                .build();

        Errors errors = new BeanPropertyBindingResult(todo, "todo");

        todoValidator.validate(todo, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("color").getCode(), is("color.empty"));
    }

    @Test
    public void forcedUser() throws Exception {
        Todo todo = new Todo.TodoBuilder("title")
                .tags("tag1", "tag2")
                .color(Color.RED)
                .expReward(ExperienceAmount.HIGH)
                .coinReward(CoinAmount.HIGH)
                .owner(new User())
                .withTask(new Task("make a sandwich"))
                .build();

        Errors errors = new BeanPropertyBindingResult(todo, "todo");
        todoValidator.validate(todo, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("owner").getCode(), is("owner.assignedbyuser"));
    }

    @Test
    public void tagFlood() throws Exception {
        Todo todo = new Todo.TodoBuilder("title")
                .color(Color.RED)
                .expReward(ExperienceAmount.HIGH)
                .coinReward(CoinAmount.HIGH)
                .withTask(new Task("make a sandwich"))
                .build();

        List<String> tags = IntStream.range(0, 40).mapToObj(Integer::toString).collect(Collectors.toList());
        todo.setTags(tags);

        Errors errors = new BeanPropertyBindingResult(todo, "todo");
        todoValidator.validate(todo, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("tags").getCode(), is("tags.toobig"));
    }

    @Test
    public void tagTooLong() throws Exception {
        Todo todo = new Todo.TodoBuilder("title")
                .tags(getLongString(), "tag2")
                .color(Color.RED)
                .expReward(ExperienceAmount.HIGH)
                .coinReward(CoinAmount.HIGH)
                .withTask(new Task("make a sandwich"))
                .build();

        Errors errors = new BeanPropertyBindingResult(todo, "todo");
        todoValidator.validate(todo, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("tags").getCode(), is("tags.toolong"));
    }

    private String getLongString() {
        char[] array = new char[512];
        Arrays.fill(array, 'x');
        return new String(array);
    }
}

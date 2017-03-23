package pl.pr0gramista.habit;

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
public class HabitValidatorTest {

    HabitValidator habitValidator;

    @Before
    public void setUp() throws Exception {
        habitValidator = new HabitValidator();
    }

    @Test
    public void textTooLong() throws Exception {
        Habit habit = new Habit.HabitBuilder(getLongString())
                .tags("tag1", "tag2")
                .color(Color.RED)
                .expReward(ExperienceAmount.HIGH)
                .coinReward(CoinAmount.HIGH)
                .build();

        Errors errors = new BeanPropertyBindingResult(habit, "habit");

        habitValidator.validate(habit, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("text").getCode(), is("text.toolong"));
    }

    @Test
    public void nullCoinReward() throws Exception {
        Habit habit = new Habit.HabitBuilder("example text")
                .tags("tag1", "tag2")
                .color(Color.RED)
                .expReward(ExperienceAmount.HIGH)
                .build();

        Errors errors = new BeanPropertyBindingResult(habit, "habit");

        habitValidator.validate(habit, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("coinReward").getCode(), is("coinReward.empty"));
    }

    @Test
    public void nullExpReward() throws Exception {
        Habit habit = new Habit.HabitBuilder("example text")
                .tags("tag1", "tag2")
                .color(Color.RED)
                .coinReward(CoinAmount.HIGH)
                .build();

        Errors errors = new BeanPropertyBindingResult(habit, "habit");

        habitValidator.validate(habit, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("expReward").getCode(), is("expReward.empty"));
    }

    @Test
    public void nullColor() throws Exception {
        Habit habit = new Habit.HabitBuilder("example text")
                .tags("tag1", "tag2")
                .expReward(ExperienceAmount.HIGH)
                .coinReward(CoinAmount.HIGH)
                .build();

        Errors errors = new BeanPropertyBindingResult(habit, "habit");

        habitValidator.validate(habit, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("color").getCode(), is("color.empty"));
    }

    @Test
    public void forcedUser() throws Exception {
        Habit habit = new Habit.HabitBuilder("example text")
                .tags("tag1", "tag2")
                .expReward(ExperienceAmount.HIGH)
                .coinReward(CoinAmount.HIGH)
                .color(Color.RED)
                .owner(new User())
                .build();

        Errors errors = new BeanPropertyBindingResult(habit, "habit");
        habitValidator.validate(habit, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("owner").getCode(), is("owner.assignedbyuser"));
    }

    @Test
    public void tagFlood() throws Exception {
        Habit habit = new Habit.HabitBuilder("example text")
                .expReward(ExperienceAmount.HIGH)
                .coinReward(CoinAmount.HIGH)
                .color(Color.GREEN)
                .build();

        List<String> tags = IntStream.range(0, 40).mapToObj(Integer::toString).collect(Collectors.toList());
        habit.setTags(tags);

        Errors errors = new BeanPropertyBindingResult(habit, "habit");
        habitValidator.validate(habit, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("tags").getCode(), is("tags.toobig"));
    }

    @Test
    public void tagTooLong() throws Exception {
        Habit habit = new Habit.HabitBuilder("example text")
                .expReward(ExperienceAmount.HIGH)
                .tags(getLongString(), "wow")
                .coinReward(CoinAmount.HIGH)
                .color(Color.RED)
                .build();

        Errors errors = new BeanPropertyBindingResult(habit, "habit");
        habitValidator.validate(habit, errors);

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

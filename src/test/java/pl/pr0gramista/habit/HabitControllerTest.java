package pl.pr0gramista.habit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pr0gramista.enums.CoinAmount;
import pl.pr0gramista.enums.Color;
import pl.pr0gramista.enums.ExperienceAmount;
import pl.pr0gramista.user.User;
import pl.pr0gramista.user.UserService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class HabitControllerTest {

    @Mock
    private HabitRepository habitRepository;

    @Mock
    private UserService userService;

    private HabitController habitController;

    @Mock
    private HabitValidator habitValidator;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before() {
        habitController = new HabitController(habitRepository, habitValidator);
        mockMvc = MockMvcBuilders
                .standaloneSetup(habitController)
                .build();
    }

    @Test
    public void create() throws Exception {
        //This is used as a request body
        Habit modifiedHabit = new Habit.HabitBuilder("text is awesome")
                .expReward(ExperienceAmount.HIGH)
                .coinReward(CoinAmount.HIGH)
                .color(Color.RED)
                .tags("tag1")
                .build();

        when(habitRepository.save(any(Habit.class))).thenReturn(new Habit.HabitBuilder(modifiedHabit).id(1).build());

        mockMvc.perform(post("/habit/")
                .content(mapper.writeValueAsString(modifiedHabit))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        ArgumentCaptor<Habit> captor = ArgumentCaptor.forClass(Habit.class);
        verify(habitRepository, atMost(1)).save(captor.capture());
        assertThat(captor.getValue(), allOf(
                hasProperty("id", is(nullValue())),
                hasProperty("expReward", is(ExperienceAmount.HIGH)),
                hasProperty("coinReward", is(CoinAmount.HIGH)),
                hasProperty("text", is("text is awesome")),
                hasProperty("color", is(Color.RED)),
                hasProperty("owner", is(notNullValue()))
        ));
    }

    @Test
    public void read() throws Exception {
        //Just one habit
        List<Habit> habit = new LinkedList<>();
        habit.add(new Habit.HabitBuilder("Text 4")
                .expReward(ExperienceAmount.MEDIUM)
                .coinReward(CoinAmount.SMALL)
                .color(Color.BLUE)
                .tags("tag1", "tag2")
                .build());

        when(habitRepository.findAllByOwner(any())).thenReturn(habit);

        mockMvc.perform(get("/habit/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[{'expReward': 'MEDIUM', 'coinReward': 'SMALL', 'text': 'Text 4', 'color': 'BLUE', 'tags': ['tag1', 'tag2']}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        Habit habitToModify = new Habit.HabitBuilder("Text 4")
                .expReward(ExperienceAmount.MEDIUM)
                .coinReward(CoinAmount.SMALL)
                .color(Color.BLUE)
                .tags("tag1")
                .build();

        when(habitRepository.findOneByIdAndOwner(eq(1L), any(User.class))).thenReturn(Optional.of(habitToModify));

        //This is used as a request body
        Habit modifiedHabit = new Habit.HabitBuilder("Yay!")
                .color(Color.GREEN)
                .expReward(ExperienceAmount.HIGH)
                .coinReward(CoinAmount.MEDIUM)
                .tags("tag2")
                .build();

        mockMvc.perform(put("/habit/1")
                .content(mapper.writeValueAsString(modifiedHabit))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        ArgumentCaptor<Habit> captor = ArgumentCaptor.forClass(Habit.class);
        verify(habitRepository, atMost(1)).save(captor.capture());

        assertThat(captor.getValue(), allOf(
                hasProperty("id", is(1L)),
                hasProperty("expReward", is(ExperienceAmount.HIGH)),
                hasProperty("coinReward", is(CoinAmount.MEDIUM)),
                hasProperty("text", is("Yay!")),
                hasProperty("color", is(Color.GREEN)),
                hasProperty("owner", is(notNullValue())),
                hasProperty("tags", hasItem("tag2"))
        ));
    }

    @Test
    public void deleteT() throws Exception {
        mockMvc.perform(delete("/habit/1"))
                .andExpect(status().isOk());

        verify(habitRepository, atLeastOnce()).removeByIdAndOwner(eq(1L), any(User.class));
    }
}

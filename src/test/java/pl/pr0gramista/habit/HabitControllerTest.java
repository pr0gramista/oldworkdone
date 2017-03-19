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
import pl.pr0gramista.habits.Habit;
import pl.pr0gramista.habits.HabitController;
import pl.pr0gramista.habits.HabitRepository;
import pl.pr0gramista.user.User;
import pl.pr0gramista.user.UserService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private MockMvc mockMvc;
    private List<Habit> defaultHabits;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before() {
        habitController = new HabitController(habitRepository, userService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(habitController)
                .build();

        //Generate habits
        defaultHabits =
                IntStream.range(0, 5).mapToObj(value ->
                        new Habit(
                                100 * value,
                                150 * value,
                                "Title " + String.valueOf(value),
                                "Description " + String.valueOf(value)))
                        .collect(Collectors.toList());
    }

    @Test
    public void create() throws Exception {
        //This is used as a request body
        Habit modifiedHabit = new Habit.HabitBuilder("6")
                .description("xxwxw")
                .expReward(400.23F)
                .coinReward(1.0F)
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
                hasProperty("expReward", is(400.23F)),
                hasProperty("coinReward", is(1.0F)),
                hasProperty("title", is("6")),
                hasProperty("description", is("xxwxw")),
                hasProperty("owner", is(notNullValue()))
        ));
    }

    @Test
    public void read() throws Exception {
        //Just one habit
        List<Habit> habit = new LinkedList<>();
        habit.add(new Habit.HabitBuilder("Title 4")
                .description("Description 4")
                .expReward(400F)
                .coinReward(600F)
                .build());

        when(habitRepository.findAllByOwner(any())).thenReturn(habit);

        mockMvc.perform(get("/habit/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[{'expReward':400.0, 'coinReward':600.0, 'title': 'Title 4', 'description': 'Description 4'}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        List<Habit> habits = new LinkedList<>(defaultHabits);
        Habit habitToModify = habits.get(1);

        when(habitRepository.findOneByIdAndOwner(eq(1L), any(User.class))).thenReturn(Optional.of(habitToModify));

        //This is used as a request body
        Habit modifiedHabit = new Habit.HabitBuilder("6")
                .description("xxwxw")
                .expReward(400.23F)
                .coinReward(1.0F)
                .build();

        mockMvc.perform(put("/habit/1")
                .content(mapper.writeValueAsString(modifiedHabit))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        ArgumentCaptor<Habit> captor = ArgumentCaptor.forClass(Habit.class);
        verify(habitRepository, atMost(1)).save(captor.capture());

        assertThat(captor.getValue(), allOf(
                hasProperty("id", is(1L)),
                hasProperty("expReward", is(400.23F)),
                hasProperty("coinReward", is(1.0F)),
                hasProperty("title", is("6")),
                hasProperty("description", is("xxwxw")),
                hasProperty("owner", is(notNullValue()))
        ));
    }

    @Test
    public void deleteT() throws Exception {
        mockMvc.perform(delete("/habit/1"))
                .andExpect(status().isOk());

        verify(habitRepository, atLeastOnce()).removeByIdAndOwner(eq(1L), any(User.class));
    }
}

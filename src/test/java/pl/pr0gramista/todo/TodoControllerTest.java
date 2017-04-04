package pl.pr0gramista.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import pl.pr0gramista.user.User;
import pl.pr0gramista.user.UserService;

import java.util.Collections;
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
public class TodoControllerTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TodoValidator todoValidator;

    @Mock
    private UserService userService;

    private TodoController todoController;
    private MockMvc mockMvc;

    private Todo exampleTodo;
    private String exampleTodoJson;
    private Todo exampleTodo2;
    private String exampleTodoJson2;

    @Before
    public void before() throws JsonProcessingException {
        todoController = new TodoController(todoRepository, taskRepository, todoValidator);
        mockMvc = MockMvcBuilders
                .standaloneSetup(todoController)
                .build();

        ObjectMapper mapper = new ObjectMapper();

        //Make first example
        exampleTodo = new Todo.TodoBuilder("Summer time")
                .withTask(new Task("Ride a bike"))
                .withTask(new Task("Clean garage"))
                .withTask(new Task("Learn to play"))
                .build();
        exampleTodoJson = mapper.writeValueAsString(exampleTodo);
        System.out.println(exampleTodoJson);

        //Make second example
        exampleTodo2 = new Todo.TodoBuilder("Winter time")
                .withTask(new Task("Take a long nap"))
                .withTask(new Task("Throw snowballs"))
                .withTask(new Task("Make a snowman"))
                .build();
        exampleTodoJson2 = mapper.writeValueAsString(exampleTodo2);
        System.out.println(exampleTodoJson2);
    }

    @Test
    public void create() throws Exception {
        when(todoRepository.save(any(Todo.class))).thenReturn(new Todo.TodoBuilder(exampleTodo).id(1).build());

        mockMvc.perform(post("/todo/")
                .content(exampleTodoJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //We can't use simply eq(exampleTodo) because User is being injected
        ArgumentCaptor<Todo> argument = ArgumentCaptor.forClass(Todo.class);

        verify(taskRepository, atMost(1)).save(exampleTodo.getTasks());
        verify(todoRepository).save(argument.capture());
        assertThat(argument.getValue(), allOf(
                hasProperty("id", is(nullValue())),
                hasProperty("title", is(exampleTodo.getTitle())),
                hasProperty("tasks", is(exampleTodo.getTasks())),
                hasProperty("owner", is(notNullValue()))
        ));
    }

    @Test
    public void read() throws Exception {
        when(todoRepository.findAllByOwner(any(User.class))).thenReturn(Collections.singletonList(exampleTodo));

        mockMvc.perform(get("/todo/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[" + exampleTodoJson + "]"))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        when(todoRepository.findOneByIdAndOwner(eq(1L), any(User.class))).thenReturn(Optional.of(exampleTodo));

        mockMvc.perform(put("/todo/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(exampleTodoJson2))
                .andExpect(status().isOk());

        Todo exampleTodo2WithId = new Todo.TodoBuilder(exampleTodo2).id(1).build();
        verify(taskRepository, atMost(1)).save(exampleTodo2WithId.getTasks());
        verify(todoRepository, atMost(1)).save(exampleTodo2WithId);
    }

    @Test
    public void deleteT() throws Exception {
        mockMvc.perform(delete("/todo/1"))
                .andExpect(status().isOk());

        verify(todoRepository, atLeastOnce()).removeByIdAndOwner(eq(1L), any(User.class));
    }
}

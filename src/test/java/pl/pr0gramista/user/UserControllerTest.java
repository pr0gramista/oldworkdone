package pl.pr0gramista.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pr0gramista.FakeUserArgumentResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    UserController userController = new UserController();

    MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setCustomArgumentResolvers(new FakeUserArgumentResolver())
                .build();
    }

    @Test
    public void contentIsGood() throws Exception {
        mockMvc.perform(get("/user/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(content().json(
                        "{\n" +
                                "  \"uid\": \"0284024804\",\n" +
                                "  \"coins\": 42,\n" +
                                "  \"experience\": 987,\n" +
                                "  \"level\": 2,\n" +
                                "  \"photo\": \"wowphoto.png\",\n" +
                                "  \"name\": \"Kamil Buntownik\"\n" +
                                "}"
                ));
    }
}

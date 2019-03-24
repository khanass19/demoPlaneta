package planeta.kino.demo.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import planeta.kino.demo.controller.UserController;
import planeta.kino.demo.entity.User;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestUserController {

    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testLoginUser() throws Exception {
        User user = new User();
        user.setEmail("admin");
        user.setPassword("admin");
        final String inputJson = mapToJson(user);

        final MvcResult mvcResult = mockMvc.perform(post("/public/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isOk()).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test(expected = org.springframework.web.util.NestedServletException.class)
    public void testLoginUserNegative() throws Exception {
        User user = new User();
        user.setEmail("admin");
        user.setPassword("notExistingAdmin");
        final String inputJson = mapToJson(user);

        final MvcResult mvcResult = mockMvc.perform(post("/public/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().is5xxServerError()).andReturn();
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}

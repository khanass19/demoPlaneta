package planeta.kino.demo.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import planeta.kino.demo.DemoApplication;
import planeta.kino.demo.controller.FIlmController;
import planeta.kino.demo.entity.Film;
import planeta.kino.demo.service.FilmService;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(DemoApplication.class)
public class TestFilmController {

    @InjectMocks
    private FIlmController fIlmController;

    @Mock
    private FilmService filmService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(fIlmController).build();
    }

    @Test
    public void testSave() throws Exception {
        Film film = new Film();
        film.setName("filmTest");
        film.setLengthInSeconds(10000);
        final String inputJson = mapToJson(film);

        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/film/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        final int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}

package planeta.kino.demo.service.serviceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import planeta.kino.demo.DemoApplication;
import planeta.kino.demo.controller.FIlmController;
import planeta.kino.demo.entity.Film;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(DemoApplication.class)
public class TestFilmService {

    @InjectMocks
    private FIlmController fIlmController;

    @Mock
    private FIlmServiceImpl filmService;

    private MockMvc mockMvc;

    private List<Film> listOfTestFilms;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(fIlmController).build();
    }

    @Before
    public void buildTestData() {
        Film film = Film.builder()
                .setName("testFilm01")
                .setLengthInSeconds(10_000)
                .build();
        Film filmSecond = Film.builder()
                .setName("testFilm02")
                .setLengthInSeconds(10_000_000)
                .build();
        listOfTestFilms = new ArrayList<>();
        listOfTestFilms.add(film);
        listOfTestFilms.add(filmSecond);
    }

    @Test
    public void testGetAll() throws Exception {
        when(filmService.getAll()).thenReturn(listOfTestFilms);
        mockMvc.perform(get("/films").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("testFilm01")))
                .andExpect(jsonPath("$[1].name", is("testFilm02")));
    }



}

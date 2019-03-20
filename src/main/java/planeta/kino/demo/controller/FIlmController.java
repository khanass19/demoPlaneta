package planeta.kino.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import planeta.kino.demo.entity.Film;
import planeta.kino.demo.repository.FilmRepository;
import planeta.kino.demo.service.FilmService;
import planeta.kino.demo.service.serviceImpl.FIlmServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
public class FIlmController {

    private static final Logger logger =
            LogManager.getLogger(FIlmController.class);


    @Autowired
    private FilmService filmService;

    @PostMapping(value = "/film")
    public Film saveFilm(@RequestBody @Valid Film film) throws InterruptedException {
        return filmService.saveFilm(film);
    }

    @GetMapping(value = "/films")
    public List<Film> getAllFilms() {
        List<Film> films = filmService.getAll();
        try {

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return films;
    }

    @GetMapping(value = "/films/pageable")
    @PreAuthorize("hasAnyAuthority('USER')")
    public Page<Film> getAllFilmsUsingPageable(@PageableDefault(sort = {"id"},
            direction = Sort.Direction.ASC) Pageable pageable) {
        return filmService.getAllByPage(pageable);
    }

    @DeleteMapping(value = "/film/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public boolean getAllFilms(@PathVariable Long id) {
        filmService.delete(id);
        return true;
    }

    @PatchMapping("/film")
    @PreAuthorize("hasAnyAuthority('USER')")

    public Film updateFilm(@RequestBody Film film) {
        return filmService.updateFilm(film);
    }

}

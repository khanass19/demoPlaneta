package planeta.kino.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import planeta.kino.demo.entity.Film;

import java.util.List;

public interface FilmService {

    Film saveFilm(Film film);

    Page<Film> getAllByPage(Pageable pageable);

    List<Film> getAll();

    void delete(Long id);

    Film updateFilm(Film film);

}

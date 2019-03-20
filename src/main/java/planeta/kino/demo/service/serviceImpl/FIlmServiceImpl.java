package planeta.kino.demo.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planeta.kino.demo.entity.Film;
import planeta.kino.demo.repository.FilmRepository;
import planeta.kino.demo.service.FilmService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FIlmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Page<Film> getAllByPage(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        filmRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Film updateFilm(Film film) {
        filmRepository.updateFilm(film.getName(), film.getLengthInSeconds(), film.getId());
        return filmRepository.getOne(film.getId());
    }

}

package planeta.kino.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import planeta.kino.demo.entity.Film;
import planeta.kino.demo.entity.User;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @Modifying
    @Query("update Film u set u.name= ?1, u.lengthInSeconds = ?2 where u.id = ?3")
    void updateFilm(String name, Integer lengthInSeconds, Long filmId);

}

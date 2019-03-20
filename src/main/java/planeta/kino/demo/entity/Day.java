package planeta.kino.demo.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToMany
    @JoinTable(name = "day_films",
            joinColumns = { @JoinColumn(name = "day_id") },
            inverseJoinColumns = { @JoinColumn(name = "film_id") })
    private List<Film> films = new ArrayList<>();

    public Day(LocalDate date, List<Film> films) {
        this.date = date;
        this.films = films;
    }

    public Day() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}

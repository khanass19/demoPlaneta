package planeta.kino.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.core.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "null not allowed here")
    private String name;

    private Integer lengthInSeconds;

    @OneToMany
    private List<Advertisement> list = new ArrayList<>();

    @ManyToMany(mappedBy = "films")
    private List<Day> days = new ArrayList<>();

    @ManyToMany(mappedBy = "bookingFilms")
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Film() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLengthInSeconds() {
        return lengthInSeconds;
    }

    public void setLengthInSeconds(Integer lengthInSeconds) {
        this.lengthInSeconds = lengthInSeconds;
    }

    public List<Advertisement> getList() {
        return list;
    }

    public void setList(List<Advertisement> list) {
        this.list = list;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public Film(String name, Integer lengthInSeconds, List<Advertisement> list, List<Day> days, List<User> users) {
        this.name = name;
        this.lengthInSeconds = lengthInSeconds;
        this.list = list;
        this.days = days;
        this.users = users;
    }
}

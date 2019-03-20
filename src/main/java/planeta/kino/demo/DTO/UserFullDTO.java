package planeta.kino.demo.DTO;

import planeta.kino.demo.entity.User;
import planeta.kino.demo.entity.UserInfo;

import java.time.LocalDate;

public class UserFullDTO extends UserSimpleDTO {

    private String name;
    private LocalDate dateBirth;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }
}

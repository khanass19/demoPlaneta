package planeta.kino.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import planeta.kino.demo.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> getAllByEmailOrPassword(String email, String password);

    @Query("Select u from User u where u.email=:email")
    List<User> findByEmail(@Param("email") String email);

}

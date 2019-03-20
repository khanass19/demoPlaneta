package planeta.kino.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planeta.kino.demo.entity.User;
import planeta.kino.demo.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
}

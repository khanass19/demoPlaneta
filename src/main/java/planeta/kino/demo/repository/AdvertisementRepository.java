package planeta.kino.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planeta.kino.demo.entity.Advertisement;
import planeta.kino.demo.entity.User;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement,Long> {
}

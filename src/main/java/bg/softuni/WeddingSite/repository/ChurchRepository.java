package bg.softuni.WeddingSite.repository;

import bg.softuni.WeddingSite.models.Church;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurchRepository extends JpaRepository<Church, Long> {
}

package bg.softuni.WeddingSite.repository;

import bg.softuni.WeddingSite.models.Wedding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeddingRepository extends JpaRepository<Wedding, Long> {
}

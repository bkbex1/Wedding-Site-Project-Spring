package bg.softuni.WeddingSite.repository;

import bg.softuni.WeddingSite.models.AdminMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMessageRepository extends JpaRepository<AdminMessage, Long> {
}

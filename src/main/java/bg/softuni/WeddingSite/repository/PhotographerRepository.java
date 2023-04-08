package bg.softuni.WeddingSite.repository;

import bg.softuni.WeddingSite.models.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface PhotographerRepository extends JpaRepository<Photographer, Long> {
}

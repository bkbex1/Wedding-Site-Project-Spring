package bg.softuni.WeddingSite.repository;

import bg.softuni.WeddingSite.models.Person;
import bg.softuni.WeddingSite.models.Picture;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.Wedding;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<Picture> findPictureById(Long id);

    List<User> findByFriendsNotContaining(User user);

    Optional<User> findUserByFirstName(String firstName);
}

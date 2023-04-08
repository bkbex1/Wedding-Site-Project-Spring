package bg.softuni.WeddingSite.repository;

import bg.softuni.WeddingSite.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional <Restaurant> findByName(String name);
}

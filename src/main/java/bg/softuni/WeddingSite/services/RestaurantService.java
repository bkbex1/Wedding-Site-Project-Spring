package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.Restaurant;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.dtos.RestaurantDTO;
import bg.softuni.WeddingSite.repository.RestaurantRepository;
import bg.softuni.WeddingSite.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Boolean addingRestaurant(RestaurantDTO restaurantDTO, User user) throws Exception {
        Optional<Restaurant> restaurantOpt = this.restaurantRepository.findByName(restaurantDTO.getName());
        if (restaurantOpt.isPresent()){
            return true;
        }else{
            Restaurant restaurant = new Restaurant();
            restaurant.setCreator(user);
            restaurant.setName(restaurantDTO.getName());
            restaurant.setCapacity(restaurantDTO.getCapacity());
            restaurant.setAddress(restaurantDTO.getAddress());

            restaurantRepository.save(restaurant);
        }

        return true;
    }

    public List<Restaurant> findAllRestaurants() {
        return this.restaurantRepository.findAll();
    }

    public Optional<Restaurant> findById(Long restaurantId) {
        return this.restaurantRepository.findById(restaurantId);
    }
}

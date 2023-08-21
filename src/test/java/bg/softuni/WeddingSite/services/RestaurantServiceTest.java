package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.Restaurant;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.dtos.RestaurantDTO;
import bg.softuni.WeddingSite.repository.RestaurantRepository;
import bg.softuni.WeddingSite.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    private RestaurantService toTest;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        toTest = new RestaurantService(restaurantRepository, userRepository);
    }

    @Test
    void testAddingRestaurant() throws Exception {
        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setName("Restaurant");
        when(restaurantRepository.findByName("Restaurant")).thenReturn(Optional.of(new Restaurant()));
        assertTrue(toTest.addingRestaurant(restaurant, new User()));
    }
    @Test
    void testAddingRestaurant_Sec() throws Exception {
        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setName("Restaurant");
        when(restaurantRepository.findByName("Restaurant")).thenReturn(Optional.empty());
        assertTrue(toTest.addingRestaurant(restaurant, new User()));
        Mockito.verify(restaurantRepository).save(any());
    }
    @Test
    void testFindAllRestaurants() {
        List<Restaurant> restaurants = List.of(new Restaurant(), new Restaurant());
        when(restaurantRepository.findAll()).thenReturn(restaurants);
        assertEquals(toTest.findAllRestaurants(), restaurants);
    }

    @Test
    void testFindById() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Sofia restaurant");
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        assertEquals(toTest.findById(1L).get().getName(), restaurant.getName());
    }
}

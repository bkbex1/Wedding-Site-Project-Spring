package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.models.Restaurant;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.dtos.RestaurantDTO;
import bg.softuni.WeddingSite.repository.UserRepository;
import bg.softuni.WeddingSite.services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;


@Controller
public class VendorsController {

    private final RestaurantService restaurantService;
    private final UserRepository userRepository;

    public VendorsController(RestaurantService restaurantService, UserRepository userRepository) {
        this.restaurantService = restaurantService;
        this.userRepository = userRepository;
    }
    @ModelAttribute("restaurantDTO")
    public RestaurantDTO restaurantInit() {
        return new RestaurantDTO();
    }

    @GetMapping("/restaurants")
    public String getAllRestaurants(Model model){

        List<Restaurant> allRestaurants = this.restaurantService.findAllRestaurants();

        model.addAttribute("allRestaurants", allRestaurants);
        return "/restaurants";
    }

    @GetMapping("/restaurant/{id}")
    public String getRestaurantProfile(@PathVariable("id") Long restId, Model model){

        Restaurant byId = this.restaurantService.findById(restId).get();

        model.addAttribute("restaurant", byId);
        return "/restaurantProfile";
    }
    @GetMapping("/vendors")
    public String getAllVendors(Model model){

        List<Restaurant> allRestaurants = this.restaurantService.findAllRestaurants();

        model.addAttribute("allRestaurants", allRestaurants);
        return "/vendors";
    }

    @PostMapping("/restaurant")
    public String createWedding(@Valid RestaurantDTO restaurantDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Principal principal) throws Exception {

        User user = this.userRepository.findByUsername(principal.getName()).get();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("restaurantDTO", restaurantDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.restaurantDTO", bindingResult);

            return "redirect:/createRestaurant";
        }

        restaurantService.addingRestaurant(restaurantDTO, user);

        return "redirect:/profile";
    }

    @GetMapping("/createRestaurant")
    public String createRestaurant(){
        return "addingRestaurant";
    }
}

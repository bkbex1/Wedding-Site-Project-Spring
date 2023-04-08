package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.models.Comment;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.Wedding;
import bg.softuni.WeddingSite.models.dtos.CommentDto;
import bg.softuni.WeddingSite.models.dtos.RestaurantDTO;
import bg.softuni.WeddingSite.models.dtos.UserViewDto;
import bg.softuni.WeddingSite.models.dtos.WeddingRegDTO;
import bg.softuni.WeddingSite.services.AuthService;
import bg.softuni.WeddingSite.services.CommentService;
import bg.softuni.WeddingSite.services.RestaurantService;
import bg.softuni.WeddingSite.services.WeddingService;
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
import java.util.Optional;

@Controller
public class WeddingsController {
    private final WeddingService weddingService;
    private final RestaurantService restaurantService;
    private final AuthService authService;
    private final CommentService commentService;

    public WeddingsController(WeddingService weddingService, RestaurantService restaurantService, AuthService authService, CommentService commentService) {
        this.weddingService = weddingService;
        this.restaurantService = restaurantService;
        this.authService = authService;
        this.commentService = commentService;
    }

    @ModelAttribute("weddingRegDTO")
    public WeddingRegDTO initForm() {
        return new WeddingRegDTO();
    }


    @ModelAttribute("restaurantDTO")
    public RestaurantDTO restaurantInit() {
        return new RestaurantDTO();
    }


    @GetMapping("/weddings")
    public String homeWeddings(Model model){
        List<Wedding> allWeddings = weddingService.findAllWeddings();
        model.addAttribute("allWeddings", allWeddings);
        return "weddings";
    }

    @PostMapping("/wedding/{id}")
    public String commentWedding(@PathVariable("id") Long id, @Valid CommentDto commentDto, Principal principal ){
        Wedding weddingById = weddingService.getWeddingById(id).get();
        commentDto.setWedding(weddingById);
        this.commentService.addingComment(commentDto, principal);
        return "redirect:/profile";
    }

    @GetMapping("/wedding/{id}")
    public String wedding(@PathVariable("id") Long weddingId,
                          Model model, Principal principal){
        User userByUsername = authService.getUserByUsername(principal.getName());
        List<Comment> comments = this.commentService.findAllCommentsForWedding(weddingId);
        UserViewDto userDto = new UserViewDto();
        userDto.setUsername(userByUsername.getUsername());
        userDto.setLastName(userByUsername.getLastName());
        userDto.setEmail(userByUsername.getEmail());
        userDto.setPicture(userByUsername.getPicture());

        Wedding weddingById = weddingService.getWeddingById(weddingId).get();
        model.addAttribute("wedding", weddingById);
        model.addAttribute("userDto", userDto);
        model.addAttribute("comments", comments);

        return "weddingPlanner";
    }

    @GetMapping("/vendors")
    public String vendors(){
        return "vendors";
    }

    @GetMapping("/create")
    public String create(){
        return "create";
    }

    @PostMapping("/create")
    public String createWedding(@Valid WeddingRegDTO weddingRegDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Principal principal ){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("weddingRegDTO", weddingRegDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.weddingRegDTO", bindingResult);

            return "redirect:/create";
        }


        this.weddingService.addingWedding(weddingRegDTO, principal);
        return "redirect:/profile";
    }

    @GetMapping("/createRestaurant")
    public String createRestaurant(){
        return "addingRestaurant";
    }

    @PostMapping("/restaurant")
    public String createWedding(@Valid RestaurantDTO restaurantDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Principal principal) throws Exception {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("restaurantDTO", restaurantDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.restaurantDTO", bindingResult);

            return "redirect:/createRestaurant";
        }

        restaurantService.addingRestaurant(restaurantDTO, principal);

        return "redirect:/profile";
    }
}

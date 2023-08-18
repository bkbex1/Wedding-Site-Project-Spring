package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.models.Comment;
import bg.softuni.WeddingSite.models.Role;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.Wedding;
import bg.softuni.WeddingSite.models.dtos.*;
import bg.softuni.WeddingSite.models.enums.UserRoles;
import bg.softuni.WeddingSite.services.AuthService;
import bg.softuni.WeddingSite.services.CommentService;
import bg.softuni.WeddingSite.services.RestaurantService;
import bg.softuni.WeddingSite.services.WeddingService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.stream.Collectors;

@Controller
public class WeddingsController {
    private final WeddingService weddingService;
    private final RestaurantService restaurantService;
    private final AuthService authService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public WeddingsController(WeddingService weddingService, RestaurantService restaurantService, AuthService authService, CommentService commentService) {
        this.weddingService = weddingService;
        this.restaurantService = restaurantService;
        this.authService = authService;
        this.commentService = commentService;
        this.modelMapper = new ModelMapper();
    }

    @ModelAttribute("weddingRegDTO")
    public WeddingRegDTO initForm() {
        return new WeddingRegDTO();
    }



    @GetMapping("/weddings")
    @Transactional
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
        return "redirect:/wedding/"+id;
    }

    @GetMapping("/wedding/{id}")
    public String wedding(@PathVariable("id") Long weddingId,
                          Model model, Principal principal){
        UserViewDto userDto = this.modelMapper.map(authService.getUserByUsername(principal.getName()), UserViewDto.class);
        List<Comment> comments = this.commentService.findAllCommentsForWedding(weddingId);
        List<CommentDto> commentDtos = comments.stream().map(e->this.modelMapper.map(e, CommentDto.class)).collect(Collectors.toList());

        for (var comment: commentDtos){
            if (comment.getCreator().getUsername().equals(userDto.getUsername()) || userDto.getRoles().size()>1){
                comment.setCanBeModifier(true);
            }
        }


        Wedding weddingById = this.weddingService.getWeddingById(weddingId).get();

        model.addAttribute("wedding", weddingById);
        model.addAttribute("userDto", userDto);
        model.addAttribute("comments", commentDtos);

        return "weddingPlanner";
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




}

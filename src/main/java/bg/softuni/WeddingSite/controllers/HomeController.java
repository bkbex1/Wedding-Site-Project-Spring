package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.models.Picture;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.Wedding;
import bg.softuni.WeddingSite.models.dtos.UserProfileDTO;
import bg.softuni.WeddingSite.models.dtos.UserViewDto;
import bg.softuni.WeddingSite.services.AuthService;
import bg.softuni.WeddingSite.services.CommentService;
import bg.softuni.WeddingSite.services.FileService;
import bg.softuni.WeddingSite.services.WeddingService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class HomeController {

    private User loggedUser;
    private final AuthService userService;
    private final FileService fileService;
    private final WeddingService weddingService;
    private CommentService commentService;
    private final ModelMapper modelMapper;

    public HomeController(AuthService userService, FileService fileService, WeddingService weddingService, CommentService commentService) {
        this.userService = userService;
        this.fileService = fileService;
        this.weddingService = weddingService;
        this.commentService = commentService;
        this.modelMapper = new ModelMapper();
    }

    @ModelAttribute("userProfileDTO")
    public UserProfileDTO initForm() {
        return new UserProfileDTO();
    }


    @GetMapping("/")
    public String home(Model model){
        List<User> allUsers = this.userService.findAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "index";
    }

    @GetMapping("/contact-us")
    public String contactUs(){
        return "contact-us";
    }

    @GetMapping("/image/{id}")
    public HttpEntity<byte[]> imageDisplay(@PathVariable("id") Long fieldId){
        var byCreator = this.fileService.findById(fieldId).orElseThrow();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType(MimeTypeUtils
                .parseMimeType(byCreator.getContentType())));

        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+byCreator.getFileName());
        headers.setContentLength(byCreator.getData().length);

        return new HttpEntity<>(byCreator.getData(), headers);
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal){
        List<User> allUsers = this.userService.findAllUsers();
        model.addAttribute("allUsers", allUsers);
        loggedUser = this.userService.getUserByUsername(principal.getName());
        List<Wedding> allWeddings = weddingService.findAllWeddings();
        model.addAttribute("allWeddings", allWeddings);
        List<Wedding> weddings = this.weddingService.findAllWeddingsWereUserIdGroomOrBride(loggedUser.getId());

        UserProfileDTO userProfileDTO = this.modelMapper.map(loggedUser, UserProfileDTO.class);

        model.addAttribute("userProfileDTO", userProfileDTO);
        model.addAttribute("weddings", weddings);

        return "profile";
    }

    @PostMapping("/profile")
    public String profileEdit(@Valid UserProfileDTO userProfileDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, Principal principal
                              ) throws IOException {

        loggedUser = this.userService.getUserByUsername(principal.getName());
        Long idOfPic = fileService.saveFile(userProfileDTO.getImg(), loggedUser);

        if (idOfPic!=null && idOfPic>0){
            Picture picture = this.fileService.findById(idOfPic).orElseThrow(NoSuchElementException::new);
            loggedUser.setPicture(picture);
        }

        loggedUser.setUsername(userProfileDTO.getUsername());
        loggedUser.setFirstName(userProfileDTO.getFirstName());
        loggedUser.setMiddleName(userProfileDTO.getMiddleName());
        loggedUser.setLastName(userProfileDTO.getLastName());
        loggedUser.setAddress(userProfileDTO.getAddress());
        loggedUser.setBirthDate(userProfileDTO.getBirthDate());
        loggedUser.setEmail(userProfileDTO.getEmail());
        this.userService.saveUser(loggedUser);
        return "redirect:/profile";
    }

    @GetMapping("/friends")
    public String friends(){
        return "friends";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<User> allUsers = this.userService.findAllUsers();
        List<UserViewDto> usersViewDto = new ArrayList<>();
        for (var user: allUsers){
            usersViewDto.add(this.modelMapper.map(user, UserViewDto.class));
        }

        model.addAttribute("allUsers", usersViewDto);

        return "users";
    }

    @GetMapping("/user/{username}")
    public String userProfile(@PathVariable("username") String username, Model model){

        User userProfile = this.userService.getUserByUsername(username);
        List<Wedding> weddings = this.weddingService.findAllWeddingsWereUserIdGroomOrBride(userProfile.getId());

        model.addAttribute("user", userProfile);

        model.addAttribute("weddings", weddings);

        if (userProfile.getPicture()!=null){
            model.addAttribute("fileId", userProfile.getPicture().getId());
        }else{
            model.addAttribute("fileId", 0);
        }

        return "userProfile";

    }

    @GetMapping("/gallery")
    public String gallery(){
        return "/fragments/gallery";
    }
}

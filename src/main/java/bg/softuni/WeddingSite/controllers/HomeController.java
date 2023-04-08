package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.models.Picture;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.dtos.ImageDTO;
import bg.softuni.WeddingSite.models.dtos.UserProfileDTO;
import bg.softuni.WeddingSite.services.AuthService;
import bg.softuni.WeddingSite.services.FileService;
import jakarta.validation.Valid;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class HomeController {

    private User loggedUser;
    private final AuthService userService;
    private final FileService fileService;

    public HomeController(AuthService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;

    }

    @ModelAttribute("userProfileDTO")
    public UserProfileDTO initForm() {
        return new UserProfileDTO();
    }


    @GetMapping("/")
    public String home(){
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
        loggedUser = this.userService.getUserByUsername(principal.getName());

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername(loggedUser.getUsername());
        userProfileDTO.setFirstName(loggedUser.getFirstName());
        userProfileDTO.setMiddleName(loggedUser.getMiddleName());
        userProfileDTO.setLastName(loggedUser.getLastName());
        userProfileDTO.setAddress(loggedUser.getAddress());
        userProfileDTO.setBirthDate(loggedUser.getBirthDate());
        userProfileDTO.setEmail(loggedUser.getEmail());

        model.addAttribute("userProfileDTO", userProfileDTO);
        if (loggedUser.getPicture()!=null){
            model.addAttribute("fileId", loggedUser.getPicture().getId());
        }
        return "profile";
    }
    @PostMapping("/profile")
    public String profileEdit(@Valid UserProfileDTO userProfileDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, Principal principal
                              ) throws IOException {

//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("userProfileDTO", userProfileDTO);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileDTO", bindingResult);
//
//            return "redirect:/profile";
//        }


        loggedUser = this.userService.getUserByUsername(principal.getName());
        Long idOfPic = fileService.saveFile(userProfileDTO.getImg(), loggedUser);

        Picture picture = this.fileService.findById(idOfPic).orElseThrow(NoSuchElementException::new);

        loggedUser.setPicture(picture);
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
        model.addAttribute("allUsers", allUsers);
        return "users";
    }

    @GetMapping("/user/{username}")
    public String userProfile(@PathVariable("username") String username, Model model){

        User userProfile = this.userService.getUserByUsername(username);
        model.addAttribute("user", userProfile);
        return "userProfile";

    }


}

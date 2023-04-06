package bg.softuni.WeddingSite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/contact-us")
    public String contactUs(){
        return "contact-us";
    }

    @GetMapping("/profile")
    public String profile(){
        return "profile";
    }

    @GetMapping("/friends")
    public String friends(){
        return "friends";
    }

    @GetMapping("/gallery")
    public String gallery(){
        return "fragments/gallery";
    }
}

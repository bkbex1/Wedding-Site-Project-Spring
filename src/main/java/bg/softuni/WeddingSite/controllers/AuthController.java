package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.dtos.UserRegistrationDTO;
import bg.softuni.WeddingSite.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute("userRegistrationDTO")
    public UserRegistrationDTO initForm() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegistrationDTO userRegistrationDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);

            return "redirect:/users/register";
        }

        this.authService.register(userRegistrationDTO);

        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal){
        User user = authService.getUserByUsername(principal.getName());
        return "profile";
    }

//    @ExceptionHandler({UsernameNotFoundException.class})
//    public ResponseEntity<ErrorApiResponse> handleRouteNotFound() {
//        return ResponseEntity.status(404).body(new ErrorApiResponse("Such route doesn't exist!", 1004));
//    }
//
//
//    class ErrorApiResponse {
//        private String message;
//        private Integer errorCode;
//
//        public ErrorApiResponse(String message, Integer errorCode) {
//            this.message = message;
//            this.errorCode = errorCode;
//        }
//
//        public ErrorApiResponse() {
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//
//        public Integer getErrorCode() {
//            return errorCode;
//        }
//
//        public void setErrorCode(Integer errorCode) {
//            this.errorCode = errorCode;
//        }
//    }
}

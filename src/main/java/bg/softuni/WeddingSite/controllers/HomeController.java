package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.models.*;
import bg.softuni.WeddingSite.models.dtos.AdminMassageDTO;
import bg.softuni.WeddingSite.models.dtos.UserMessageDTO;
import bg.softuni.WeddingSite.models.dtos.UserProfileDTO;
import bg.softuni.WeddingSite.models.dtos.UserViewDto;
import bg.softuni.WeddingSite.services.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class HomeController {

    private User loggedUser;
    private final AuthService userService;
    private final FileService fileService;
    private final WeddingService weddingService;
    private CommentService commentService;
    private final ModelMapper modelMapper;
    private final MessageService messageService;

    public HomeController(AuthService userService, FileService fileService, WeddingService weddingService, CommentService commentService, MessageService messageService) {
        this.userService = userService;
        this.fileService = fileService;
        this.weddingService = weddingService;
        this.commentService = commentService;
        this.messageService = messageService;
        this.modelMapper = new ModelMapper();
    }

    @ModelAttribute("userProfileDTO")
    public UserProfileDTO initForm() {
        return new UserProfileDTO();
    }


    @GetMapping("/")
    public String home(Model model, Principal principal){
        return "index";
    }

    @GetMapping("/contact-us")
    public String contactUs( Model model, Principal principal){
        User userByUsername = new User();
        boolean isLogged = false;
        AdminMassageDTO massageDTO = new AdminMassageDTO();

        if (principal!=null){
            userByUsername = this.userService.getUserByUsername(principal.getName());
            String fullName = userByUsername.getFirstName()+" "+ userByUsername.getLastName();
            massageDTO.setFullName(fullName);
            massageDTO.setEmail(userByUsername.getEmail());
            isLogged = true;
        }

        model.addAttribute("isLogged", isLogged);
        model.addAttribute("loggedUser", userByUsername);
        model.addAttribute("massageDTO", massageDTO);

        return "contact-us";
    }

    @PostMapping("/contact-us")
    public String contactUsMassage(@Valid AdminMassageDTO messageDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   Model model, Principal principal){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("messageDTO", messageDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);

            return "redirect:/contact-us";
        }

        messageDTO.setDateSend(LocalDate.now());
        User receiver = this.userService.getUserByUsername("Boris");
        messageDTO.setReceiver(receiver);
        AdminMessage messageToSend = this.modelMapper.map(messageDTO, AdminMessage.class);

        if (principal == null) {
            messageToSend.setSenderName(messageDTO.getFullName());
            messageToSend.setSenderEmail(messageDTO.getEmail());
        }else{
            User sender = this.userService.getUserByUsername(principal.getName());
            messageToSend.setSenderName(sender.getFirstName());
            messageToSend.setSenderEmail(sender.getEmail());
        }
        this.messageService.saveAdminMassage(messageToSend);

        return "redirect:/profile";
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
    @Transactional
    public String profile(Model model, Principal principal){
        User user = this.userService.getUserByUsername(principal.getName());

        boolean isAdmin = user.getRoles().size() > 1;

        List<User> allUsers = this.userService.findAllUsers();
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("allUsers", allUsers);

        List<User> allFriends = user.getFriends().stream().toList();
        List<Wedding> myWeddings = this.weddingService.findAllWeddingsWereUserIdGroomOrBride(user.getId());
        List<Conversation> conversations = this.messageService.getConversationsForUser(user);

        UserProfileDTO userProfileDTO = this.modelMapper.map(user, UserProfileDTO.class);

        model.addAttribute("allFriends", allFriends);
        model.addAttribute("allWeddings", myWeddings);
        model.addAttribute("allConversations", conversations);

        model.addAttribute("userProfileDTO", userProfileDTO);
        model.addAttribute("weddings", myWeddings);

        return "profile";
    }

    @PostMapping("/profile")
    @Transactional
    public String profileEdit(@Valid UserProfileDTO userProfileDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, Principal principal
                              ) throws IOException {

        User user = this.userService.getUserByUsername(principal.getName());
        Long idOfPic = fileService.saveFile(userProfileDTO.getImg(), user);

        if (idOfPic!=null && idOfPic>0){
            Picture picture = this.fileService.findById(idOfPic).orElseThrow(NoSuchElementException::new);
            user.setPicture(picture);
        }

        user.setUsername(userProfileDTO.getUsername());
        user.setFirstName(userProfileDTO.getFirstName());
        user.setMiddleName(userProfileDTO.getMiddleName());
        user.setLastName(userProfileDTO.getLastName());
        user.setAddress(userProfileDTO.getAddress());
        user.setBirthDate(userProfileDTO.getBirthDate());
        user.setEmail(userProfileDTO.getEmail());
        this.userService.saveUser(user);
        return "redirect:/profile";
    }

    @GetMapping("/users")
    @Transactional
    public String users(Model model, Principal principal){

        User userByUsername = this.userService.getUserByUsername(principal.getName());
        List<User> allUsers = this.userService.findByFriendsNotContaining(userByUsername);

        for (var user: allUsers){
            if (user.getUsername().equals(userByUsername.getUsername())){
                allUsers.remove(user);
                break;
            }
        }

        List<UserViewDto> usersViewDto = new ArrayList<>();
        allUsers.forEach(user->usersViewDto.add(this.modelMapper.map(user, UserViewDto.class)));
        model.addAttribute("userDTOs", usersViewDto);
        return "users";
    }

    @GetMapping("/user/add/{username}")
    @Transactional
    public String addFriend(@PathVariable("username") String username,
                      Principal principal){
        User user = this.userService.getUserByUsername(principal.getName());
        User friendUser = this.userService.getUserByUsername(username);

        user.addFriend(friendUser);

        this.userService.saveUser(user);

        return "redirect:/users";
    }

    @GetMapping("/user/remove/{username}")
    @Transactional
    public String removeFriend(@PathVariable("username") String username,
                        Principal principal){
        User user = this.userService.getUserByUsername(principal.getName());
        User friendToRemove = user.getFriends().stream().filter(friend->friend.getUsername().equals(username)).findFirst().get();
        user.removeFriend(friendToRemove);
        this.userService.saveUser(user);

        return "redirect:/users";
    }

    @GetMapping("/user/{username}")
    @Transactional
    public String userProfile(@PathVariable("username") String username, Model model, Principal principal){

        User loggedUser = this.userService.getUserByUsername(principal.getName());
        User userProfile = this.userService.getUserByUsername(username);
        Conversation conversation = this.messageService.getAllMessagesBetweenUsers(loggedUser, userProfile);

        UserProfileDTO userProfileDTO = this.modelMapper.map(loggedUser, UserProfileDTO.class);

        List<Conversation> conversationList = new ArrayList();
        if (conversation != null){
            conversationList.add(conversation);
        }


        boolean isFriend = false;
        UserMessageDTO userMessageDTO = new UserMessageDTO();
        Set<User> friends = loggedUser.getFriends();
        for (var friend:friends){
            if (friend.getUsername().equals(userProfile.getUsername())){
                isFriend =true;
                break;
            }
        }

        List<Wedding> weddings = this.weddingService.findAllWeddingsWereUserIdGroomOrBride(userProfile.getId());

        model.addAttribute("user", userProfile);
        model.addAttribute("allWeddings", weddings);
        model.addAttribute("isFriend", isFriend);
        model.addAttribute("allConversations", conversationList);
        model.addAttribute("userMessageDTO", userMessageDTO);
        model.addAttribute("userProfileDTO", userProfileDTO);

        if (userProfile.getPicture()!=null){
            model.addAttribute("fileId", userProfile.getPicture().getId());
        }else{
            model.addAttribute("fileId", 0);
        }

        return "userProfile";

    }

    @PostMapping("/user/{username}")
    @Transactional
    public String sendUserMessage(@Valid UserMessageDTO userMessageDTO,
                                  @PathVariable("username") String username, Model model, Principal principal){
        User loggedUser = this.userService.getUserByUsername(principal.getName());
        User userProfile = this.userService.getUserByUsername(username);

        Conversation conversation = this.messageService.getAllMessagesBetweenUsers(loggedUser, userProfile);
        UserMessage userMessage = new UserMessage();
        if (conversation==null){
            conversation = new Conversation();
            Set<User> participants = new LinkedHashSet<>(Arrays.asList(loggedUser, userProfile));
            conversation.setParticipants(participants);
        }


        userMessage.setDateSend(LocalDateTime.now());
        userMessage.setText(userMessageDTO.getText());
        userMessage.setSender(loggedUser);
        userMessage.setConversation(conversation);
        conversation.getMessages().add(userMessage);
        this.messageService.saveUserMassage(userMessage);
        this.messageService.saveConversation(conversation);

        model.addAttribute("user", userProfile);

        return "redirect:/user/"+username;

    }

    @GetMapping("/gallery")
    public String gallery(){
        return "/fragments/gallery";
    }

//    @GetMapping("/admin")
//    public String admin(Model model, Principal principal){
//
//        User user = this.userService.getUserByUsername(principal.getName());
//        UserProfileDTO userProfileDTO = this.modelMapper.map(user, UserProfileDTO.class);
//
//        model.addAttribute("userProfileDTO", userProfileDTO);
//
//        return "/admin";
//    }

}

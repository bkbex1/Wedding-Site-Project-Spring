package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.models.Conversation;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.UserMessage;
import bg.softuni.WeddingSite.models.Wedding;
import bg.softuni.WeddingSite.models.dtos.UserMessageDTO;
import bg.softuni.WeddingSite.models.dtos.UserProfileDTO;
import bg.softuni.WeddingSite.services.AuthService;
import bg.softuni.WeddingSite.services.CommentService;
import bg.softuni.WeddingSite.services.MessageService;
import bg.softuni.WeddingSite.services.WeddingService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RestApiController {

    private final MessageService messageService;
    private final ModelMapper modelMapper;
    private final AuthService userService;
    private final WeddingService weddingService;

    public RestApiController(CommentService commentService, MessageService messageService, ModelMapper modelMapper, AuthService userService, WeddingService weddingService) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.weddingService = weddingService;
    }

    @GetMapping("/profile/conversation/{id}")
    @Transactional
    public ResponseEntity<List<UserMessageDTO>> getAllMessages(@PathVariable("id") Long id) {
        List<UserMessage> messages = this.messageService.getMessagesForCommunication(id);
        List<UserMessageDTO> messageDTOS = new ArrayList<>();

        messages.forEach(message->{
            User sender = message.getSender();
            UserProfileDTO upDTO = new UserProfileDTO();
            upDTO.setUsername(sender.getUsername());
            upDTO.setLastName(sender.getLastName());
            upDTO.setFirstName(sender.getFirstName());
            upDTO.setEmail(sender.getEmail());
            upDTO.setPicture(sender.getPicture());

            UserMessageDTO umDTO = new UserMessageDTO();
            umDTO.setSender(upDTO);
            umDTO.setDateSend(message.getDateSend());
            umDTO.setText(message.getText());
            messageDTOS.add(umDTO);
        });
        messageDTOS.forEach(message->message.setSender(this.modelMapper.map(message.getSender(), UserProfileDTO.class)));

        return ResponseEntity.ok(messageDTOS);
    }


    @GetMapping("/profile/conversation")
    public ResponseEntity<List<Conversation>> getAllConversations(Principal principal) {
        User user = this.userService.getUserByUsername(principal.getName());
        List<Conversation> conversations = this.messageService.getConversationsForUser(user);

        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/profile/friends")
    public ResponseEntity<List<User>> profileFriends(Principal principal) {
        User user = this.userService.getUserByUsername(principal.getName());
        List<User> allFriends = user.getFriends().stream().toList();
        return ResponseEntity.ok(allFriends);
    }

    @GetMapping("/profile/weddings")
    public ResponseEntity<List<Wedding>> profileWedding(Principal principal) {
        User user = this.userService.getUserByUsername(principal.getName());
        List<Wedding> myWeddings = this.weddingService.findAllWeddingsWereUserIdGroomOrBride(user.getId());
        return ResponseEntity.ok(myWeddings);
    }
}

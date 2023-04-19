package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.models.Comment;
import bg.softuni.WeddingSite.models.dtos.UserViewDto;
import bg.softuni.WeddingSite.models.viws.CommentView;
import bg.softuni.WeddingSite.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestApiController {

    private final CommentService commentService;

    public RestApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<List<CommentView>> getAllComments(@PathVariable("id") Long id ) {
        List<CommentView> commentsView = new ArrayList<>();
        for (var comment : commentService.findAllCommentsForWedding(id)){
            CommentView comment1 = new CommentView();
            UserViewDto user = new UserViewDto();

            user.setFirstName(comment.getCreator().getFirstName());
            user.setUsername(comment.getCreator().getUsername());
            user.setPicture(comment.getCreator().getPicture());
            user.setLastName(comment.getCreator().getLastName());
            user.setEmail(comment.getCreator().getEmail());

            comment1.setCreator(user);
            comment1.setText(comment.getText());
            comment1.setId(comment.getId());
            comment1.setCreated(comment.getCreated());
            commentsView.add(comment1);
        }
        return ResponseEntity.ok(commentsView);
    }

    @PostMapping("/comment/{id}")
    public String postComment(@PathVariable("id")Long id) {
        return "redirect:/wedding/{id}";
    }
    @PatchMapping("/comment/{id}")
    public String editComment(@PathVariable("id")Long id) {
        return "redirect:/wedding/{id}";
    }
}

package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.models.Comment;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.services.AuthService;
import bg.softuni.WeddingSite.services.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.management.relation.Role;
import javax.naming.NoPermissionException;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CommentsController {

    private CommentService commentService;
    private AuthService authService;

    public CommentsController(CommentService commentService, AuthService authService) {
        this.commentService = commentService;
        this.authService = authService;
    }

    @PreAuthorize("@commentService.isOwner(#userDetails, #id)")
    @DeleteMapping("/comment/{id}")
    public String delete(@PathVariable("id")Long id,
                         @AuthenticationPrincipal UserDetails userDetails) {

        this.commentService.deleteCommentById(id);

        return "redirect:/weddings";
    }
}

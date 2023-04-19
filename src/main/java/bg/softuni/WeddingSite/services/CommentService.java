package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.Comment;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.dtos.CommentDto;
import bg.softuni.WeddingSite.models.enums.UserRoles;
import bg.softuni.WeddingSite.repository.CommentRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private AuthService authService;
    private WeddingService weddingService;

    public CommentService(CommentRepository commentRepository, AuthService authService, WeddingService weddingService) {
        this.commentRepository = commentRepository;
        this.authService = authService;
        this.weddingService = weddingService;
    }

    public List<Comment> findAllCommentsForWedding(Long weddingId) {
        List<Comment> allByWeddingId = this.commentRepository.findAllByWeddingId(weddingId);
        return allByWeddingId;
    }

    public void addingComment(CommentDto commentDto, Principal principal) {
        User creator = this.authService.getUserByUsername(principal.getName());

        Comment comment = new Comment();
        comment.setCreated(LocalDate.now());
        comment.setCreator(creator);
        comment.setText(commentDto.getText());
        comment.setWedding(commentDto.getWedding());

        commentRepository.save(comment);

    }

    public Optional<Comment> findCommentById(Long commentId) {
        return this.commentRepository.findById(commentId);
    }

    public void deleteComment(Comment commentById) {
        this.commentRepository.delete(commentById);
    }


    public boolean isOwner(UserDetails userDetails, Long id) {
        if (id == null || userDetails == null) {
            return  false;
        }

        var comment = commentRepository.
                findById(id).
                orElse(null);

        if (comment == null) {
            return false;
        }

        return userDetails.getUsername().equals(comment.getCreator().getUsername()) ||
                isUserAdmin(userDetails);
    }

    private boolean isUserAdmin(UserDetails userDetails) {
        // to do
        return userDetails.getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                anyMatch(a -> a.equals("ROLE_" + UserRoles.ADMIN.name()));
    }

    public void deleteCommentById(Long id) {
        this.commentRepository.findById(id)
                .ifPresent(commentRepository::delete);
    }

}

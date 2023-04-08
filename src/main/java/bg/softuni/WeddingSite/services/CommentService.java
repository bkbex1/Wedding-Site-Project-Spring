package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.Comment;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.dtos.CommentDto;
import bg.softuni.WeddingSite.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

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
       return this.commentRepository.findAllByWeddingId(weddingId);
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
}

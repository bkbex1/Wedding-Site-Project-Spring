package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.Comment;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.dtos.CommentDto;
import bg.softuni.WeddingSite.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    CommentService toTest;

    @Mock
    CommentRepository commentRepository;

    @Mock
    AuthService authService;

    @Mock
    WeddingService weddingService;

    @BeforeEach
    void setUp(){
        toTest = new CommentService(commentRepository, authService, weddingService);
    }

    @Test
    void testFindAllCommentsForWedding(){
        when(commentRepository.findAllByWeddingId(1L)).thenReturn(List.of(new Comment(), new Comment()));
        List<Comment> allComments = toTest.findAllCommentsForWedding(1L);
        assertEquals(allComments.size(),2);
    }

    @Test
    void testAddingComment(){
        CommentDto commentDto = new CommentDto();
        commentDto.setText("Text");
        User creator = new User();
        creator.setUsername("Boris");
        toTest.addingComment(commentDto, creator);
        Mockito.verify(commentRepository).save(any());
    }

    @Test
    void testFindCommentById(){
        Comment comment = new Comment();
        User user = new User();
        user.setUsername("Boris");
        comment.setCreator(user);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        assertEquals(toTest.findCommentById(1L).get().getCreator().getUsername(),user.getUsername());
    }

    @Test
    void testDeleteComment(){
        Comment comment = new Comment();
        toTest.deleteComment(comment);
        Mockito.verify(commentRepository).delete(any());

    }

    @Test
    void testDeleteCommentById(){
        when(commentRepository.findById(1L)).thenReturn(Optional.of(new Comment()));
        toTest.deleteCommentById(1L);
        Mockito.verify(commentRepository).delete(any());
    }

    @Test
    public void testIsOwnerUserDetailsNull() {
        Long id = 1L;

        boolean result = toTest.isOwner(null, id);

        assertFalse(result);
    }

}

package bg.softuni.WeddingSite.repository;

import bg.softuni.WeddingSite.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByWeddingId(Long weddingId);
}

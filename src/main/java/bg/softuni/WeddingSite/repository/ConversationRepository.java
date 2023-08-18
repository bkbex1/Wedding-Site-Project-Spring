package bg.softuni.WeddingSite.repository;

import bg.softuni.WeddingSite.models.Conversation;
import bg.softuni.WeddingSite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c WHERE SIZE(c.participants) = 2 AND :sender MEMBER OF c.participants AND :receiver MEMBER OF c.participants")
    Conversation findConversationByParticipants(@Param("sender") User sender, @Param("receiver") User receiver);

    @Query("SELECT c FROM Conversation c WHERE :user MEMBER OF c.participants")
    List<Conversation> findConversationsByUser(@Param("user") User user);

}

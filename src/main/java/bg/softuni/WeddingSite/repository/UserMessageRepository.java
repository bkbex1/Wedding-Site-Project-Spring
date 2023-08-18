package bg.softuni.WeddingSite.repository;

import bg.softuni.WeddingSite.models.Conversation;
import bg.softuni.WeddingSite.models.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {

    List<UserMessage> findByConversationId(Long communicationId);
}

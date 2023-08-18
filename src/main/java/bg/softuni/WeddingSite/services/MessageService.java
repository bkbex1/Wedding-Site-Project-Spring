package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.AdminMessage;
import bg.softuni.WeddingSite.models.Conversation;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.UserMessage;
import bg.softuni.WeddingSite.repository.AdminMessageRepository;
import bg.softuni.WeddingSite.repository.ConversationRepository;
import bg.softuni.WeddingSite.repository.UserMessageRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final AdminMessageRepository messageRepository;
    private final UserMessageRepository userMessageRepository;
    private final ConversationRepository conversationRepository;

    public MessageService(AdminMessageRepository messageRepository, UserMessageRepository userMessageRepository, ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.userMessageRepository = userMessageRepository;
        this.conversationRepository = conversationRepository;
    }

    public void saveAdminMassage(AdminMessage massage){
        this.messageRepository.save(massage);
    }

    public void saveUserMassage(UserMessage massage){
        this.userMessageRepository.save(massage);
    }

    public Conversation getAllMessagesBetweenUsers(User sender, User receiver) {
        return this.conversationRepository.findConversationByParticipants(sender, receiver);
    }

    public void saveConversation(Conversation conversation){
        this.conversationRepository.save(conversation);
    }

    public List<Conversation> getConversationsForUser(User user) {
        return conversationRepository.findConversationsByUser(user);
    }

    public List<UserMessage> getMessagesForCommunication(Long communicationId) {
        return this.userMessageRepository.findByConversationId(communicationId);
    }
}

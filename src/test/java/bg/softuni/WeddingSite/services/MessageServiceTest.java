package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.AdminMessage;
import bg.softuni.WeddingSite.models.Conversation;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.UserMessage;
import bg.softuni.WeddingSite.repository.AdminMessageRepository;
import bg.softuni.WeddingSite.repository.ConversationRepository;
import bg.softuni.WeddingSite.repository.UserMessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {


    private MessageService toTest;

    @Mock
    private UserMessageRepository userMessageRepository;

    @Mock
    private AdminMessageRepository adminMessageRepository;

    @Mock
    private ConversationRepository conversationRepository;

    @BeforeEach
    void setUp(){
        toTest = new MessageService(adminMessageRepository,userMessageRepository, conversationRepository);
    }

    @Test
    void testUserMessageFound(){
        List<UserMessage> messages = List.of(new UserMessage(), new UserMessage(), new UserMessage());
        when(userMessageRepository.findByConversationId(1L)).thenReturn(messages);
        List<UserMessage> messagesFound = toTest.getMessagesForCommunication(1L);

        assertEquals(messages.size(), messagesFound.size());

    }

    @Test
    void testAdminMessageSave(){
        AdminMessage messages = new AdminMessage();
        toTest.saveAdminMassage(messages);
        Mockito.verify(adminMessageRepository).save(any());
    }
    @Test
    void testUserMessageSave(){
        UserMessage messages = new UserMessage();
        toTest.saveUserMassage(messages);
        Mockito.verify(userMessageRepository).save(any());
    }
    @Test
    void testUserConversationSave(){
        UserMessage messages = new UserMessage();

        Conversation conversation = new Conversation();
        conversation.setMessages(List.of(messages,messages));
        toTest.saveUserMassage(messages);
        toTest.saveConversation(conversation);

        Mockito.verify(conversationRepository).save(any());
    }
    @Test
    void getConversationBetweenUsers(){
        User sender = new User();
        sender.setUsername("Boris");
        User receiver = new User();
        receiver.setUsername("Preslava");

        Conversation conversation = new Conversation();
        conversation.setParticipants(Set.of(sender, receiver));

        when(conversationRepository.findConversationByParticipants(sender, receiver)).thenReturn(conversation);

        Conversation conversationFound = toTest.getAllMessagesBetweenUsers(sender, receiver);
        assertTrue(conversationFound.getParticipants().contains(sender));
        assertTrue(conversationFound.getParticipants().contains(receiver));
        assertEquals(2,conversation.getParticipants().size() );

    }
    @Test
    void findConversationForUser(){
        User user = new User();
        user.setUsername("Boris");
        Conversation conversation = new Conversation();
        when(conversationRepository.findConversationsByUser(user)).thenReturn(List.of(conversation, conversation));

        List<Conversation> conversationFound = toTest.getConversationsForUser(user);

        assertEquals(2,conversationFound.size());

    }

}

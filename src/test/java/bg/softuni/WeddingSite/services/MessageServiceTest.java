package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.UserMessage;
import bg.softuni.WeddingSite.repository.AdminMessageRepository;
import bg.softuni.WeddingSite.repository.ConversationRepository;
import bg.softuni.WeddingSite.repository.UserMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    }

    @Test
    void testUserMessageNotFound(){
    }

}

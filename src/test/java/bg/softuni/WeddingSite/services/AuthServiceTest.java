package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.dtos.UserRegistrationDTO;
import bg.softuni.WeddingSite.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    private final String EXISTING_USERNAME = "BORIS";

    private final String NOT_EXISTING_USERNAME = "PESHO";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private AuthService toTest;

    @BeforeEach
    void setUp(){
        toTest = new AuthService(userRepository, passwordEncoder);
    }

    @Test
    void testUserFound(){

        //ARRANGE
        User user = new User();
        user.setUsername(EXISTING_USERNAME);
        user.setPassword("qwerty");

        when(userRepository.findByUsername(EXISTING_USERNAME)).thenReturn(Optional.of(user));
        // EO: ARRANGE
        // ACT
        User adminDetails = toTest.getUserByUsername(EXISTING_USERNAME);
        //EO : ACT

        // ASSERT
        Assertions.assertNotNull(adminDetails);
        Assertions.assertEquals(EXISTING_USERNAME, adminDetails.getUsername());
        Assertions.assertEquals(user.getPassword(), adminDetails.getPassword());

        //EO: ASSERT
    }

    @Test
    void testUserRegistration(){
        //Arrange
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername(EXISTING_USERNAME);
        userRegistrationDTO.setEmail("user@gmail.com");
        userRegistrationDTO.setPassword("qwerty");
        userRegistrationDTO.setConfirmPassword("qwerty");

        //Act
        toTest.register(userRegistrationDTO);
        //Assert

        Mockito.verify(userRepository).save(any());
    }

    @Test
    void testUserNotFound(){
        assertThrows(UsernameNotFoundException.class, ()-> toTest.getUserByUsername("Gosho"));
    }

    @Test
    void testUserRegistration_version2(){

        String encodedPassword = "encoded_password";
        //Arrange
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername(EXISTING_USERNAME);
        userRegistrationDTO.setEmail("user@gmail.com");
        userRegistrationDTO.setPassword("qwerty");
        userRegistrationDTO.setConfirmPassword("qwerty");

        when(passwordEncoder.encode(userRegistrationDTO.getPassword()))
                .thenReturn(encodedPassword);
        //Act
        toTest.register(userRegistrationDTO);
        //Assert

        Mockito.verify(userRepository).save(userArgumentCaptor.capture());

        User actualSavedUser = userArgumentCaptor.getValue();

        Assertions.assertEquals(userRegistrationDTO.getEmail(), actualSavedUser.getEmail());
        Assertions.assertEquals(encodedPassword, actualSavedUser.getPassword());

    }
}

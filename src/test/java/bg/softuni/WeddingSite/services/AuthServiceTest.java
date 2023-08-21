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

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    private final String EXISTING_USERNAME = "BORIS";

    private final String NOT_EXISTING_USERNAME = "Eric";

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
        assertEquals(EXISTING_USERNAME, adminDetails.getUsername());
        assertEquals(user.getPassword(), adminDetails.getPassword());

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

        verify(userRepository).save(any());
    }
    @Test
    void testUserSave(){
        //Arrange
        User user = new User();
        user.setUsername(EXISTING_USERNAME);
        user.setEmail("user@gmail.com");
        user.setPassword("qwerty");
        //Act
        toTest.saveUser(user);
        //Assert
        verify(userRepository).save(any());
    }

    @Test
    void testUserNotFound(){
        assertThrows(UsernameNotFoundException.class, ()-> toTest.getUserByUsername(NOT_EXISTING_USERNAME));
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

        verify(userRepository).save(userArgumentCaptor.capture());

        User actualSavedUser = userArgumentCaptor.getValue();

        assertEquals(userRegistrationDTO.getEmail(), actualSavedUser.getEmail());
        assertEquals(encodedPassword, actualSavedUser.getPassword());
    }

    @Test
    void testUserRegistration_DifferentPasswords(){
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername(EXISTING_USERNAME);
        userRegistrationDTO.setEmail("user@gmail.com");
        userRegistrationDTO.setPassword("qwerty");
        userRegistrationDTO.setConfirmPassword("qwerty1");

        Assertions.assertThrows(RuntimeException.class,()-> toTest.register(userRegistrationDTO));
    }
    @Test
    void testUserRegistration_EmailUsed(){
        User user = new User();

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername(EXISTING_USERNAME);
        userRegistrationDTO.setEmail("user@gmail.com");
        userRegistrationDTO.setPassword("qwerty");
        userRegistrationDTO.setConfirmPassword("qwerty");

        when(userRepository.findByEmail(userRegistrationDTO.getEmail())).thenReturn(Optional.of(user));

        Assertions.assertThrows(RuntimeException.class,()-> toTest.register(userRegistrationDTO));
    }
    @Test
    void testFindAllUsers(){
        List<User> userList = List.of(new User(), new User(), new User());
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = toTest.findAllUsers();
        assertEquals(userList.size(), result.size());
    }

    @Test
    public void testFindByFriendsNotContainingNoFriends() {
        User user = new User();
        user.setFriends(new LinkedHashSet<>());

        List<User> result = toTest.findByFriendsNotContaining(user);

        assertTrue(result.isEmpty());
        verify(userRepository).findByFriendsNotContaining(user);
    }

    @Test
    public void testFindByFriendsNotContainingNoOtherUsers() {
        User user = new User();
        User friend1 = new User();
        User friend2 = new User();
        user.setFriends(Set.of(friend1, friend2));

        when(userRepository.findByFriendsNotContaining(user)).thenReturn(new ArrayList<>());

        List<User> result = toTest.findByFriendsNotContaining(user);

        assertTrue(result.isEmpty());
        verify(userRepository).findByFriendsNotContaining(user);
    }

    @Test
    public void testFindByFriendsNotContainingSomeOtherUsers() {
        User user = new User();
        User friend1 = new User();
        User friend2 = new User();
        user.setFriends(Set.of(friend1, friend2));

        User otherUser1 = new User();
        User otherUser2 = new User();
        List<User> otherUsers = List.of(otherUser1, otherUser2);

        when(userRepository.findByFriendsNotContaining(user)).thenReturn(otherUsers);

        List<User> result = toTest.findByFriendsNotContaining(user);

        assertEquals(otherUsers, result);
        verify(userRepository).findByFriendsNotContaining(user);
    }
}

package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.Picture;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.dtos.UserRegistrationDTO;
import bg.softuni.WeddingSite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(UserRegistrationDTO registrationDTO) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())){
            throw new RuntimeException("password.match");
        }

        Optional<User> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());

        if (byEmail.isPresent()){
            throw new RuntimeException("email.used");
        }

        User user = new User(
                registrationDTO.getUsername(),
                passwordEncoder.encode(registrationDTO.getPassword()),
                registrationDTO.getEmail());

        this.userRepository.save(user);
        return true;
    }

    @Transactional
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));
    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public Optional<Picture> findPictureById(User user) {
        return this.userRepository.findPictureById(user.getId());
    }

    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Transactional
    public List<User> findByFriendsNotContaining(User user) {
        return this.userRepository.findByFriendsNotContaining(user);
    }


    public User getUserByFirstName(String firstName) {
        return this.userRepository.findUserByFirstName(firstName).get();
    }
}

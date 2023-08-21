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

import java.util.List;
import java.util.Optional;


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
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public Optional<Picture> findPictureById(Long id) {
        return this.userRepository.findPictureById(id);
    }

    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> findByFriendsNotContaining(User user) {
        return this.userRepository.findByFriendsNotContaining(user);
    }

}

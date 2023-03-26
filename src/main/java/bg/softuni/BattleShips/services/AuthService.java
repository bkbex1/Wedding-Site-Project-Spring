package bg.softuni.BattleShips.services;

import bg.softuni.BattleShips.models.User;
import bg.softuni.BattleShips.models.dtos.UserLoginDTO;
import bg.softuni.BattleShips.models.dtos.UserRegistrationDTO;
import bg.softuni.BattleShips.repository.UserRepository;
import bg.softuni.BattleShips.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private LoggedUser userSession;

    public AuthService(UserRepository userRepository, LoggedUser userSession) {
        this.userSession = userSession;
        this.userRepository = userRepository;
    }

    public boolean register(UserRegistrationDTO registrationDTO) {
        if (registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())){
            return false;
        }

        Optional<User> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());
        if (byEmail.isPresent()){
            return false;
        }
        Optional<User> byUsername = this.userRepository.findByUsername(registrationDTO.getUsername());
        if (byUsername.isPresent()){
            return false;
        }

        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setFullName(registrationDTO.getFullName());
        user.setPassword(registrationDTO.getPassword());

        this.userRepository.save(user);
        return true;
    }

    public boolean login(UserLoginDTO loginDTO) {
        Optional<User> user =  this.userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

        if (user.isEmpty()){
            return false;
        }
        this.userSession.login(user.get());
        return true;
    }
}

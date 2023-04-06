package bg.softuni.WeddingSite.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LoggedUserDetailService implements UserDetailsService {

    private final AuthService authService;

    public LoggedUserDetailService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userRep = authService.getUserByUsername(username);
        return new User(userRep.getUsername(), userRep.getPassword(),
                userRep.getRoles().stream().map(
                role -> new SimpleGrantedAuthority("ROLE_"+role.getName()))
                        .collect(Collectors.toList()));
    }

}

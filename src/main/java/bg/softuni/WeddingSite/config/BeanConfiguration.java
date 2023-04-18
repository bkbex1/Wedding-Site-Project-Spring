package bg.softuni.WeddingSite.config;

import bg.softuni.WeddingSite.services.AuthService;
import bg.softuni.WeddingSite.services.LoggedUserDetailService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class BeanConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests(authorize -> {
            try {
                authorize
                        .requestMatchers("/","/users", "/weddings",
                                "/home", "/js/**", "/css/**", "/vendors", "/restaurants",
                                "/restaurant/**", "/gallery").permitAll()
                        .requestMatchers("/users/login", "/users/register").anonymous()
                        .requestMatchers("/profile", "/wedding/**", "/comment/**").authenticated()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().and()
                        .formLogin()
                        .loginPage("/users/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .failureUrl("/users/login?error=true")
                        .and()
                        .logout()
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder("",
                20,
                30,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
    }


}

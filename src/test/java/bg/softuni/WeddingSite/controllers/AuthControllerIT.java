package bg.softuni.WeddingSite.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIT {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegistrationPost() throws Exception {
        mockMvc.perform(post("/users/register")
                .param("username","Boris")
                .param("email", "boris@gmail.com")
                .param("password", "qwerty")
                .param("confirmPassword", "qwerty")
                .with(csrf())
        ).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/users/login"));

    }
    @Test
    void testRegistrationGet() throws Exception {
        mockMvc.perform(get("/users/register")
                .with(csrf())
        ).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/register"));

    }
}

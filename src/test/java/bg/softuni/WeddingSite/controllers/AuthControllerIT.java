package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIT {

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
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }

//    @Test
//    @WithMockUser(username = "Boris", roles = "USER")
//    void testProfileGet() throws Exception {
//        mockMvc.perform(get("/users/profile")
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(view().name("profile"));
//    }
    @Test
    void testLoginGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

}

package bg.softuni.WeddingSite.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getHomePageTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getContactUsPageTest() throws Exception {
        mockMvc.perform(get("/contact-us"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact-us"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void getContactUsPageTestWithUser() throws Exception {
        mockMvc.perform(get("/contact-us").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("contact-us"));
    }

    @Test
    public void getGalleryPageTest() throws Exception {
        mockMvc.perform(get("/gallery"))
                .andExpect(status().isOk())
                .andExpect(view().name("/fragments/gallery"));
    }
}

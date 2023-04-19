//package bg.softuni.WeddingSite.rest;
//
//import bg.softuni.WeddingSite.models.dtos.CommentDto;
//import bg.softuni.WeddingSite.services.CommentService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CommentRestControllerTest {
//    private static final Long WEDDING_ID = 2L;
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CommentService commentService;
//
//    @Test
//    public void getComments_twoCommentsExist_commentsReturnedAsJsonAndStatusIsOk() throws Exception {
//        when(commentService.findAllCommentsForWedding(WEDDING_ID)).thenReturn(List.of(
//                new CommentDto(3L, "John Doe", "Marina, I Love You, My friend"),
//                new CommentDto(4L, "Foo Bar", "I will merry You")
//        ));
//
//        mockMvc.perform(get("/api/" + ROUTE_ID + "/comments"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.[0].authorName", is("John Doe")))
//                .andExpect(jsonPath("$.[0].message", is("This is comment #1")))
//                .andExpect(jsonPath("$.[1].authorName", is("Foo Bar")))
//                .andExpect(jsonPath("$.[1].message", is("This is comment #2")));
//    }
//
//    @Test
//    @WithMockUser(username = "testUsername")
//    public void createComment_sampleData_commentIsReturnedAsExpected() throws Exception {
//        when(commentService.createComment(any())).thenAnswer(interaction -> {
//            CommentCreationDto commentCreationDto = interaction.getArgument(0);
//            return new CommentDisplayView(1L, commentCreationDto.getUsername(), commentCreationDto.getMessage());
//        });
//        CommentMessageDto commentMessageDto = new CommentMessageDto("This is comment #1");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mockMvc.perform(post("/api/" + ROUTE_ID + "/comments")
//                .content(objectMapper.writeValueAsString(commentMessageDto))
//                .with(csrf())
//                        .contentType("application/json")
//                        .accept("application/json"))
//                .andExpect(status().is(201))
//                .andExpect(jsonPath("$.message", is("This is comment #1")))
//                .andExpect(jsonPath("$.authorName", is("testUsername")));
//    }
//
//}

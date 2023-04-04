package bg.softuni.WeddingSite.models.dtos;

import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Controller;

@Controller
public class UserLoginDTO {

    @Min(5)
    private String userName;

    @Min(5)
    private String password;
}

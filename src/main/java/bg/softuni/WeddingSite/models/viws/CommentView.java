package bg.softuni.WeddingSite.models.viws;

import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.Wedding;
import bg.softuni.WeddingSite.models.dtos.UserViewDto;

import java.time.LocalDate;

public class CommentView {
    private Long id;
    private String text;
    private UserViewDto creator;
    private LocalDate created;

    public CommentView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserViewDto getCreator() {
        return creator;
    }

    public void setCreator(UserViewDto creator) {
        this.creator = creator;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }


}

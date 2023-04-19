package bg.softuni.WeddingSite.models.dtos;

import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.Wedding;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CommentDto {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCanBeModified(Boolean canBeModified) {
        this.canBeModified = canBeModified;
    }

    private Long id;
    private User creator;

    private Wedding wedding;

    @Size(min = 3, max = 5000)
    private String text;

    private Boolean canBeModified = false;

    public Boolean getCanBeModified() {
        return canBeModified;
    }

    public void setCanBeModifier(Boolean canBeModified) {
        this.canBeModified = canBeModified;
    }

    private LocalDate created;

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Wedding getWedding() {
        return wedding;
    }

    public void setWedding(Wedding wedding) {
        this.wedding = wedding;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
}

package bg.softuni.WeddingSite.models.dtos;

import bg.softuni.WeddingSite.models.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AdminMassageDTO {

    String fullName;

    User receiver;

    private LocalDate dateSend;

    @Size(min = 3, max = 250)
    String text;

    @Size(min = 3, max = 50)
    String title;

    String email;

    public String getFullName() {
        return fullName;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateSend() {
        return dateSend;
    }

    public void setDateSend(LocalDate dateSend) {
        this.dateSend = dateSend;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdminMassageDTO() {
    }


}

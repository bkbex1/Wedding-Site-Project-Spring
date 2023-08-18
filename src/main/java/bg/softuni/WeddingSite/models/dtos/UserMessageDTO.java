package bg.softuni.WeddingSite.models.dtos;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UserMessageDTO {

    private UserProfileDTO sender;

    private LocalDateTime dateSend;

    public UserProfileDTO getSender() {
        return sender;
    }

    public void setSender(UserProfileDTO sender) {
        this.sender = sender;
    }

    public LocalDateTime getDateSend() {
        return dateSend;
    }

    public void setDateSend(LocalDateTime dateSend) {
        this.dateSend = dateSend;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    boolean isRead;

    @Size(min = 3, max = 250)
    String text;

    public UserMessageDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

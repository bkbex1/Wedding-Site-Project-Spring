package bg.softuni.WeddingSite.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "admin_messages")
public class AdminMessage {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "sender_name")
   private String senderName;

   @Column(name = "sender_email")
   private String senderEmail;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "receiver_id")
   private User receiver;

   @Column(name = "date_send")
   private LocalDate dateSend;

   @Column
   boolean isRead;

   @Column(name = "text", length = 250)
   String text;

   @Column(name = "title", length = 100)
   String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public LocalDate getDateSend() {
        return dateSend;
    }

    public void setDateSend(LocalDate dateSend) {
        this.dateSend = dateSend;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
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

    public AdminMessage() {
        }
}

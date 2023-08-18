package bg.softuni.WeddingSite.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> participants;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<UserMessage> messages;

    public Conversation() {
        this.messages = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<UserMessage> getMessages() {
        return messages;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public void setMessages(List<UserMessage> messages) {
        this.messages = messages;
    }
}

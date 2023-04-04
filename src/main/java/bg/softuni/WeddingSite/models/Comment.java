package bg.softuni.WeddingSite.models;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User commentator;

    @OneToOne
    private Wedding wedding;

    public Long getId() {
        return id;
    }

    public Comment() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCommentator() {
        return commentator;
    }

    public void setCommentator(User commentator) {
        this.commentator = commentator;
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

    @Column(columnDefinition = "Text")
    private String text;
}

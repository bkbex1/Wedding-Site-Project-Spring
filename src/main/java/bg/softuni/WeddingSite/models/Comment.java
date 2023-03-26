package bg.softuni.WeddingSite.models;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "commentator")
    private User commentator;

    @Column(name = "wedding")
    private Wedding wedding;

    @Column(columnDefinition = "Text")
    private String text;
}

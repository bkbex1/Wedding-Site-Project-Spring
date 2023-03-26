package bg.softuni.WeddingSite.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "weddings")
public class Wedding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "groom")
    private User groom;

    @Column(name = "bride")
    private User bride;

    @OneToOne
    private Church church;

    private Restaurant restaurant;

    private LocalDate weddingDate;

    @OneToMany
    private List<Picture> photos;

    private List<User> photographer;

    @ManyToOne
    private List<Comment> comments;

    @ManyToOne
    private List<User> guests;

}

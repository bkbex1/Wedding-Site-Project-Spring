package bg.softuni.WeddingSite.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    private List<Picture> photos;

    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToOne
    private List<User> friends;

    @ManyToMany
    private List<Wedding> wedding;

    public User() {}


}

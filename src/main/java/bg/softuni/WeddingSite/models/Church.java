package bg.softuni.WeddingSite.models;

import jakarta.persistence.*;

@Entity
@Table(name = "churches")
public class Church {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String Address;

    @Column(name = "capacity")
    private int capacity;
}

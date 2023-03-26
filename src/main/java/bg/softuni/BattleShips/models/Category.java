package bg.softuni.BattleShips.models;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(unique = true, nullable = false)
    private ShipType name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Category(){}

    public Category(long id, ShipType name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Category(ShipType name) {
        this.name = name;
    }
}

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

    @Column(name = "name")
    private String name;

    @OneToOne
    private User creator;

    @OneToOne
    private Picture churchPic;

    public Picture getChurchPic() {
        return churchPic;
    }

    public void setChurchPic(Picture churchPic) {
        this.churchPic = churchPic;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Church() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

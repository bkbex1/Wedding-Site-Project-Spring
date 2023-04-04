package bg.softuni.WeddingSite.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "photographers")
public class Photographer extends Person{

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "photographer")
    private List<Wedding> weddings;

    @OneToMany(mappedBy = "photographer")
    private List<Picture> photos;

    public Photographer() {
        super();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

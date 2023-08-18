package bg.softuni.WeddingSite.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "photographers")
public class Photographer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    private User creator;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "photographer", fetch = FetchType.LAZY)
    private List<Wedding> weddings;

    @OneToMany(mappedBy = "photographer", fetch = FetchType.LAZY)
    private List<Picture> photos;

    @OneToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @OneToOne
    private Picture photographerPic;

    public Picture getPhotographerPic() {
        return photographerPic;
    }

    public void setPhotographerPic(Picture photographerPic) {
        this.photographerPic = photographerPic;
    }

    public Photographer() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Wedding> getWeddings() {
        return weddings;
    }

    public void setWeddings(List<Wedding> weddings) {
        this.weddings = weddings;
    }

    public List<Picture> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Picture> photos) {
        this.photos = photos;
    }

}

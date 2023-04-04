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

    @OneToOne
    private Church church;

    @ManyToOne
    private Restaurant restaurant;

    @Column(name = "wedding_date")
    private LocalDate weddingDate;

    @OneToMany
    private List<Picture> photos;

    @OneToMany(mappedBy = "id")
    private List<Comment> comments;

    @OneToOne
    private Person groom;

    @OneToOne
    private Person bride;

    @ManyToOne
    private Photographer photographer;

    @OneToMany
    private List<Person> guests;

    public Wedding() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(LocalDate weddingDate) {
        this.weddingDate = weddingDate;
    }

    public List<Picture> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Picture> photos) {
        this.photos = photos;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Person getGroom() {
        return groom;
    }

    public void setGroom(Person groom) {
        this.groom = groom;
    }

    public Person getBride() {
        return bride;
    }

    public void setBride(Person bride) {
        this.bride = bride;
    }

    public Photographer getPhotographer() {
        return photographer;
    }

    public void setPhotographer(Photographer photographer) {
        this.photographer = photographer;
    }

    public List<Person> getGuests() {
        return guests;
    }

    public void setGuests(List<Person> guests) {
        this.guests = guests;
    }
}

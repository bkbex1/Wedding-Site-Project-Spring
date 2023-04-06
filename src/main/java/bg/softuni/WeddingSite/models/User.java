package bg.softuni.WeddingSite.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends Person {

    @Column(name = "user_name", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "registered_date")
    private LocalDate registeredDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @OneToOne
    private Picture picture;

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User(String username, String encode, String email) {
        this.username = username;
        this.password = encode;
        this.email= email;
        this.registeredDate = LocalDate.now();
    }

    public User() {
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

}

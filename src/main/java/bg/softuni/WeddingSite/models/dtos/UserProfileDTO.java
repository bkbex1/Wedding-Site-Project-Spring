package bg.softuni.WeddingSite.models.dtos;

import bg.softuni.WeddingSite.models.Picture;
import bg.softuni.WeddingSite.models.Role;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public class UserProfileDTO extends UserRegistrationDTO {

    private String username;

    private String firstName;

    private String middleName;

    private String lastName;

    private String address;

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    private Picture picture;

    private Sex gender;

    private LocalDate birthDate;

    private MultipartFile img;

    public MultipartFile getImg() {
        return img;
    }

    public UserProfileDTO setImg(MultipartFile img) {
        this.img = img;
        return this;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public UserProfileDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getGender() {
        return gender;
    }

    public void setGender(Sex gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


}

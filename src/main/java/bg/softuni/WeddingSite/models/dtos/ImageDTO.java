package bg.softuni.WeddingSite.models.dtos;

import org.springframework.web.multipart.MultipartFile;

public class ImageDTO {

    private MultipartFile img;

    public MultipartFile getImg() {
        return img;
    }

    public ImageDTO setImg(MultipartFile img) {
        this.img = img;
        return this;
    }
}

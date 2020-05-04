package ImageHoster.service;

import ImageHoster.model.Image;
import ImageHoster.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


//The @Service annotation denotes that this is the class which takes care of the business logic of the application
//This class acts as a intermediary between the controller classes and repository classes
//gets the information from the controller class and interacts with the repository class
//and returns the resulting information back to the controller which will respond back to the client browser's request
@Service
public class ImageService {   // service class to execute the business logic for all requests involving images
    @Autowired
    private ImageRepository imageRepository;    //a instance of the ImageRepository

    //Call the getAllImages() method in the Repository and obtain a List of all the images in the database
    public List<Image> getAllImages() {
        return imageRepository.getAllImages();
    }


    //The method calls the createImage() method in the Repository and passes the image to be persisted in the database
    public void uploadImage(Image image) {
        imageRepository.uploadImage(image);
    }

    //The method calls the getImage() method in the Repository and passes the id of the image to be fetched
    public Image getImage(Integer imageId) {
        return imageRepository.getImage(imageId);
    }

    //The method calls the updateImage() method in the Repository and passes the Image to be updated in the database
    public void updateImage(Image updatedImage) {
        imageRepository.updateImage(updatedImage);
    }

    //The method calls the deleteImage() method in the Repository and passes the Image id of the image to be deleted in the database
    public void deleteImage(Integer imageId) {
        imageRepository.deleteImage(imageId);
    }

    //The method calls the checkOwnerOfImage() in the repository and passes the loggedin-userId and the imageId,to verify the owner of the image
    public boolean checkOwnerOfImage(Integer userId, Integer imageId) {
        return imageRepository.checkOwnerOfImage(userId, imageId);
    }
}


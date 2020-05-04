package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.Tag;
import ImageHoster.model.User;
import ImageHoster.service.ImageService;
import ImageHoster.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


//This is the controller which will handle all the requests from the client that specifically involve the Images on the imageHoster db
//handles the above requests and processes them by accessing the appropriate service layer
@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;      //instance of ImageService wired to this class

    @Autowired
    private TagService tagService;         //instance of TagService wired to this class

    //This method displays all the images in the imageHoster db onto the user home page after successful login by the user
    @RequestMapping("images")
    public String getUserImages(Model model) {
        List<Image> images = imageService.getAllImages();  //fetch all the images in the imageHoster db
        model.addAttribute("images", images);
        return "images";
    }

    //This controller method is invoked when the user requests for the details of a image by clicking on the title of the image
    //These images are displayed on the user home page when the user successfully logs in
    //The details of the image are fetched from the imageHoster db by passing on the imageid of the image to appropriate service method
    @RequestMapping("/images/{imageId}/{title}")
    public String showImage(@PathVariable("title") String title,@PathVariable("imageId") Integer imageId, Model model) {
        Image image = imageService.getImage(imageId);          //fetch the image
        model.addAttribute("image", image);                 //adds the details of the image to the model
        model.addAttribute("tags", image.getTags());
        model.addAttribute("comments",image.getComments());
        return "images/image";                                 //returns the image.html
    }

    //This controller method is called when the user requests to upload a image after logging in successfully
    //The method returns 'images/upload.html' file
    @RequestMapping("/images/upload")
    public String newImage() {
        return "images/upload";
    }

    //This controller is called when the user enters the details of the image he/she wants to upload and submits the same
    //This methods processes the request and calls the appropriate service method to pass on the details to the repository
    @RequestMapping(value = "/images/upload", method = RequestMethod.POST)
    public String createImage(@RequestParam("file") MultipartFile file, @RequestParam("tags") String tags, Image newImage, HttpSession session) throws IOException {

        User user = (User) session.getAttribute("loggeduser");     //gets the user who is uploading the image
        newImage.setUser(user);
        String uploadedImageData = convertUploadedFileToBase64(file);  //converts the image uploaded by the user into base64 format
        newImage.setImageFile(uploadedImageData);

        List<Tag> imageTags = findOrCreateTags(tags);  //returns the list of tags associated with the image,from the string "tags"
        newImage.setTags(imageTags);
        newImage.setDate(new Date());
        imageService.uploadImage(newImage);   //calls the appropriate method in service layer to be committed to the db
        return "redirect:/images";             //redirects to the "/images" request handler

    }

    //This controller method when the user requests to edit a image
    //only the owner of the image can edit a image
    //This method processes the request and responds accordingly based on the status of the user who wants to edit the image
    @RequestMapping(value = "/editImage")
    public String editImage(@RequestParam("imageId") Integer imageId,HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggeduser");   //user who requested to edit image
        Image image = imageService.getImage(imageId);                      //fetch the image
        boolean ownerOfImage = imageService.checkOwnerOfImage(user.getId(),imageId);   //checks the status of the user
        if (ownerOfImage) {                                                  //user is owner...allow user to edit
            String tags = convertTagsToString(image.getTags()); //method call to convert the List of tags into a String
            model.addAttribute("image", image);    //add the image details to model
            model.addAttribute("tags", tags);
            return "images/edit";                    //return the edit.html with the image details to be edited
        }else{                                                            //user is not owner..display appropriate message
            String editError ="Only the owner of the image can edit the image";
            model.addAttribute("image",image);
            model.addAttribute("tags",image.getTags());
            model.addAttribute("editError",editError);  //add editError message to the model
            return "images/image";  //return the same image with the error message
        }
    }

    //This controller methods is called when the user submits the edited image and its details
    //processes the request and calls the appropriate service method to update the image details
    @RequestMapping(value = "/editImage", method = RequestMethod.PUT)
    public String editImageSubmit(@RequestParam("file") MultipartFile file, @RequestParam("imageId") Integer imageId, @RequestParam("tags") String tags, Image updatedImage, HttpSession session) throws IOException {

        Image image = imageService.getImage(imageId);              //fetch the image
        String updatedImageData = convertUploadedFileToBase64(file); //converts the image into base64 string
        List<Tag> imageTags = findOrCreateTags(tags);   //converts the string of tags into list of tags

        if (updatedImageData.isEmpty())                        //was the image changed?
            updatedImage.setImageFile(image.getImageFile());  //if no,keep the old image
        else {
            updatedImage.setImageFile(updatedImageData);       //if yes,then update the image
        }

        updatedImage.setId(imageId);                   //set the other details of the image
        User user = (User) session.getAttribute("loggeduser");
        updatedImage.setUser(user);
        updatedImage.setTags(imageTags);
        updatedImage.setDate(new Date());

        imageService.updateImage(updatedImage);         //call the service method to update the image
        return "redirect:/images/" + updatedImage.getId()+'/'+ updatedImage.getTitle();     //return the updated image
    }


    //This controller method is called when the user requests to delete a image
    //Ascertains the owner of the image requested to be deleted
    //processes the request accordingly based on the owner of the image
    @RequestMapping(value = "/deleteImage", method = RequestMethod.DELETE)
    public String deleteImageSubmit(@RequestParam(name = "imageId") Integer imageId,HttpSession session,Model model) {
        User user = (User) session.getAttribute("loggeduser");   //user who requested to delete a image
        Image image = imageService.getImage(imageId);   //fetch the image
        boolean ownerOfImage = imageService.checkOwnerOfImage(user.getId(),imageId);  //check the status of the user
        if (ownerOfImage) {                             //owner of the image...allow delete
            imageService.deleteImage(imageId);          //method call to appropriate service logic
            return "redirect:/images";                  //display all the images in the db
        }else{
            String deleteError ="Only the owner of the image can delete the image";  //not owner..display error message
            model.addAttribute("image",image);
            model.addAttribute("tags",image.getTags());
            model.addAttribute("deleteError",deleteError);   //add deleteError to the model
            return "images/image";                              //return the same image with the error message
        }

    }


    //This method converts the image to Base64 format
    private String convertUploadedFileToBase64(MultipartFile file) throws IOException {
        return Base64.getEncoder().encodeToString(file.getBytes());
    }

    //This method is used by the controller methods in this class.
    //This method accepts a string of tags from the calling method and returns a List of tags
    //This method in the process of converting the string into a list of tags accesses the concerned table in the db
    //and creates a new tag in the db if the tag is not found in the Tags table
    private List<Tag> findOrCreateTags(String tagNames) {
        StringTokenizer st = new StringTokenizer(tagNames, ",");
        List<Tag> tags = new ArrayList<Tag>();

        while (st.hasMoreTokens()) {
            String tagName = st.nextToken().trim();
            Tag tag = tagService.getTagByName(tagName);

            if (tag == null) {
                Tag newTag = new Tag(tagName);
                tag = tagService.createTag(newTag);
            }
            tags.add(tag);
        }
        return tags;
    }

    //This method is used by the controller methods of this class
    //The method receives the list of all tags
    //Converts the list of all tags to a single string containing all the tags separated by a comma
    //Returns the string
    private String convertTagsToString(List<Tag> tags) {
        StringBuilder tagString = new StringBuilder();

        for (int i = 0; i <= tags.size() - 2; i++) {
            tagString.append(tags.get(i).getName()).append(",");
        }

        Tag lastTag = tags.get(tags.size() - 1);
        tagString.append(lastTag.getName());

        return tagString.toString();
    }
}


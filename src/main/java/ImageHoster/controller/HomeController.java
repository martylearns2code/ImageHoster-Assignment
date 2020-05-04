package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


//This is the HOME or MAIN controller that will be accessed by the application after starting the application
//Takes care of the part to display the landing page which provides the user with the interface to interact with the application
@Controller
public class HomeController {

    @Autowired
    private ImageService imageService;

    @RequestMapping("/")
    public String getAllImages(Model model) {   //retrieves all the images from the imageHoster db and returns the index.html
        List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);        //after adding the images to the index.html
        return "index";
    }
}

package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.model.UserProfile;
import ImageHoster.service.ImageService;
import ImageHoster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

//This Controller handles all the requests send by the user/client browser that involve more specifically the User table in  the db
@Controller
public class UserController {

    @Autowired
    private UserService userService;                   //an instance of UserService wired in to this class

    @Autowired
    private ImageService imageService;                 //an instance of ImageService wired into this class

    //This controller method handles the request from the user to register the user on the Application
    //sends the required form which is registration.html to the user/client browser
    @RequestMapping("users/registration")
    public String registration(Model model) {
        User user = new User();                //instantiates a User object along with UserProfile and adds it to the model
        UserProfile profile = new UserProfile();
        user.setProfile(profile);
        model.addAttribute("User", user);
        return "users/registration";               //returns the registration.html after setting up the model
    }

    //This controller method is invoked when the user submits the registration form after entering details in the registration.html
    //Validates the password entered by the user by making the appropriate call to the business logic and if it is valid then..
    //calls the business logic again to register the new user information in the appropriate table in the imageHoster db
    @RequestMapping(value = "users/registration",method = RequestMethod.POST)
    public String registerUser(User user, Model model) {
        boolean validPassword = userService.validatePassword(user.getPassword());   //validates the password entered by the user
        if(validPassword) {                      //if valid register user
            userService.registerUser(user);
            return "users/login";
        }else{                                  //else add & display customized error message on the registration.html
            String passwordTypeError = "Password must contain atleast 1 alphabet, 1 number & 1 special character";
            User newUser = new User();
            model.addAttribute("User", newUser);
            model.addAttribute("passwordTypeError",passwordTypeError);  //add passwordError to model
            return "users/registration";               //return registration.html
        }
    }

    //This controller method is called when the request when the user registers successfully/or when user requests to login
    @RequestMapping("users/login")
    public String login() {
        return "users/login";
    }                   //return login.html

    //This controller method is called when the user submits request to login with the user details i.e username and password
    //This method validates the user details i.e checks whether the user is registered in the db by making call to the business logic
    //If the user details are valid then sets his session id and returns images.html
    //If the user details are not valid,it requests user to login with correct details or register new by returning the login.html
    @RequestMapping(value = "users/login", method = RequestMethod.POST)
    public String loginUser(User user, HttpSession session) {
        User existingUser = userService.login(user);
        if (existingUser != null) {                  //valid user...so log in session id and return images.html
            session.setAttribute("loggeduser", existingUser);
            return "redirect:/images";
        } else {
            return "users/login";                 //invalid user..return login.html
        }
    }

    //This controller is invoked when the user requests to logout of the application
    //The user's session is invalidated and the index.html is returned
    @RequestMapping(value = "users/logout", method = RequestMethod.POST)
    public String logout(Model model, HttpSession session) {
        session.invalidate();

        List<Image> images = imageService.getAllImages();    //fetch all images in the db
        model.addAttribute("images", images);
        return "index";                                         //return index.html
    }
}


package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//The @Service annotation denotes that this is the class which takes care of the business logic of the application
//This class acts as a intermediary between the controller classes and repository classes
//gets the information from the controller class and interacts with the repository class
//and returns the resulting information back to the controller which will respond back to the client browser's request
@Service
public class UserService {  //service class that executes the business logic for all the requests that involve user

    @Autowired
    private UserRepository userRepository;    //a instance of UserRepository

    //Call the registerUser() method in the UserRepository class to persist the user record in the database
    public void registerUser(User newUser) {
        userRepository.registerUser(newUser);
    }

    //The method calls checkUser() in the repository passing the User object to find out if the user is registered in the db
    public User login(User user) {
        return userRepository.checkUser(user.getUsername(), user.getPassword());
    }

    //This method validates the password entered by the user when user tries to register with the application
    //Returns true if password entered is valid else false
    public boolean validatePassword(String password) {
        boolean alphabetPresent,numberPresent,spCharPresent;
        alphabetPresent=numberPresent=spCharPresent=false;
        for(int i=0;i<password.length();i++){
            char ch = password.charAt(i);
            if(Character.isDigit(ch)) numberPresent=true;
            if(Character.isLetter(ch)) alphabetPresent=true;
            if(!Character.isLetter(ch) && !Character.isDigit(ch) && !Character.isSpaceChar(ch)) spCharPresent=true;
            if(numberPresent && alphabetPresent && spCharPresent) break;
        }
        return numberPresent && alphabetPresent && spCharPresent;
    }
}


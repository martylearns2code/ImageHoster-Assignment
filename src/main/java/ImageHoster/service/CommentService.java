package ImageHoster.service;


import ImageHoster.model.Comment;
import ImageHoster.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//The @Service annotation denotes that this is the class which takes care of the business logic of the application
//This class acts as a intermediary between the controller classes and repository classes
//gets the information from the controller class and interacts with the repository class
//and returns the resulting information back to the controller which will respond back to the client browser's request
@Service
public class CommentService {    // a service class to execute business logic for requests that involves comments
    @Autowired
    private CommentRepository commentRepository;           //a instance of the CommentRepository

    //The method calls the addCommentToImage() in the repository and passes the comment to be persisted in the db
    public void addCommentToImage(Comment newComment) {
        commentRepository.addCommentToImage(newComment);
    }
}

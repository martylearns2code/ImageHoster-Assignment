package ImageHoster.controller;


import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

//A Controller for the Comments which are posted on the displayed Image
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ImageService imageService;

    //This method handles the request to CREATE the Comments posted to the image and calls the appropriate service method to process the comment
    @RequestMapping(value="/image/{imageId}/{imageTitle}/comments",method= RequestMethod.POST)
    public String createComment(@RequestParam("comment") String comment, @PathVariable("imageId") Integer imageId, HttpSession session, Comment newComment){
        User commentedByUser= (User) session.getAttribute("loggeduser");   //gets the user who posted the comment
        LocalDate currentDate = LocalDate.now();
        Image imageCommentedOn =imageService.getImage(imageId);    //gets the image on which the comment was posted
        newComment.setText(comment);                               //the following code sets up the new comment
        newComment.setCreatedDate(currentDate);
        newComment.setImage(imageCommentedOn);
        newComment.setUser(commentedByUser);
        commentService.addCommentToImage(newComment);            //calls the appropriate method in service layer to process the comment
        return "redirect:/images/"+imageCommentedOn.getId()+'/'+imageCommentedOn.getTitle();  //returns or redirects to the appropriate html page

    }
}

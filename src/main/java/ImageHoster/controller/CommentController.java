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

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ImageService imageService;

    //This method handles the request to commit the comments added to the image to the database.
    @RequestMapping(value="/image/{imageId}/{imageTitle}/comments",method= RequestMethod.POST)
    public String createComment(@RequestParam("comment") String comment, @PathVariable("imageId") Integer imageId, HttpSession session, Comment newComment){
        User commentedByUser= (User) session.getAttribute("loggeduser");
        LocalDate currentDate = LocalDate.now();
        Image imageCommentedOn =imageService.getImage(imageId);
        newComment.setText(comment);
        newComment.setCreatedDate(currentDate);
        newComment.setImage(imageCommentedOn);
        newComment.setUser(commentedByUser);
        commentService.addCommentToImage(newComment);
        return "redirect:/images/"+imageCommentedOn.getId()+'/'+imageCommentedOn.getTitle();

    }
}

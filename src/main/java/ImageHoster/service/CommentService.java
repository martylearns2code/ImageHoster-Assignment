package ImageHoster.service;


import ImageHoster.model.Comment;
import ImageHoster.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    public void addCommentToImage(Comment newComment) { commentRepository.addCommentToImage(newComment); }
}

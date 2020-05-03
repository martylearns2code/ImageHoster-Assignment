package ImageHoster.repository;

import ImageHoster.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
@Repository
public class CommentRepository {
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;


    //Method to persist the new comment to the comments table
    public Comment addCommentToImage(Comment newComment) {
            EntityManager em=emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            try{
                transaction.begin();
                em.persist(newComment);
                transaction.commit();
            }catch(Exception e){
                transaction.rollback();
            }
            return newComment;
    }

}

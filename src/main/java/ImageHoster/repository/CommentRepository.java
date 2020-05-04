package ImageHoster.repository;

import ImageHoster.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
//This class is annotated with the @Repository which means that this is the class that will do all the interactions with the database
//In this application the db that is being used is imageHoster db
//This class uses the Datasource bean and EntityManagerFactory bean configured in the JpaConfig class
//This class also uses a persistenceUnit which is included in the class and whose details are obtained from the persistence.xml
@Repository
public class CommentRepository {  //a repository that is created to handle transactions specifically that involve the "comments" table

    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;


    //Method to persist the new comment to the comments table
    //The appropriate business logic or service layer method calls this method and passes on the new comment
    public Comment addCommentToImage(Comment newComment) {
            EntityManager em=emf.createEntityManager();     //entity manager to manage the transactions with the "comments" table
            EntityTransaction transaction = em.getTransaction();            //transaction obtained from the entity manager
            try{
                transaction.begin();                 //begin the transaction
                em.persist(newComment);             //persist the new comment
                transaction.commit();               //commit the transaction
            }catch(Exception e){
                transaction.rollback();             //rollback if the transaction fails
            }
            return newComment;                       //return the committed comment
    }

}

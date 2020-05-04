package ImageHoster.repository;

import ImageHoster.model.Image;
import ImageHoster.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import java.util.List;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
//This class is annotated with the @Repository which means that this is the class that will do all the interactions with the database
//In this application the db that is being used is imageHoster db
//This class uses the Datasource bean and EntityManagerFactory bean configured in the JpaConfig class
//This class also uses a persistenceUnit which is included in the class and whose details are obtained from the persistence.xml
@Repository
public class ImageRepository {   //this repository handles all the transactions that involve the "iamges" table

    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;


    //The method receives the Image object to be persisted in the "images" table in database
    //The appropriate  method from ImageService-service layer-calls this method and passes on the image object
    public Image uploadImage(Image newImage) {

        EntityManager em = emf.createEntityManager();       //obtain the entity manager
        EntityTransaction transaction = em.getTransaction();   //obtain the transaction

        try {
            transaction.begin();                //begin transaction
            em.persist(newImage);               //persist the newImage
            transaction.commit();               //commit the transaction
        } catch (Exception e) {
            transaction.rollback();             //rollback if the transaction fails
        }
        return newImage;
    }

    //This method fetches all the images from the "images" table and returns them as a list of images
    //Appropriate method from the ImageService-service layer calls this method
    public List<Image> getAllImages() {
        EntityManager em = emf.createEntityManager();  //obtain the entity manager
        TypedQuery<Image> query = em.createQuery("SELECT i from Image i", Image.class);  //create the Query
        List<Image> resultList = query.getResultList();  //execute the query and fetch the list of images

        return resultList;            //return the image list
    }

    //This method fetches an image from the "images" table that corresponds to the imageId passed on to this method
    //Appropriate method from ImageService-service layer calls this method
    //Returns the image fetched from the database
    public Image getImage(Integer imageId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Image> typedQuery = em.createQuery("SELECT i from Image i where i.id =:imageId", Image.class).setParameter("imageId", imageId);
        Image image = typedQuery.getSingleResult(); //fetch the image from the typed query created above
        return image;    //return the image
    }

    //The method receives the Image object to be updated in the database
    //Appropriate method from the ImageService-service layer calls this method and passes on a image object
    public void updateImage(Image updatedImage) {
        EntityManager em = emf.createEntityManager();       //obtain entity manager
        EntityTransaction transaction = em.getTransaction();   //obtain a transaction

        try {
            transaction.begin();                                 //begin transaction
            em.merge(updatedImage);                              //merge is used to update the image as it is in a detached state
            transaction.commit();                                //commit the transaction
        } catch (Exception e) {
            transaction.rollback();                              //rollback if the transaction fails
        }
    }

    //This method receives the Image id of the image to be deleted  from the "images" table in the database
    //Appropriate method from the ImageService-service layer calls this method after confirming the ownership of the image
    public void deleteImage(Integer imageId) {
        EntityManager em = emf.createEntityManager();             //gets the entity manager
        EntityTransaction transaction = em.getTransaction();      //gets the transaction

        try {
            transaction.begin();                                  //begins the transaction
            Image image = em.find(Image.class, imageId);          //the image has to be fetched from the db to make it's state persisted
            em.remove(image);         //only when the image is in the persistent state,the remove() can be used to delete the image
            transaction.commit();     //commit the transaction
        } catch (Exception e) {
            transaction.rollback();   //rollback if transaction fails
        }
    }

    //This method receives the imageId and the userId of the user who requested to delete this image
    //Appropriate method from the ImageService-service layer calls this method
    //checks the ownership of the image and returns a boolean based on the result of the query
    public boolean checkOwnerOfImage(Integer userId, Integer imageId) {
        EntityManager em = emf.createEntityManager();
        Query getOwnerQuery = em.createQuery("select i.user from Image i where i.id=:imageId").setParameter("imageId", imageId);
        User ownerOfImage = (User) getOwnerQuery.getSingleResult();   //obtain the owner of the image
        return (ownerOfImage.getId().equals(userId));            //return whether the user is owner of the image-a boolean
    }


}


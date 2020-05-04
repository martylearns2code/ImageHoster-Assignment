package ImageHoster.repository;

import ImageHoster.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.*;


//The annotation is a special type of @Component annotation which describes that the class defines a data repository
//This class is annotated with the @Repository which means that this is the class that will do all the interactions with the database
//In this application the db that is being used is imageHoster db
//This class uses the Datasource bean and EntityManagerFactory bean configured in the JpaConfig class
//This class also uses a persistenceUnit which is included in the class and whose details are obtained from the persistence.xml
@Repository
public class TagRepository {    //a repository to handle the transactions that involve the "Tags"table in the db

    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //This method receives a tag object that has to be persisted in the "Tags" table and returns the newly created tag
    //A method from the ImageService-service layer calls this method as images have a "has-a-tag"relation with tags
    public Tag createTag(Tag tag) {
        EntityManager em = emf.createEntityManager();            //get a entity manager
        EntityTransaction transaction = em.getTransaction();     //get a transaction

        try {
            transaction.begin();              //begin transaction
            em.persist(tag);                  //persist the new tag
            transaction.commit();             //commit transaction
        } catch (Exception e) {
            transaction.rollback();            //rollback if transaction fails
        }
        return tag;                            //return tag
    }

    //This method receives a tagname-a string,and it looks for a tag with that name in the "Tags" table
    //and returns the tag object if found
    //A method from the ImageService-service layer calls this method as images have a "has-a-tag"relation with tags
    public Tag findTag(String tagName) {
        EntityManager em = emf.createEntityManager();  //get the entity manager
        try {
            TypedQuery<Tag> typedQuery = em.createQuery("SELECT t from Tag t where t.name =:tagName", Tag.class).setParameter("tagName", tagName);
            return typedQuery.getSingleResult(); //return the Tag if query is successful
        } catch (NoResultException nre) {
            return null;                    //return null if tag not found
        }
    }
}


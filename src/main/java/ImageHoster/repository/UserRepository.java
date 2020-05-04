package ImageHoster.repository;

import ImageHoster.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
//This class is annotated with the @Repository which means that this is the class that will do all the interactions with the database
//In this application the db that is being used is imageHoster db
//This class uses the Datasource bean and EntityManagerFactory bean configured in the JpaConfig class
//This class also uses a persistenceUnit which is included in the class and whose details are obtained from the persistence.xml
@Repository
public class UserRepository {  // a repository that handles all interactions that involve the "users" table in the db

    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //This method receives the User object to be persisted in the database
    //The appropriate method from the UserService-service layer calls this method and passes on the User object
    //the method receives the User object only after the password entered by the user confirms to the specified format
    public void registerUser(User newUser) {
        EntityManager em = emf.createEntityManager();    //get entity manager
        EntityTransaction transaction = em.getTransaction();           //get a transaction

        try {
            transaction.begin();     //begin the transaction
            em.persist(newUser);    //persist() method changes the state of the model object from transient state to persistence state
            transaction.commit();   //commit the transaction
        } catch (Exception e) {
            transaction.rollback();   //rollback if transaction fails
        }
    }


    //This method receives the entered username and password from the client when the user tries to login
    //The Appropriate method in the UserService-service layer calls this method
    //returns the User object if a match is found otherwise returns null
    public User checkUser(String username, String password) {
        EntityManager em = emf.createEntityManager();             //get entity manager
        try {
            TypedQuery<User> typedQuery = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            typedQuery.setParameter("username", username);  //create query and set the parameters for the query
            typedQuery.setParameter("password", password);
            return typedQuery.getSingleResult();          //return user if found
        } catch (NoResultException nre) {
            return null;                                 //return null if user not found
        }
    }
}

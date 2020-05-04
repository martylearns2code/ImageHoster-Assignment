package ImageHoster.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity annotation specifies that the corresponding class is a JPA entity
//This class is annotated with @Entity annotation which indicates to the application
//that this class should be represented as a Table in the imageHoster db
//so a Table is created according to the attributes mentioned in this class
// and it is done as per the annotations which mark various attributes
@Entity
@Table(name = "users")
public class User {

    //various attributes of the class that will be represented as columns in the table "users" in imageHoster db
    //each attribute is marked with @Annotations that has to be implemented while creating the table
    // and while performing various transactions involving this table in the db
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private UserProfile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<Comment> comments= new ArrayList<>();


    //Default constructor for the class
    public User(){

    }


    //Getters and Setters for all the attributes of the class

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Comment> getComments() { return comments; }

    public void setComment(List<Comment> comments) { this.comments = comments; }
}



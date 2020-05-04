package ImageHoster.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//@Entity annotation specifies that the corresponding class is a JPA entity
//This class is annotated with @Entity annotation which indicates to the application
//that this class should be represented as a Table in the imageHoster db
//so a Table is created according to the attributes mentioned in this class
// and it is done as per the annotations which mark various attributes
@Entity
@Table(name = "images")
public class Image {

    //various attributes of the class that will be represented as columns in the table "images" in imageHoster db
    //each attribute is marked with @Annotations that has to be implemented while creating the table
    // and while performing various transactions involving this table in the db
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String imageFile;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy="image",cascade=CascadeType.REMOVE,fetch=FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    //constructors of the class
    public Image() {
    }

    public Image(int id, String title, String imageFile, Date date) {
        this.id = id;
        this.title = title;
        this.imageFile = imageFile;
        this.date = date;
    }

    public Image(int id, String title, String imageFile, String description, Date date) {
        this.id = id;
        this.title = title;
        this.imageFile = imageFile;
        this.description = description;
        this.date = date;
    }


    //Getters and Setters for all the attributes of the class

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) { this.comments = comments; }
}


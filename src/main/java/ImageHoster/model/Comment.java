package ImageHoster.model;

import javax.persistence.*;
import java.time.LocalDate;


//@Entity annotation specifies that the corresponding class is a JPA entity
//This class is annotated with @Entity annotation which indicates to the application
//that this class should be represented as a Table in the imageHoster db
//so a Table is created according to the attributes mentioned in this class
// and it is done as per the annotations which mark various attributes
@Entity
@Table(name="comments")
public class Comment {

    //various attributes of the class that will be represented as columns in the table "comments" in imageHoster db
    //each attribute is marked with @Annotations that has to be implemented while creating the table
    // and while performing various transactions involving this table in the db
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(name="date_created_on")
    private LocalDate createdDate;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="image_id")
    private Image image;

    //Default constructor of the class
    public Comment(){

    }
    //Getter and Setter methods for all the attributes in the class

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}

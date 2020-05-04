package ImageHoster.model;

import javax.persistence.*;
import java.util.List;

//@Entity annotation specifies that the corresponding class is a JPA entity
//This class is annotated with @Entity annotation which indicates to the application
//that this class should be represented as a Table in the imageHoster db
//so a Table is created according to the attributes mentioned in this class
// and it is done as per the annotations which mark various attributes
@Entity
@Table(name = "Tags")
public class Tag {

    //various attributes of the class that will be represented as columns in the table "Tags" in imageHoster db
    //each attribute is marked with @Annotations that has to be implemented while creating the table
    // and while performing various transactions involving this table in the db
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")   //mapped by "tags" field in the images table
    private List<Image> images;

    //Default constructor for the class
    public Tag() {
    }

    //Getters and setters for all the attributes of the class

    public Tag(String tagName) {
        this.name = tagName;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}


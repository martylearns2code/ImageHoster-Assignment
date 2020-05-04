package ImageHoster.model;

import javax.persistence.*;

//@Entity annotation specifies that the corresponding class is a JPA entity
//This class is annotated with @Entity annotation which indicates to the application
//that this class should be represented as a Table in the imageHoster db
//so a Table is created according to the attributes mentioned in this class
// and it is done as per the annotations which mark various attributes
@Entity
@Table(name = "user_profile")
public class UserProfile {

    //various attributes of the class that will be represented as columns in the table "user_profile" in imageHoster db
    //each attribute is marked with @Annotations that has to be implemented while creating the table
    // and while performing various transactions involving this table in the db
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "mobile_number")
    private String mobileNumber;

    //Default constructor for the class
    public UserProfile() {
    }

    //Getters and Setters for all the attributes of the class

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}


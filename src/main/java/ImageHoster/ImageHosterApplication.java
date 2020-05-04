package ImageHoster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//This is where the Application starts as this is the entry point for the application...
//This class is annotated with @Springbootapplication annotation which denotes that this is a Springboot application
// and this is  where the application should executing..
@SpringBootApplication
public class ImageHosterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImageHosterApplication.class, args);
    }
}


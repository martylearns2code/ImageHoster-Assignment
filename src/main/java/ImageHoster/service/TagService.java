package ImageHoster.service;

import ImageHoster.model.Tag;
import ImageHoster.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//The @Service annotation denotes that this is the class which takes care of the business logic of the application
//This class acts as a intermediary between the controller classes and repository classes
//gets the information from the controller class and interacts with the repository class
//and returns the resulting information back to the controller which will respond back to the client browser's request
@Service
public class TagService {  //service class to execute business logic for all requests that involve Tags
    @Autowired
    private TagRepository tagRepository;        //a instance of TagRepository

    //The method calls getTagByName() in the repository passing the title of the tag
    public Tag getTagByName(String title) {
        return tagRepository.findTag(title);
    }

    //The method calls createTag() in the repository with the tag that has to be persisted in the db
    public Tag createTag(Tag tag) {
        return tagRepository.createTag(tag);
    }
}


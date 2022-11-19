package photos;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.HashSet;

public class Photo {


    String path;
    FileTime lastModified;
    String location;
    HashSet<String> tags;

    public Photo(String path){
        this.path = path;
    }
}

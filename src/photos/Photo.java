package photos;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class Photo {


    private String path;
    private String caption;
    Date lastModified;

    private ArrayList<String> tags;

    public Photo(String path){
        this.path = path;
        tags = new ArrayList<String>();
        lastModified = new Date(new File(path.substring(5)).lastModified());
    }

    public String getPath(){
        return path;
    }
    public void setCaption(String caption){
        this.caption = caption;
    }
    public String getCaption(){
        return caption;
    }

    public void addTag(String tag){
        if(tag.startsWith("location=")&&tags.size()>0&&tags.get(0).startsWith("location=")){
            tags.remove(0);
        }

        if(tags.contains(tag)==false){
            if(tag.startsWith("location=")){
                tags.add(0,tag);
            }
            else{
                tags.add(tag);
            }

        }
    }
    public ArrayList<String> getTags(){
        return tags;
    }
}

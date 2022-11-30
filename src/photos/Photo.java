package photos;

import javafx.util.converter.LocalDateStringConverter;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.time.*;

/**
 * <h1>The Photo Class</h1>
 * Class used to hold information on a single photo
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-30
 */
public class Photo implements Serializable {


    private String path;
    private String caption;
    public Date lastModified;
    private LocalDate last_date_modified;
    private ArrayList<String> tags;


    /**
     * Initializes a photo object
     * @param path The path to the photo in the machine
     */
    public Photo(String path){
        this.path = path;
        tags = new ArrayList<String>();


        lastModified =  new Date(new File(path.substring(5)).lastModified());
        last_date_modified = lastModified.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Getter method for the path
     * @return String of the path.
     */
    public String getPath(){
        return path;
    }

    /**
     * Setter method for the caption
     * @param caption String of the caption
     * @return Nothing
     */
    public void setCaption(String caption){
        this.caption = caption;
    }

    /**
     * Getter method for the caption
     * @return String of the caption
     */
    public String getCaption(){
        return caption;
    }

    /**
     * Getter method for the last modified date
     * @return The last modified Date
     */
    public Date getLastModified(){ return lastModified; }

    /**
     * Getter method for the last modified date
     * @return LocalDate
     */
    public LocalDate getLast_date_modified(){ return last_date_modified; }

    /**
     * Add a tag to the photo
     * @param tag The tag as a String
     * @return Nothing
     */
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

    /**
     * Getter method for the tags
     * @return ArrayList<String> of the tags
     */
    public ArrayList<String> getTags(){
        return tags;
    }

}

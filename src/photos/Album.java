package photos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * <h1>The Album Class</h1>
 * Class used to store information about an album
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-30
 */
public class Album implements Serializable {
    private ArrayList<Photo> photos;
    private String name;
    private Date earliest;
    private Date latest;

    /**
     * Initializes the album object
     * @param name The name of this album
     */
    public Album(String name){
        this.name = name;
        photos = new ArrayList<Photo>();
        earliest = null;
        latest = null;
    }

    /**
     * Adds a photo to this album
     * @param a The photo to be added
     * @return Nothing
     */
    public void addPhoto(Photo a){
        if(photos.contains(a)){
            return;
        }
        if(earliest == null && latest ==null){
            this.earliest = a.lastModified;
            this.latest = a.lastModified;
        }

        photos.add(a);
        if (this.earliest.after(a.lastModified)){
            this.earliest = a.lastModified;
        }
        if(this.latest.before(a.lastModified)){
            this.latest = a.lastModified;
        }
    }

    /**
     * Renames the current album to a different name
     * @param a The new name
     * @return Nothing
     */
    public void rename(String a){
        name=a;
    }


    /**
     * Getter method for the album name
     * @return String
     */
    public String getName(){
        return name;
    }

    /**
     * The string used to display the album
     * @return String
     */
    public String toString(){
        if(earliest==null){
            return this.name+"\n"+this.getPhotos().size()+" photos" ;
        }
        return this.name+"\n"+this.getPhotos().size()+" photos" +"\n"+this.earliest+" - "+this.latest;
    }

    /**
     * Getter method for all the photos in the album
     * @return ArrayList<Photo>
     */
    public ArrayList<Photo> getPhotos(){
        return photos;
    }

    /**
     * Removes the photo from the album
     * @param a The photo to be removed
     * @return Nothing
     */

    public void removePhoto(Photo a){
        photos.remove(a);
        if(photos.size()==0){
            earliest = null;
            latest = null;
        }
        else{
            earliest = photos.get(0).lastModified;
            latest = photos.get(0).lastModified;
            for(Photo photo: photos){
                if (this.earliest.after(photo.lastModified)){
                    this.earliest = photo.lastModified;
                }
                if(this.latest.before(photo.lastModified)){
                    this.latest = photo.lastModified;
                }
            }
        }
    }
}

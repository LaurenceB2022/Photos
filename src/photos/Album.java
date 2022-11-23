package photos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Album implements Serializable {
    private ArrayList<Photo> photos;
    private String name;
    private Date earliest;
    private Date latest;

    public Album(String name){
        this.name = name;
        photos = new ArrayList<Photo>();
        earliest = null;
        latest = null;
    }

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
    public void rename(String a){
        name=a;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        if(earliest==null){
            return this.name+"\n"+this.getPhotos().size()+" photos" ;
        }
        return this.name+"\n"+this.getPhotos().size()+" photos" +"\n"+this.earliest+" - "+this.latest;
    }

    public ArrayList<Photo> getPhotos(){
        return photos;
    }

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

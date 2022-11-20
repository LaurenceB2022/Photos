package photos;

import java.util.ArrayList;

public class Album {
    private ArrayList<Photo> photos;
    private String name;

    public Album(String name){
        this.name = name;
        photos = new ArrayList<Photo>();
    }

    public void addPhoto(Photo a){
        photos.add(a);
    }
    public void rename(String a){
        name=a;
    }

    public String toString(){
        return this.name;
    }
}

package photos;

import java.util.ArrayList;

public class Album {
    private ArrayList<Photo> photos;

    public Album(){
        photos = new ArrayList<Photo>();
    }

    public void addPhoto(Photo a){
        photos.add(a);
    }
}

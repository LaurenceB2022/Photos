package photos.users;

import photos.Photo;

import java.util.ArrayList;

public class User {

    private String username;
    private ArrayList<Photo> photos;

    public User(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
    }
    
}

package photos.users;

import photos.Album;
import photos.Photo;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String username;
    private HashMap<String,Album> albums;

    public User(String username){
        this.username = username;
        albums = new HashMap<String,Album>();
    }

    public String getUsername(){
        return username;
    }

    public Album createAlbum(String name){
        albums.put(name,new Album());
        return albums.get(name);
    }

    public void renameAlbum(String oldName, String newName){
        albums.put(newName,albums.get(oldName));
        albums.remove(oldName);
    }
    public void deleteAlbum(String name){
        albums.remove(name);
    }
    
}

package photos.users;

import photos.Album;
import photos.Photo;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String username;
    private ArrayList<Album> albums;

    public User(String username){
        this.username = username;
        albums = new ArrayList<Album>();
    }

    public String getUsername(){
        return username;
    }

    public Album createAlbum(String name){
        albums.add(new Album(name));
        return albums.get(0);
    }


    public void deleteAlbum(Album album){
        albums.remove(album);
    }

    public ArrayList<Album> getAlbums(){
        return albums;
    }
    
}

package photos.users;

import photos.Album;
import photos.Photo;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String username;
    private ArrayList<Album> albums;

    public ArrayList<String> tagTypes;

    public User(String username){
        this.username = username;
        albums = new ArrayList<Album>();
        tagTypes = new ArrayList<String>();
        tagTypes.add("location");
    }

    public String getUsername(){
        return username;
    }

    public Album createAlbum(String name){
        for(Album a:albums){
            if (a.getName().equals(name)){
                return a;
            }
        }
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

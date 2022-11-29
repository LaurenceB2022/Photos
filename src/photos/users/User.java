package photos.users;

import photos.Album;
import photos.Photo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {

    private String username;
    private ArrayList<Album> albums;

    public ArrayList<String> tagTypes;
    public ArrayList<Photo> userPhotos;

    public User(String username){
        this.username = username;
        albums = new ArrayList<Album>();
        tagTypes = new ArrayList<String>();
        tagTypes.add("location");
        tagTypes.add("person");
        userPhotos = new ArrayList<Photo>();
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
    public int getNumAlbums(){ return albums.size(); }

    public String toString(){
        return this.username;
    }
}

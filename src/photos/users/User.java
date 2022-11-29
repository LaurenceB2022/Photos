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
        Photo first = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/cherry-blossoms-korea-beautiful-34712339.jpeg");
        Photo second = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/feet-bathroom-scale-isolated-792851.jpeg");
        Photo third = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/horse-racing-hong-kong-34749739.jpeg");
        Photo fourth = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/low-gi-foods-healthy-weight-loss-slimming-diet-29310784.jpeg");
        Photo fifth = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/supermarket-refrigerated-shelves-december-located-parknshop-tseung-kwan-o-hong-kong-35867257.jpeg");
        userPhotos.add(first);
        userPhotos.add(second);
        userPhotos.add(third);
        userPhotos.add(fourth);
        userPhotos.add(fifth);
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

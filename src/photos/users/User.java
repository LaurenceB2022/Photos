package photos.users;

import photos.Album;
import photos.Photo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>The User Class</h1>
 * Used to hold information about a specific user
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-30
 */
public class User implements Serializable {

    private String username;
    private ArrayList<Album> albums;

    public ArrayList<String> tagTypes;
    private ArrayList<Photo> userPhotos;

    /**
     * Initializes a User with the given name
     * @param username String that is the user's name
     */
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

    /**
     * Getter method for username
     * @return String The Username
     */
    public String getUsername(){
        return username;
    }


    /**
     * Creates new album
     * @param name The new album's name
     * @return Album the one just created
     */
    public Album createAlbum(String name){
        for(Album a:albums){
            if (a.getName().equals(name)){
                return a;
            }
        }
        albums.add(new Album(name));
        return albums.get(0);
    }

    /**
     * Deletes album
     * @param album The album to be deleted from this user
     * @return Nothing
     */
    public void deleteAlbum(Album album){
        albums.remove(album);
    }

    /**
     * Getter method for albums
     * @return ArrayList<Album> All the albums
     */
    public ArrayList<Album> getAlbums(){
        return albums;
    }

    /**
     * Used to add a photo to this user
     * @param a The photo
     * @return Nothing
     */

    public void addPhoto(Photo a){
        if(userPhotos.contains(a)){
            return;
        }
        userPhotos.add(a);

    }

    /**
     * Getter method for all the user photos
     * @return ArrayList<Photo>
     */
    public ArrayList<Photo> getUserPhotos(){
        return userPhotos;
    }

    /**
     * Used to display the user's name
     * @return String the user's name
     */
    public String toString(){
        return this.username;
    }
}

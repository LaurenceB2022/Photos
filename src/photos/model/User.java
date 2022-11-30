package photos.model;

import photos.Photos;

import java.io.Serializable;
import java.util.ArrayList;

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
        if(Photos.admin!=null){
            for(Photo a: Photos.admin.stockPhotos){
                userPhotos.add(a);
            }
        }

    }

    /**
     * Getter method for username
     * @return String of the username
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

    public Album getAlbum(String album_name){
        for(int index = 0; index < albums.size(); index++){
            if(albums.get(index).getName().compareTo(album_name) == 0){
                return albums.get(index);
            }
        }
        return null;
    }
    /**
     * Getter method for all the user photos
     * @return ArrayList<Photo> of the User's photos
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

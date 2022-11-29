package photos.users;

import photos.Album;
import photos.Photo;

import java.io.*;
import java.util.ArrayList;

/**
 * <h1>The Admin Class</h1>
 *  Used to hold all information about the app
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-30
 */

public class Admin implements Serializable{

    private final String username = "admin";
    public static final String storeFile = "users.dat";
    public static final String storeDir = "docs";
    public ArrayList<User> registered_users;

    /**
     * Initializes an Admin object with the stock user and photos
     */
    public Admin(){
        registered_users = new ArrayList<User>();
                User stock = new User("stock");

        Album stockAlbum = stock.createAlbum("stock");
        for(Photo e: stock.getUserPhotos()){
            stockAlbum.addPhoto(e);

        }
        registered_users.add(stock);
    }

    /**
     * Retrieves all the users in the app
     * @return ArrayList<User>
     */
    public ArrayList<User> getRegistered_users(){
        return registered_users;
    }

    /**
     * Adds a user to the app
     * @param username The user we are adding
     * @return Nothing>
     */
    public void addUser(User username){
        registered_users.add(username);
    }

    /**
     * Removes a user from the app
     * @param name The username of the user we are removing
     * @return Nothing
     */
    public void removeUser(String name){
        for(int i = 0; i < registered_users.size(); i++){
            if(registered_users.get(i).equals(name)){
                registered_users.remove(i);
            }
        }
    }
    /**
     * Used to display the name of the user (Admin in this case)
     * @return String of User name
     */
    @Override
    public String toString(){
        return username;
    }
}

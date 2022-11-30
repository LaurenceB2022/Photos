package model;

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
    public ArrayList<Photo> stockPhotos;

    /**
     * Initializes an Admin object with the stock user and photos.
     */
    public Admin(){
        registered_users = new ArrayList<User>();
        stockPhotos = new ArrayList<Photo>();
        Photo first = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/cherry-blossoms-korea-beautiful-34712339.jpeg");
        Photo second = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/feet-bathroom-scale-isolated-792851.jpeg");
        Photo third = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/horse-racing-hong-kong-34749739.jpeg");
        Photo fourth = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/low-gi-foods-healthy-weight-loss-slimming-diet-29310784.jpeg");
        Photo fifth = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/supermarket-refrigerated-shelves-december-located-parknshop-tseung-kwan-o-hong-kong-35867257.jpeg");
        stockPhotos.add(first);
        stockPhotos.add(second);
        stockPhotos.add(third);
        stockPhotos.add(fourth);
        stockPhotos.add(fifth);
        User stock = new User("stock");

        Album stockAlbum = stock.createAlbum("stock");

        stockAlbum.addPhoto(first);
        stockAlbum.addPhoto(second);
        stockAlbum.addPhoto(third);
        stockAlbum.addPhoto(fourth);
        stockAlbum.addPhoto(fifth);
        stock.getUserPhotos().add(first);
        stock.getUserPhotos().add(second);
        stock.getUserPhotos().add(third);
        stock.getUserPhotos().add(fourth);
        stock.getUserPhotos().add(fifth);
        registered_users.add(stock);
    }

    /**
     * Retrieves all the users in the app
     * @return ArrayList<User> of the users saved to the Admin.
     */
    public ArrayList<User> getRegistered_users(){
        return registered_users;
    }

    /**
     * Adds a user to the app
     * @param username The user we are adding to registered_users
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
     * @return String of the Admin name
     */
    @Override
    public String toString(){
        return username;
    }
}

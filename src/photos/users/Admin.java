package photos.users;

import photos.Album;
import photos.Photo;

import java.io.*;
import java.util.ArrayList;

public class Admin implements Serializable{

    private final String username = "admin";
    public static final String storeFile = "users.dat";
    public static final String storeDir = "docs";
    public ArrayList<User> registered_users;


    public Admin(){
        registered_users = new ArrayList<User>();
                User stock = new User("stock");
        Photo first = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/cherry-blossoms-korea-beautiful-34712339.jpeg");
        Photo second = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/feet-bathroom-scale-isolated-792851.jpeg");
        Photo third = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/horse-racing-hong-kong-34749739.jpeg");
        Photo fourth = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/low-gi-foods-healthy-weight-loss-slimming-diet-29310784.jpeg");
        Photo fifth = new Photo("file:"+System.getProperty("user.dir")+"/"+"data/supermarket-refrigerated-shelves-december-located-parknshop-tseung-kwan-o-hong-kong-35867257.jpeg");
        Album stockAlbum = stock.createAlbum("stock");
        stockAlbum.addPhoto(first);
        stockAlbum.addPhoto(second);
        stockAlbum.addPhoto(third);
        stockAlbum.addPhoto(fourth);
        stockAlbum.addPhoto(fifth);
        registered_users.add(stock);
    }

    public ArrayList<User> getRegistered_users(){
        return registered_users;
    }


    public void addUser(User username){
        registered_users.add(username);
    }

    public void removeUser(String name){
        for(int i = 0; i < registered_users.size(); i++){
            if(registered_users.get(i).equals(name)){
                registered_users.remove(i);
            }
        }
    }

    @Override
    public String toString(){
        return username;
    }
}

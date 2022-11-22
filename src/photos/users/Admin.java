package photos.users;

import java.io.*;
import java.util.ArrayList;

public class Admin implements Serializable{

    private final String username = "admin";
    public static final String storeFile = "users.dat";
    public static final String storeDir = "docs";
    public static ArrayList<User> registered_users;

    public Admin(){

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

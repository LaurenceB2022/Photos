package photos.users;

import java.io.*;
import java.util.ArrayList;

public class Admin implements Serializable{

    private final String username = "admin";
    public static final String storeFile = "users.dat";
    public static final String storeDir = "docs";
    ArrayList<User> registered_users;

    public Admin(){
        registered_users = readAdmin();
    }

    public ArrayList<User> getRegistered_users(){
        return registered_users;
    }

    public static void writeAdmin(Admin curr) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(storeDir + File.separator + storeFile));

        for(int index = 0; index < curr.getRegistered_users().size(); index++){
            oos.writeObject(curr.getRegistered_users().get(index));
        }
        oos.close();

    }


    public static ArrayList<User> readAdmin() throws ClassNotFoundException, IOException{
        ArrayList<User> userList = new ArrayList<>();
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(storeDir + File.separator + storeFile));

        try{
            while(true){
                User current = (User)ois.readObject();
                userList.add(current);
            }
        } catch (EOFException e) {
            //System.out.println("End of File.");
        } catch (Exception e) {
            //System.out.println("Here be error..."+e.getMessage());
        }

        ois.close();
        return userList;
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

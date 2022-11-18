package photos.users;

import java.io.*;
import java.util.ArrayList;

public class Admin {

    private final String username = "admin";
    ArrayList<User> registered_users;

    public Admin(){
        registered_users = readFromFile();
    }

    public ArrayList<User> readFromFile(){
        File myFile = new File("docs/userlist.txt");

        registered_users = new ArrayList<User>();

        try (BufferedReader bf = new BufferedReader(new FileReader(myFile))) {


            if (bf != null) {
                String currentLine = bf.readLine();
                while (currentLine != null) {
                    String user = bf.readLine();
                    registered_users.add(new User(user));
                    currentLine = bf.readLine();
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return registered_users;
    }

    public void addUser(String username){
        registered_users.add(new User(username));
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

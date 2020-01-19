package ir.amirab.serversocket.Db;

import java.util.ArrayList;
import java.util.List;

//create a fake database simulation with creating an object of this class
public class UserDB {
    ArrayList<User> users = new ArrayList<>();
    private static UserDB instance = null;

    public static UserDB getInstance() {
        if (instance != null) return instance;
        synchronized (UserDB.class) {
            instance = new UserDB();
        }
        return instance;
    }

    public boolean isTokenExists(String token) {
        for (User user : users) {
            if (user.token.equals(token)) return true;
        }
        return false;
    }

    public User getUserWithToken(String token) {
        for (User user : users) {
            if (user.token.equals(token)) return user;
        }
        return null;
    }

    public boolean addUser(User user) {
        if (users.contains(user)) return false;
        return users.add(user);
    }

    public List<User> getAllOnlineUsers() {
        ArrayList<User> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isOnline) onlineUsers.add( user);
        }
        return onlineUsers;
    }

    public void signOut(String token) {
        getUserWithToken(token).isOnline = false;
    }

    public void signIn(String token) {
        getUserWithToken(token).isOnline = true;
    }

    public User getUserWithName(String name) {
        for (User user : users) {
            if (user.name.equals(name)) return user;
        }
        return null;
    }

    public boolean addWaitingForChats(User requester, User receiver) {
        try {
            return receiver.getWaitingForMeList().add(requester);
        } catch (Exception e) {
            return false;
        }
    }
}

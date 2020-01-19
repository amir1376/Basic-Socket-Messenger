package ir.amirab.serversocket.Db;

import ir.amirab.serversocket.connectionmanager.ChatManager;

import java.util.ArrayList;
import java.util.List;

// a user model
public class User implements Cloneable {
    public String name;
    public String token;//fake token we use just ip for this
    public boolean isOnline;
    public ChatManager chatManager;// reference to the connected session
    public User currentActiveChat = null;// a simple active contact
    private List<User> waitingForMeList = new ArrayList<>();

    public User(String name,
                String token,
                boolean isOnline,
                ChatManager chatManager,
                User currentActiveChat,
                List<User> waitingForMeList) {
        this.name = name;
        this.token = token;
        this.isOnline = isOnline;
        this.chatManager = chatManager;
        this.currentActiveChat = currentActiveChat;
        this.setWaitingForMeList(waitingForMeList);
    }


    public User(User user) {
        this(user.name,
                user.token,
                user.isOnline,
                user.chatManager,
                user.currentActiveChat,
                user.getWaitingForMeList()
        );
    }


    public void setChatManager(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    public boolean isInChat() {
        return currentActiveChat != null;
    }

    public User getConnectedContact() {
        return currentActiveChat;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("your identification is name:").append(name).append(" , ").append("token:").append(token);
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return other.name.equals(name)
                && other.token.equals(token);
    }

    public List<User> getWaitingForMeList() {
        if (waitingForMeList != null) {
            return waitingForMeList;
        }
        synchronized (this) {
            waitingForMeList = new ArrayList<>();
        }
        return waitingForMeList;
    }

    public void setWaitingForMeList(List<User> waitingForMeList) {
        this.waitingForMeList = waitingForMeList;
    }
}

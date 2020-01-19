package ir.amirab.serversocket.connectionmanager.command;

import ir.amirab.serversocket.Db.User;
import ir.amirab.serversocket.Db.UserDB;

import java.io.IOException;
import java.util.List;

public class OnlineUsers extends Command {

    @Override
    public void execute() throws IOException {
        List<User> users = UserDB.getInstance().getAllOnlineUsers();
        replay("online users count: " + users.size());//zero base to one base
        for (int i = 0; i < users.size(); i++) {
            replay(users.get(i).name);
        }
    }
}
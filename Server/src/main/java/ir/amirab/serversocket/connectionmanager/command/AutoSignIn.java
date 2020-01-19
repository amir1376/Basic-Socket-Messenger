package ir.amirab.serversocket.connectionmanager.command;

import ir.amirab.serversocket.Db.User;
import ir.amirab.serversocket.Db.UserDB;
import ir.amirab.serversocket.connectionmanager.ChatManager;

import java.io.IOException;

public class AutoSignIn extends Command {


    @Override
    public void execute() throws IOException {
        ChatManager chatManager = getChatManager();
        String token = chatManager.getToken();
        UserDB db = UserDB.getInstance();
        User user = chatManager.getUserIfRegistered();
        if (user != null) {
            db.signIn(token);
            replay("signed in as " + user.name);
        }
    }
}
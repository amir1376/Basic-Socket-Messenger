package ir.amirab.serversocket.connectionmanager.command;

import ir.amirab.serversocket.Db.User;
import ir.amirab.serversocket.Db.UserDB;
import ir.amirab.serversocket.connectionmanager.ChatManager;

import java.io.IOException;

public class Register extends Command {


    @Override
    public void execute() throws IOException {
        ChatManager chatManager = getChatManager();
        String[] args = getArgs();
        if (args.length<1){
            chatManager.replay("enter name after /register {...}");
        }
        UserDB.getInstance().addUser(new User(
                args[0], chatManager.getToken(), true, chatManager, null, null
        ));
    }
}
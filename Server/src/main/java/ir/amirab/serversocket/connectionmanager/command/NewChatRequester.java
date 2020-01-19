package ir.amirab.serversocket.connectionmanager.command;

import ir.amirab.serversocket.Db.User;
import ir.amirab.serversocket.Db.UserDB;

import java.io.IOException;

public class NewChatRequester extends Command {

    @Override
    public void execute() throws IOException {
        UserDB db = UserDB.getInstance();
        String[] args = getArgs();
        if (args.length<1){
            replay("enter name after /chat {...}");
            return;
        }
        User requestingUser = db.getUserWithName(args[0]);
        if (requestingUser == null) {
            replay("user not found");
            return;
        }
        if (!requestingUser.isOnline) {
            replay("user is not online");
            return;
        }
        if (db.addWaitingForChats(getMyUser(), requestingUser)) {
            requestingUser.chatManager.replay(getMyUser().name + " wants to chat with you if you interested send /accept" + " " + getMyUser().name);
            replay("request was sent");
            return;
        } else {
            replay("some error accursed when requesting");
            return;
        }
    }
}
package ir.amirab.serversocket.connectionmanager.command;

import ir.amirab.serversocket.Db.User;
import ir.amirab.serversocket.Db.UserDB;

import java.io.IOException;
import java.util.List;

public class ChatAccepter extends Command {

    @Override
    public void execute() throws IOException {
        List<User> waitingForMeList = getMyUser().getWaitingForMeList();
        String[] args = getArgs();
        if (args.length<1){
            replay("enter name after /accept {...}");
        }
        User requestedUser = UserDB.getInstance().getUserWithName(args[0]);
        if (waitingForMeList.contains(requestedUser)) {
            getMyUser().currentActiveChat = requestedUser;
            requestedUser.currentActiveChat = getMyUser();
            requestedUser.chatManager.replay(getMyUser().name + " accepts your chat request type to chat with him");
            getMyUser().chatManager.replay("chat started with " + requestedUser.name);
            waitingForMeList.remove(requestedUser);
        } else {
            getMyUser().chatManager.replay("this user does not sent you chat request send /chat {name} to him if you want chat to him");
        }
    }
}
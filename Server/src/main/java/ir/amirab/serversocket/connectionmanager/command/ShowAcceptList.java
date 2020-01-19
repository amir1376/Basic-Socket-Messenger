package ir.amirab.serversocket.connectionmanager.command;

import ir.amirab.serversocket.Db.User;

import java.io.IOException;
import java.util.List;

public class ShowAcceptList extends Command {


    @Override
    public void execute() throws IOException {
        List<User> waitingForMeList = getMyUser().getWaitingForMeList();
        replay("requested chats from this users " + waitingForMeList.size());
        for (int i = 0; i < waitingForMeList.size(); i++) {
            replay(waitingForMeList.get(i).name);
        }
        replay("type /accept {user} to start chating");
    }
}
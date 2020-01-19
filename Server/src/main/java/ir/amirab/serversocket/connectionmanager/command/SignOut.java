package ir.amirab.serversocket.connectionmanager.command;

import ir.amirab.serversocket.Db.UserDB;

public class SignOut extends Command {


    @Override
    public void execute() {
        UserDB.getInstance().signOut(getChatManager().getToken());
    }
}
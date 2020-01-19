package ir.amirab.serversocket.connectionmanager.command;

import ir.amirab.serversocket.Db.User;
import ir.amirab.serversocket.connectionmanager.ChatManager;
import ir.amirab.serversocket.interfaces.Executable;

import java.io.IOException;

public abstract class Command implements Executable {
    private ChatManager chatManager;
    private String[] args;

    protected void replay(String msg) throws IOException {
         chatManager.replay(msg);
    }
    User getMyUser() {
        return getChatManager().getMyUser();
    }

    ChatManager getChatManager() {
        return chatManager;
    }

    String[] getArgs() {
        return args;
    }

    public Command setChatManager(ChatManager chatManager) {
        this.chatManager = chatManager;return this;
    }

    public  Command setArgs(String[] args) {
        this.args = args;
        return this;
    }
}
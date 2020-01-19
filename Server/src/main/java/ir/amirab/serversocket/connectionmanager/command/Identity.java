package ir.amirab.serversocket.connectionmanager.command;

import ir.amirab.serversocket.connectionmanager.ChatManager;

import java.io.IOException;

public class Identity extends Command {

    @Override
    public void execute() throws IOException {
        ChatManager chatManager = getChatManager();
        if (chatManager.isRegistered()) {
                chatManager.replay(chatManager.getMyUser().toString());
            } else {
            chatManager.replay("you are as guest you can send /register {name} to chat with others");
            }
    }
}
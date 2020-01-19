package ir.amirab.serversocket.connectionmanager.command;

import java.io.IOException;

public class Exit extends Command {

    @Override
    public void execute() throws IOException {
        replay("Good bye!");
        getChatManager().close();
    }
}
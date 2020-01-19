package ir.amirab.serversocket.connectionmanager;

import ir.amirab.serversocket.Db.User;
import ir.amirab.serversocket.Db.UserDB;
import ir.amirab.serversocket.connectionmanager.command.*;

import java.io.IOException;
import java.net.Socket;

public class ChatManager extends LineTextBaseSocketConnectionManager {
    private static final String CHAT_START_PROTOCOL = "CHAT_START";
    private User user = null;

    public ChatManager(Socket socket) {
        super(socket);
        setOnDisconnected(() -> {
            if (getMyUser() != null) {
                getMyUser().isOnline = false;
            }
        });
        setOnNewLineReceived(line -> {
            line = line.trim();
            try {
                if (isCommand(line)) {
                    handleCommand(getCommand(line));
                } else if (isRegistered()) {
                    User user = getMyUser();
                    if (user.isInChat()) {
                        user.getConnectedContact().chatManager.replay(user.name+": "+line);
                    } else {
                        replay("you are not in any conversation send chat {user} to begin");
                    }
                } else {
                    replay("you are not registered register first with /register {name}");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public User getMyUser() {
        return getUserIfRegistered();
    }


    private void handleCommand(String command) throws IOException {
        String name = getCommandName(command);
        String[] args = getCommandArgs(command);
        Command cmd = null;
        switch (name) {
            case "/id":
                cmd = new Identity();
                break;
            case "/register":
                cmd = new Register();
                break;
            case "/chat":
                cmd = new NewChatRequester();
                break;
            case "/accept":
                cmd = new ChatAccepter();
                break;
            case "/exit":
                cmd = new Exit();
                break;
            case "/signout":
                cmd = new SignOut();
                break;
            case "/acceptlist":
                cmd = new ShowAcceptList();
                break;
            case "/onlines":
                cmd = new OnlineUsers();
                break;
            default:
                replay("command \"" + name + "\" not found");
                break;
        }
        if (cmd != null) {
            cmd
                    .setChatManager(this)
                    .setArgs(args)
                    .execute();
        }
    }



    public String getToken() {
        return getSocket().getInetAddress() + ":" + getSocket().getPort();
    }

    public String[] getCommandArgs(String command) {
        String[] strings = command.split(" ");
        String[] o = new String[strings.length - 1];
        for (int i = 0; i < o.length; i++) {
            o[i] = strings[i + 1];
        }
        return o;
    }

    public String getCommandName(String command) {
        return command.split(" ")[0];
    }

    public boolean isRegistered() {
        if (user == null) {
            user = UserDB.getInstance().getUserWithToken(getToken());
        }
        return user != null;
    }


    public User getUserIfRegistered() {
        if (user == null) {
            user = UserDB.getInstance().getUserWithToken(getToken());
        }
        user = UserDB.getInstance().getUserWithToken(getToken());
        return user;
    }

    public String getCommand(String line) {
//        return line.substring(1);
        return line;
    }

    public boolean isCommand(String line) {
        return line.startsWith("/");
    }


    private boolean isStartingRequestIsValid() throws IOException {
        return readSingleLine().trim().equals(CHAT_START_PROTOCOL);
    }

    @Override
    public void manage() throws IOException {
        System.out.println("connected " + getSocket().getInetAddress());
        if (!isStartingRequestIsValid()) {
            replay("Bad Request");
            close();
            return;
        }
        System.out.println("ok proceeding");
        new AutoSignIn().setChatManager(this).execute();
        registerIncomingLines();
    }

    @Override
    protected void finishingUp() throws IOException {
        if (getMyUser() != null) {
            getMyUser().isOnline = false;
        }
        super.finishingUp();
    }
}

package ru.geekbrains.java2.chat.server.core;


import ru.geekbrains.java2.chat.common.Library;
import ru.geekbrains.java2.network.ServerSocketThread;
import ru.geekbrains.java2.network.ServerSocketThreadListener;
import ru.geekbrains.java2.network.SocketThread;
import ru.geekbrains.java2.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    private ServerSocketThread server;
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss: ");
    private final ChatServerListener listener;
    private Vector<SocketThread> clients = new Vector<>();
    private boolean ChatServerStatus;

    public ChatServer(ChatServerListener listener) {
        this.listener = listener;
    }

    public void start(int port) {
        if (server != null && server.isAlive())
            putLog("Server is already running");
        else
            server = new ServerSocketThread(this, "Chat server", port, 1000);
    }

    public void stop() {
        if (server == null || !server.isAlive())
            putLog("Server not running");
        else
            server.interrupt();
    }

    private void putLog(String msg) {
        msg = dateFormat.format(System.currentTimeMillis()) + Thread.currentThread().getName() + ": " + msg;
        listener.onChatServerMessage(msg);
    }

    /**
     * ServerSocketThread methods
     * */

    @Override
    public void onServerSocketThreadStart(ServerSocketThread thread) {
        putLog("Server thread started");
        ChatServerStatus = true;
        SqlClient.connect();
        sendToAuthorizedClients("Chat Server started. You can send messages");
    }

    @Override
    public void onServerSocketThreadStop(ServerSocketThread thread) {
        putLog("Server thread stopped");
        sendToAuthorizedClients("Chat Server stopped. You can't send messages");
        ChatServerStatus = false;
        SqlClient.disconnect();

    }

    @Override
    public void onServerSocketCreate(ServerSocketThread thread, ServerSocket server) {
        putLog("server created");
    }

    @Override
    public void onServerSocketAcceptTimeout(ServerSocketThread thread, ServerSocket server) {
        //putLog("socket timeout");
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, Socket socket) {
        String name = "SocketThread" + socket.getInetAddress() + ":" + socket.getPort();
        new ClientThread(this, name, socket);

    }

    @Override
    public void onServerSocketThreadException(ServerSocketThread thread, Exception e) {
        putLog("server exception");
    }

    /**
     * SocketThread methods
     * */

    @Override
    public synchronized void onSocketThreadStart(SocketThread thread, Socket socket) {
        putLog("socketthread start");
    }

    @Override
    public synchronized void onSocketThreadStop(SocketThread thread) {
        clients.remove(thread);
    }

    @Override
    public synchronized void onSocketThreadReady(SocketThread thread, Socket socket) {
        clients.add(thread);
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String value) {
        ClientThread client = (ClientThread) thread;
        if (ChatServerStatus) {
        if (client.isAuthorized()) {
            handleAuthMessage(client, Library.getMsgFormat(client.getNik(), value));
        }  else {
            handleNonAuthMessage(client, value);
        }
        }
        else {
             client.close();
        }
    }

    private void handleNonAuthMessage(ClientThread clientThread, String msg) {
        String[] arr = msg.split(Library.DELIMITER);
        if (arr.length != 3 || !arr[0].equals(Library.AUTH_REQUEST)) {
            clientThread.msgFormatError(msg);
            return;
        }
        String login = arr[1];
        String password = arr[2];
        String nickname = SqlClient.getNickname(login, password);
        if (nickname == null) {
            putLog(String.format("Invalid login attempt: l='%s', p='%s'", login, password));
            clientThread.authFail();
            return;
        }
        clientThread.authAccept(nickname);
        sendToAuthorizedClients(Library.getTypeBroadcast("Server", nickname + " connected!"));
    }

    private void handleAuthMessage(ClientThread client, String msg) {
        sendToAuthorizedClients(msg);
    }

    private void sendToAuthorizedClients(String value) {
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            client.sendMessage(value);
        }
    }

    @Override
    public synchronized void onSocketThreadException(SocketThread thread, Exception e) {
        putLog("socketthread exception");
        clients.remove(thread);
    }
}

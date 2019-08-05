package ru.geekbrains.java2.chat.server.core;

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
    private final Vector<SocketThread> clients = new Vector<>();

    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss: ");

    public void start(int port) {
        if (server != null && server.isAlive())
            System.out.println("Server is already running");
        else
            server = new ServerSocketThread(this, "Chat server", port, 1000);
    }

    public void stop() {
        if (server == null || !server.isAlive())
            System.out.println("Server not runing");
        else
            server.interrupt();
    }

    private void putLog(String msg) {
        msg = dateFormat.format(System.currentTimeMillis()) + Thread.currentThread().getName() + ": " + msg;
        System.out.println(msg);
    }

    /**
     * ServerSocketThread methods
     * */

    @Override
    public void onServerSocketThreadStart(ServerSocketThread thread) {
        putLog("started");
    }

    @Override
    public void onServerSocketThreadStop(ServerSocketThread thread) {
        putLog("stopped");
    }

    @Override
    public void onServerSocketCreate(ServerSocketThread thread, ServerSocket server) {
        putLog("server created");
    }

    @Override
    public void onServerSocketAcceptTimeout(ServerSocketThread thread, ServerSocket server) {
        putLog("socket timeout");
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, Socket socket) {
        String name = "SocketThread" + socket.getInetAddress() + ":" + socket.getPort();
        new SocketThread(this, name, socket);
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
        putLog("socketthread stop");
        clients.remove(thread);
    }

    @Override
    public synchronized void onSocketThreadReady(SocketThread thread, Socket socket) {
        putLog("socketthread ready");
        clients.add(thread);
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String value) {
        thread.sendMessage("echo: " + value);
        for (int i = 0; i < clients.size() ; i++) {
            clients.get(i).sendMessage(value);
        }
    }

    @Override
    public synchronized void onSocketThreadException(SocketThread thread, Exception e) {
        putLog("socketthread exception");
    }
}

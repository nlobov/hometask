package ru.geekbrains.java2.network.echo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8189);
             Socket socket = server.accept()) {

            System.out.println("We have a connection with a client");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String b = in.readUTF();
                out.writeUTF("echo: " + b);
                System.out.println("echoServer: "+b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

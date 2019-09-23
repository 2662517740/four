package com.h5.entity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread{
    Socket socket;

    public ServerThread(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("我：");
                String accpet = in.readUTF();
                System.out.println("\n"+"客户端：" + accpet);
                String send = scanner.next();
                out.writeUTF(send);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}

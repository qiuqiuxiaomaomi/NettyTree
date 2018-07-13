package com.bonaparte.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by yangmingquan on 2018/7/12.
 */
public class NettyBioServer {
    public static void main(String[] args) throws IOException {
        int port = 8008;
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("server is start on port:" + port);
            Socket socket = null;
            while(true){
                System.out.println("accept is wait");
                socket = serverSocket.accept();
                System.out.println("accept is blocked");
                new Thread(new NettyBioServerHandler(socket)).start();
            }
        }finally {
            if (serverSocket != null){
                System.out.println("server is closed");
                serverSocket.close();
                serverSocket=null;
            }
        }
    }
}

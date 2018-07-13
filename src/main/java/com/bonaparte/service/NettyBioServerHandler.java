package com.bonaparte.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by yangmingquan on 2018/7/12.
 */
public class NettyBioServerHandler implements Runnable{
    private Socket socket;

    public NettyBioServerHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader((new InputStreamReader(this.socket.getInputStream())));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            String body = null;
            while(true){
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("Server receive order: " + body);
            }
        }catch (Exception e){
             if (in != null){
                 try {
                     in.close();
                 } catch (IOException e1) {
                     e1.printStackTrace();
                 }
             }
             if (out != null){
                 out.close();
             }
             if (this.socket != null){
                 try {
                     this.socket.close();
                 } catch (IOException e1) {
                     e1.printStackTrace();
                 }
             }
        }
    }
}

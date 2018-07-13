package com.bonaparte.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by yangmingquan on 2018/7/12.
 */
public class NettyBioClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            socket = new Socket("127.0.0.1", 8008);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Qurty Time Order");
            System.out.println("Send order 2 server succeed");
            String resp = in.readLine();
            System.out.println("Now is :" +resp);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in != null){
                in.close();
            }
            if (socket != null){
                socket.close();
            }
        }
    }
}

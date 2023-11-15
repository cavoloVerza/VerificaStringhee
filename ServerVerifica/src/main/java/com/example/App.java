package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main( String[] args ) {

        System.out.println("");
        //Server

        try {

            System.out.println( "Server in avvio!" );
            System.out.println("");

            ServerSocket server = new ServerSocket(3000);
            
            do {

                Socket client = server.accept();
                MioThread m = new MioThread(client);
                m.start();

            }while(true);

        } 
        
        catch(Exception e) {

            System.out.println(e.getMessage());
            System.out.println( "Errore durante l'istanza del Server!" );
        }

    }
}

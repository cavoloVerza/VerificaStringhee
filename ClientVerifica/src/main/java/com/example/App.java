package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class App {
    public static void main( String[] args ) {

        System.out.println("");
        // Client

        try { 
            
            System.out.println( "Connessione effettuata. Digita ESCI per uscire.");
            System.out.println("");
            Socket s = new Socket("localhost", 3000);

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            Scanner input = new Scanner(System.in);

            String risposta = "";
            String condizione = "";

            do{


                System.out.println( "Inserisci la parola: " );
                String parola = input.nextLine();
                
                out.writeBytes(parola + '\n');
                System.out.println("");

                risposta = in.readLine();

                if(risposta.contains("X")) {

                    condizione = "X";
                    System.out.println( "Disconnessione" );

                }

                else if(risposta.contains("W") || risposta.contains("L")) {


                    if(risposta.contains("W")) {
                        System.out.println( "Parola indovinata" );
                        condizione = "X";
                    }

                    if(risposta.contains("L"))
                        System.out.println( "Parola sbagliata, riporva" );

                }

                else if(risposta.length() > 1){

                    System.out.println(risposta);
                    System.out.println("");
                }

            }while(condizione != "X");

            risposta = in.readLine();
            System.out.println( "Tentativi: " + risposta);

            s.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }

    }
}

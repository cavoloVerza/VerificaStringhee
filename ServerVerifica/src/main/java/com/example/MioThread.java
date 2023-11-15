package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class MioThread extends Thread {

    Socket s;

    public MioThread(Socket s) {

        this.s = s;
    }

    public void run() {

        ArrayList <String> listaParole = new ArrayList();
        listaParole.add("lasagna");
        listaParole.add("computer");
        listaParole.add("server");
        listaParole.add("client");
        listaParole.add("monitor");
        listaParole.add("mouse");
        listaParole.add("tastiera");
        char[] charGiusti = {};

        try {

            System.out.println("un client si è collegato");
            System.out.println("");

            String parola = "";
            int randomNum = (int)(Math.random() * 8);
            for(int i = 0; i < listaParole.size(); i++) {

                if(i == randomNum)
                    parola = listaParole.get(i);
            }

            System.out.println("Parola da indovinare: " + parola);

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String parolaRicevuta = "";
            int count = 0;

            do{

                parolaRicevuta = in.readLine();
                count++;
                System.out.println("Parola ricevuta: " + parolaRicevuta);
                System.out.println("Contatore: " + count);
                System.out.println("");

                if(parolaRicevuta.contains("esci")) {

                    System.out.println("Giocatore disconnesso");
                    out.writeBytes( "X" + '\n');
                    break;
                }

                else if(parolaRicevuta.length() == 1) {

                    System.out.println("Inviato carattere");

                    for(int i = 0; i < listaParole.size(); i++) {

                        if(parola.charAt(i) == parolaRicevuta.charAt(0)) {

                            charGiusti = Arrays.copyOf(charGiusti, charGiusti.length + 1); 
                            charGiusti[charGiusti.length - 1] = parolaRicevuta.charAt(0);
                            System.out.println("carattere aggiunto");
                            break;
                        }
                    }

                    String asterischi = "";
                    boolean flag;
                    for(int i = 0; i < parola.length(); i++) {

                        if(charGiusti.length != 0) {

                            for(int j = 0; j < charGiusti.length; j++) {

                                flag = false;
                                if(parola.charAt(i) == charGiusti[j]) {

                                    asterischi = asterischi + parola.charAt(i);
                                    flag = true;
                                }

                                if(flag == false)
                                    asterischi = asterischi + "*";

                            }
                        }

                        else
                            asterischi = asterischi + "*";

                    }
                    System.out.println("Asterischi : " + asterischi);
                    out.writeBytes( asterischi + '\n');

                }

                else if(parolaRicevuta.length() > 1) {

                    System.out.println("inviato stringa");

                    if(parolaRicevuta.contains(parola)) {

                        System.out.println("Parola indovinata");
                        out.writeBytes( "W"+ '\n');
                        out.writeBytes( Integer.toString(count)+ '\n');
                    }

                    else {

                        System.out.println("Parola non indovinata");
                        out.writeBytes( "L"+ '\n');
                    }

                }


            }while(true);

            s.close();
            
        }

        catch(Exception e) {

            System.out.println(e.getMessage());
            System.out.println( "Errore durante l'istanza del Server!" );
        }
    }
    
}
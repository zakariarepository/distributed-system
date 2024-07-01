package org.example;
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String argv[]) {
        int port=0 ,port1=0 ,port2 =0;
        int response=0 , response1=0 , response2=0;
        try {
            port=8081;
            port1=8082;
            port2=8083;
            //port = Integer.parseInt(argv[0]);
        } catch (Exception e) {
            System.err.println("L'argument doit etre un nombre (port)");
            System.exit(-1);
        }
        //To call the workers
        String adresse1 = "172.17.0.2";
        String adresse2 = "172.17.0.3";

        try {
            // serveur positionne sa socket d'écoute sur le port local
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Serveur démarré sur le port " + port);


            // Boucle pour accepter les connexions des clients en continu
            while (true) {
                // se met en attente de connexion
                // de la part d'un client distant
                System.out.println("En attente d'une connexion...");
                Socket socket = serverSocket.accept();
                System.out.println("Connexion acceptée : " + socket.getInetAddress() + ":" + socket.getPort());


                // connexion acceptée : récupère les flux objets pour communiquer
                // avec le client qui vient de se connecter
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());



                // attente les données venant du client (tâche à effectuer)
                Integer firstValue = (Integer) input.readObject();
                Integer secondValue = (Integer) input.readObject();
                System.out.println("Tâche reçue : " + firstValue +" "+secondValue);

                // dispatcher le travaille entre les noeuds(workers)
                try {
                    // adresse IP du serveur
                    InetAddress adr1 = InetAddress.getByName(adresse1);
                    InetAddress adr2 = InetAddress.getByName(adresse2);

                    // ouverture de connexion avec le serveur sur le port spécifié
                    Socket socket2 = new Socket(adr1, port1);
                    Socket socket3 = new Socket(adr2, port2);

                    System.out.println(12);
                    // construction de flux objets à partir des flux de la socket
                    ObjectOutputStream output2 = new ObjectOutputStream(socket2.getOutputStream());
                    ObjectOutputStream output3 = new ObjectOutputStream(socket3.getOutputStream());
                    ObjectInputStream input2 = new ObjectInputStream(socket2.getInputStream());
                    ObjectInputStream input3 = new ObjectInputStream(socket3.getInputStream());

                    // envoi d'une tâche au workers
                    output2.writeObject(firstValue);
                    output2.writeObject((firstValue+secondValue)/2);
                    output3.writeObject((firstValue+secondValue)/2+1);
                    output3.writeObject(secondValue);
                    System.out.println("Tâche envoyée au workers");

                    // attente de réception du résultat de la tâche de la part du worker
                    response1 = (Integer) input2.readObject();
                    System.out.println("resultat recu du worker 1 est :"+response1);
                    response2 = (Integer) input3.readObject();
                    System.out.println("resultat recu du worker 2 est :"+response2);
                    response = response1*response2;
                    System.out.println("Résultat reçu du worker : " + response);

                    // Ferme les flux et la socket
                    output2.close();
                    input2.close();
                    socket2.close();
                    output3.close();
                    input3.close();
                    socket3.close();
                } catch (Exception e) {
                    System.err.println(e);
                }

                // Récupère le résultat de l'exécution de la tâche et l'envoie au client
                output.writeObject(response);
                System.out.println("Résultat envoyé au client : " + response );

                // Ferme les flux et la socket pour l'actuelle connexion client
                output.close();
                input.close();
                socket.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
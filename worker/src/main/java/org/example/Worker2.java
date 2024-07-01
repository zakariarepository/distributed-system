package org.example;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker2 {
    public static void main(String argv[]) {
        int port=0 , response=1;
        try {
            port=8083;
            //port = Integer.parseInt(argv[0]);
        } catch (Exception e) {
            System.err.println("L'argument doit etre un nombre (port)");
            System.exit(-1);
        }

        try {
            // serveur positionne sa socket d'écoute sur le port local
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("worker démarré sur le port " + port);


            // Boucle pour accepter les connexions des clients en continu
            while (true) {
                response=1;
                // se met en attente de connexion
                // de la part d'un client distant
                System.out.println("En attente d'une connexion...");
                Socket socket = serverSocket.accept();
                System.out.println("Connexion acceptée test: " + socket.getInetAddress() + ":" + socket.getPort());

                // connexion acceptée : récupère les flux objets pour communiquer
                // avec le client qui vient de se connecter
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                // attente les données venant du client (tâche à effectuer)
                Integer firstValue = (Integer) input.readObject();
                System.out.println("first value "+firstValue);
                Integer secondValue = (Integer) input.readObject();
                System.out.println("second value "+secondValue);

                //execution de la tache
                for(int i=firstValue;i<=secondValue;i++){
                    response*=i;
                }
                System.out.println("Tâche reçue : " + response);

                // Récupère le résultat de l'exécution de la tâche et l'envoie au client
                output.writeObject(response);
                System.out.println("Résultat envoyé au serveur : " + response);

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
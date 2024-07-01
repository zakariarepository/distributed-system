package com.example.systemdstribue;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

@RestController
@CrossOrigin("*")
public class Controller {
    @GetMapping(path = "api")
    public Integer test(@RequestParam Integer inputNum){
        String adresse = "10.72.175.172";
        int port = 0;
        Integer response=0;
        try {
            //port = Integer.parseInt(argv[1]);
            port=8081;
        } catch (Exception e) {
            System.err.println("Le 2eme argument doit etre un nombre (port)");
            System.exit(-1);
        }

        try {
            // adresse IP du serveur
            InetAddress adr = InetAddress.getByName(adresse);

            // ouverture de connexion avec le serveur sur le port spécifié
            Socket socket = new Socket(adr, port);

            // construction de flux objets à partir des flux de la socket
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            // envoi d'une tâche au serveur
            output.writeObject(1);
            output.writeObject(inputNum);
            System.out.println("Tâche envoyée au serveur :"+inputNum);

            // attente de réception du résultat de la tâche (la longueur de la chaîne)
            response = (Integer) input.readObject();
            System.out.println("Résultat reçu du serveur : " + response);


            // Ferme les flux et la socket
            output.close();
            input.close();
            socket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return response;
    }
}

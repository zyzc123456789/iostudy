package zy.study.bio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class BioDoubleServer {




    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(9080);
            while (true){
                Socket client = server.accept();
                clientSocket(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){

        }

    }

    private static void clientSocket(Socket clinet)
    {
        while(true){
            try {
                PrintWriter pw = new PrintWriter(clinet.getOutputStream());
                pw.write(UUID.randomUUID().toString());
                pw.write(System.lineSeparator());
                pw.flush();
                clinet.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

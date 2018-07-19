package zy.study.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

/**
 * <p>
 * 功能概述:
 * </p>
 * <p>
 * 功能详述:
 * </p>
 */
public class BioClient {
    /*========================================================================*
     *                         Public Fields (公共属性)
     *========================================================================*/

    /*========================================================================*
     *                         Private Fields (私有属性)
     *========================================================================*/

    /*========================================================================*
     *                         Construct Methods (构造方法)
     *========================================================================*/

    /*========================================================================*
     *                         Public Methods (公有方法)
     *========================================================================*/

    public static void main(String[] args) {
        BioClient bioClient = new BioClient();
        bioClient.sendClient();
    }

    public Socket createClient()  {
        Socket client = null;
        try
        {

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return client;
    }


    public void sendClient(){

        try{
            Socket client = new Socket("localhost",9090);
            PrintWriter pw = new PrintWriter(client.getOutputStream());


            pw.write(UUID.randomUUID().toString());
            pw.flush();
            Thread.sleep(1000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println(reader.readLine());


            client.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void read(){

    }


    /*========================================================================*
     *                         Private Methods (私有方法)
     *========================================================================*/
}

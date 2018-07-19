package zy.study.bio;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class BioDoubleClient {

    public static void main(String[] args) throws InterruptedException {
        Thread th = new Thread(()->{
            try {
                Socket client = new Socket("localhost",9080);
                byte[] bytes = new byte[1024];
                //client.getInputStream().read(bytes);
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                new Thread(()->{
                    while(true){

                        String line = null;
                        try {
                            line = bufReader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(line!=null){
                            System.out.println("Server Send:" + line);
                        }
                    }
                }).start();

            } catch (IOException e) {

            }
        });
        th.start();
        th.join();

    }
}

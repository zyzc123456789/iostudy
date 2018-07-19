package zy.study.bio;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 功能概述:
 * </p>
 * <p>
 * 功能详述:
 * </p>
 */
public class BioDouClient
{
    /*========================================================================*
     *                         Public Fields (公共属性)
     *========================================================================*/

    /*========================================================================*
     *                         Private Fields (私有属性)
     *========================================================================*/

    private static final AtomicInteger atcInteger = new AtomicInteger();

    private static volatile int close = 0;

    /*========================================================================*
     *                         Construct Methods (构造方法)
     *========================================================================*/

    /*========================================================================*
     *                         Public Methods (公有方法)
     *========================================================================*/

    public static void main(String[] args) throws IOException, InterruptedException {
        int cnt = 0;
//        while(cnt <= 1){
//
//            cnt++;
//        }
        while(cnt <= 1) {
            cnt++;
            new Thread(()->{
                try {
                    Socket sck = new Socket("localhost",9080);
                    PrintWriter writer = new PrintWriter(sck.getOutputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(sck.getInputStream()));
                    new Thread(()->{
                        while (true){
                            try{
                                String resp = reader.readLine();
                                System.out.println("非阻塞");
                                if(resp != null){
                                    System.out.println("Response Msg:" + resp);
                                }
                            }catch (Exception ex){
                                System.out.println("读线程:" + ex.getMessage());
                            }
                        }
                    }).start();
                    while (true){
                        writer.write(String.valueOf(System.currentTimeMillis()) + "_" + atcInteger.incrementAndGet());
                        writer.flush();
                        Thread.sleep(5*1000);
                        if(close == 1){
                            break;
                        }
                    }
                    writer.close();
                    reader.close();
                    sck.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }).start();
        }
        while(true){

        }

    }



    /*========================================================================*
     *                         Private Methods (私有方法)
     *========================================================================*/


}

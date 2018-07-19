package zy.study.bio;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * <p>
 * 功能概述:
 * </p>
 * <p>
 * 功能详述:
 * </p>
 */
public class BioDouServer {
    /*========================================================================*
     *                         Public Fields (公共属性)
     *========================================================================*/

    /*========================================================================*
     *                         Private Fields (私有属性)
     *========================================================================*/

    private ServerSocket server  = null;

    private HashMap<String,ServerThread> caches = new HashMap<>();

    private static final BlockingDeque<String> words = new LinkedBlockingDeque<>();

    /*========================================================================*
     *                         Construct Methods (构造方法)
     *========================================================================*/

    /*========================================================================*
     *                         Public Methods (公有方法)
     *========================================================================*/

    public static void main(String[] args) {
        BioDouServer server = new BioDouServer();
        server.listen();
    }

    public void listen()
    {
        try
        {
            new WriterThread().start();
            server = new ServerSocket(9080);
            int count =0;
            while(true)
            {
                count++;
                Socket socket = server.accept();
                String threadNm = String.valueOf(count);
                ServerThread th = new ServerThread(threadNm,socket);
                caches.put(threadNm,th);
                th.start();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void close() {
        sendAll("exit");
        if(server != null){
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String id, String msg)
    {
        caches.get(id).send(msg);
    }

    public void sendAll(String msg)
    {
        caches.entrySet().forEach(x->{
            send(x.getValue().getThName(),msg);
        });
    }

    /*========================================================================*
     *                         Private Methods (私有方法)
     *========================================================================*/

    private void saveToCache(Socket socket)
    {

    }

    class WriterThread extends  Thread{

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        @Override
        public void run()
        {
            while(true)
            {
                try {
                    String readLine = reader.readLine();

                    if(readLine.equals("exit")){
                        break;
                    }

                    String[] contents = readLine.split(":");
                    String user = contents[0];
                    String msg = contents[1];
                    System.out.println("User:" + user + ",Msg:" + msg);
                    if(user.equals("*")){
                        sendAll(msg);
                    }else{
                        send(user,msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("服务器端退出");
            close();
        }
    }


    class ServerThread extends Thread{

        private String thName;
        private OutputStream out;
        private InputStream in;
        private PrintWriter writer;
        private Socket socket;

        ServerThread(String thName, Socket socket) throws IOException {
            this.thName = thName;
            this.socket = socket;
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
            this.writer = new PrintWriter(this.out);
        }

        public String getThName() {
            return thName;
        }

        public void setThName(String thName) {
            this.thName = thName;
        }

        public OutputStream getOut() {
            return out;
        }

        public void setOut(OutputStream out) {
            this.out = out;
        }

        public InputStream getIn() {
            return in;
        }

        public void setIn(InputStream in) {
            this.in = in;
        }

        public PrintWriter getWriter() {
            return writer;
        }

        public void setWriter(PrintWriter writer) {
            this.writer = writer;
        }

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            this.socket = socket;
        }

        public void send(String str)
        {
            if(!this.socket.isClosed() && str!=null && !"exit".equals(str)){
                try {
                    this.writer.write(str);
                    this.writer.write(System.lineSeparator());
                    this.out.flush();
                    this.writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        public void run()
        {
            new Reader().start();
        }


        public void close(){
            try
            {
                if(this.out!=null){
                    this.out.close();
                }
                if(this.in!=null){
                    this.in.close();
                }
                if(this.writer!=null){
                    this.writer.close();
                }
                if(this.socket!=null){
                    this.socket.close();
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }

        class Reader extends Thread{

            private InputStreamReader insr = new InputStreamReader(in);
            private BufferedReader reader = new BufferedReader(insr);

            @Override
            public void run()
            {
                String line = "";
                try
                {
                    while(!socket.isClosed() && line!=null && !"exit".equals(line))
                    {
                        char[] contents = new char[1000];
                        int len = reader.read(contents,0,1000);
//                        if(len > 0)
//                            System.out.println("客户ID" + thName + "," + String.valueOf(contents));
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }

        }
    }



}

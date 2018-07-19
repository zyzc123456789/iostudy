package zy.study.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 * 功能概述:
 * </p>
 * <p>
 * 功能详述:
 * </p>
 */
public class BioServer {
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
        BioServer server = new BioServer();
        try {
            server.listen(server.createServer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 创建server
    public ServerSocket createServer()
    {

        ServerSocket server = null;
      try
      {
          server = new ServerSocket(9090);
      }
      catch (Exception ex){
          ex.printStackTrace();
      }
      return server;

    }

    private void listen(ServerSocket server) throws IOException {
        while(true)
        {
            Socket client = server.accept();
            InputStream iptStream =  client.getInputStream();

            byte[] content = new byte[1024];
            int len = iptStream.read(content);

            if(len>0){
                System.out.println(String.valueOf(content));
            }

            if(!client.isClosed()){
                PrintWriter pw = new PrintWriter(client.getOutputStream());
                pw.write("shoudao");
                pw.flush();
                pw.close();
            }
        }
    }

    /*========================================================================*
     *                         Private Methods (私有方法)
     *========================================================================*/
}

package zy.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer
{

    private static final int PORT = 8090;
    private InetSocketAddress address = null;
    private Selector selector;

    public void startServer(){
        try {
            address = new InetSocketAddress(PORT);
            // 打开通道
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(address);

            // 设置通道非阻塞
            server.configureBlocking(false);

            selector = Selector.open();

            server.register(selector,SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen()
    {
        while(true){

            try {
                int wait = selector.select();
                if(wait == 0){ continue;}
                Set<SelectionKey> selectkeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectkeys.iterator();
                while(it.hasNext()){
                    SelectionKey key = it.next();
                    //
                    process(key);
                    it.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void process(SelectionKey key)
    {

    }

    public static void main(String[] args) {

    }

}

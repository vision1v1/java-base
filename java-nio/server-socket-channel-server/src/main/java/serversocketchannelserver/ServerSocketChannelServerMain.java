package serversocketchannelserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class ServerSocketChannelServerMain {
    public static void main(String[] args){
        //test01();
        test02();
        System.out.println("End...");
    }

    //测试连接
    private static void test01() {
        try {
            System.out.println("Listening...");
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(9999));
            serverSocketChannel.configureBlocking(false);//非堵塞模式

            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    System.out.println(socketChannel.toString());
                } else {
                    //System.out.println("............");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    private static void test02() {
        try {
            System.out.println("Listening...");
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(9999));
            serverSocketChannel.configureBlocking(false);//非堵塞模式
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if(socketChannel != null){
                    startInComing(socketChannel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void startInComing(SocketChannel socketChannel) {
        Thread thread = new Thread(new InComing(socketChannel));
        thread.setDaemon(true);
        thread.setName("InComing");
        thread.start();
    }
}

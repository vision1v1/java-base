package serversocketchannelselectorserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class ServerSocketChannelSelectorServerMain {
    public static void main(String[] args){
        test01();
        System.out.println("End");
    }

    @SuppressWarnings("Duplicates")
    public static void test01(){
        try {
            System.out.println("Listening...");
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(9999));
            serverSocketChannel.configureBlocking(false);//非堵塞模式

            Selector selector = Selector.open();
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            
            while (true) {
                int readyCount = selector.selectNow();
                if (readyCount > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    for (SelectionKey key : selectionKeys) {
                        //这里处理接收新的连接
                        if (key.isAcceptable()) {
                            ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = serverSocketChannel1.accept();
                            if(socketChannel!=null){
                                socketChannel.configureBlocking(false);
                                socketChannel.register(selector, SelectionKey.OP_READ);
                                //这里不需要在分配一个线程工作了，selector 目的就是在一个线程内管理多个channel。
                                //如果启动线程，会出现selector 在不同线程处理bug。
                                //startInComing(selector);
                            }
                        }
                        //这里处理数据的读入
                        if(key.isReadable()){
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(64);
                            int byteCount = socketChannel.read(byteBuffer);
                            while (byteCount != -1){
                                byteBuffer.flip();
                                while(byteBuffer.hasRemaining()){
                                    System.out.print((char)byteBuffer.get());
                                }
                                byteBuffer.clear();
                                byteCount = socketChannel.read(byteBuffer);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

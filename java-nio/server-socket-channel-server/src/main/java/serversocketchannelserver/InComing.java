package serversocketchannelserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class InComing implements Runnable {

    private SocketChannel socketChannel;

    public InComing(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Running...");
        while (socketChannel != null && socketChannel.isConnected()){
            try{
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
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

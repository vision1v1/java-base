package socketchannelclient;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class SocketChannelClientMain {
    public static void main(String[] args){
        //test01();

        test02();
        test02();
        System.out.println("End");
    }

    //测试连接服务端
    private static void test01(){
        try{
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1",9999));
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    //测试读取本地文件，将内容发送的服务端
    @SuppressWarnings("Duplicates")
    private static void test02(){
        SocketChannel socketChannel = null;
        RandomAccessFile fromFile = null;
        try{
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1",9999));
            if(socketChannel.isConnected()){
                fromFile = new RandomAccessFile("socket-channel-client/src/main/java/socketchannelclient/SocketChannelClientMain.java", "rw");
                FileChannel fromFileChannel = fromFile.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                int byteCount = fromFileChannel.read(byteBuffer);
                while (byteCount!=-1){
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()){
                        socketChannel.write(byteBuffer);
                    }
                    byteBuffer.clear();
                    byteCount = fromFileChannel.read(byteBuffer);
                }
                socketChannel.close();
                fromFile.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                if(socketChannel != null){
                    socketChannel.close();
                }
                if(fromFile != null){
                    fromFile.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}

package channelbufferusage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelBufferUsageMain {
    public static void main(String[] args){

        test01();

        System.out.println("End");
    }

    public static void test01(){

        RandomAccessFile fromFile = null;
        RandomAccessFile toFile = null;
        try {
            String dir = System.getProperty("user.dir");
            System.out.println(dir);
            fromFile = new RandomAccessFile("channel-buffer-usage/src/main/java/channelbufferusage/ChannelBufferUsageMain.java", "rw");
            toFile = new RandomAccessFile("channel-buffer-usage/src/main/resources/ChannelBufferUsageMain.txt","rw");
            FileChannel fromFileChannel = fromFile.getChannel();
            FileChannel toFileChannel = toFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(48);
            int readBytes = fromFileChannel.read(byteBuffer);
            while (readBytes != -1) {
                System.out.println("readBytes : " + readBytes);
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    toFileChannel.write(byteBuffer);
                }
                byteBuffer.rewind();
                while (byteBuffer.hasRemaining()){
                    System.out.print((char) byteBuffer.get());
                }
                byteBuffer.clear();
                readBytes = fromFileChannel.read(byteBuffer);
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(fromFile!=null){
                try{
                    fromFile.close();
                    toFile.close();
                }
                catch (IOException e){
                }
            }
        }


    }
}

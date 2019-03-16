package com.hxb.smart;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-15 15:27:31
 */
public class NIOFile {
    public static void main(String[] args) {

    }

    public static void fileTest(){
        try (
                RandomAccessFile file = new RandomAccessFile("D:/data.txt", "r")
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(64);
            FileChannel channel = file.getChannel();
            int len = channel.read(buffer);
            while (len != -1){
                buffer.flip();
                while (buffer.hasRemaining()){
                    System.out.print((char)buffer.get());
                }
                buffer.clear();
                len = channel.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

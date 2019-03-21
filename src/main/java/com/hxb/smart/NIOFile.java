package com.hxb.smart;

import java.io.ByteArrayInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-15 15:27:31
 */
public class NIOFile {
    public static void main(String[] args) {
        fileTest();
    }

    public static void fileTest(){
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5,8,3,TimeUnit.SECONDS,new LinkedBlockingQueue<>(16));


            for(int i=0;i<5;i++){
                pool.execute(()->{
                    int d = (int) (Thread.currentThread().getId() % 5);
                            try (
                                    RandomAccessFile file = new RandomAccessFile("D:/files/goodsMongo1.json", "r")
                            ) {
                                long length = file.length();
                                System.out.println("共"+length);
                                long segment = length / 5;
                                file.skipBytes((int) (d * segment));
                                byte[] b = new byte[1024];
                                int count = 0;
                                int len = 0;
                                while ((len = file.read(b)) != -1){
                                    count +=len;
                                    if(segment - count <= 0){
                                        break;
                                    }
                                }
                                System.out.println("线程"+d+"读取"+count+"字节");
                            }catch (Exception e) {
                                e.printStackTrace();
                            }

                });
            }
        System.out.println();
    }
}

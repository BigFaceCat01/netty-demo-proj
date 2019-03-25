package com.hxb.smart.http;

import com.hxb.smart.constant.Constant;
import com.hxb.smart.file.FileServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 17:41:25
 */
public class ShellServer {
    public static void main(String[] args) {
        try {
//            new ShellServer().launch();
            ProcessBuilder p = new ProcessBuilder("cmd");
            Process exec = p.start();

            PrintWriter out = new PrintWriter(exec.getOutputStream());
            String cmd ; //你的cmd命令
            out.println("cd .. \r\n"); //输入你的命令
            out.flush(); //写到控制台

            PrintStream printStream = new PrintStream(System.out);
            new Thread(()->{

                boolean b = false;
                try (InputStream inputStream = exec.getInputStream();) {
                        for(;;){
                            int i =inputStream.read();
                            if(i != -1) {
                                printStream.print((char)i);
                            }else {
                                if(b){
                                    break;
                                }
                                b = true;
                                out.println("dir \r\n"); //输入你的命令
                                out.flush(); //写到控制台
                            }

                    }
                }catch (Exception e){
                    e.printStackTrace();

                }
            }).start();
            new Thread(()->{
                Scanner scanner = new Scanner(System.in);
                while (true){
                    String c = scanner.nextLine();
                    if("quit".equalsIgnoreCase(c)){
                        break;
                    }
                    out.println(c+"\r\n"); //输入你的命令
                    out.flush(); //写到控制台
                }
            }).start();
            Thread.sleep(500);
            exec.destroy();

//            OutputStream outputStream = exec.getOutputStream();
//            byte[] b = "cd ..".getBytes();
//            outputStream.write(b,0,b.length);
//            outputStream.flush();
//            while ((line = bufferedReader.readLine()) != null){
//                printStream.println(line);
//            }
//            exec.destroy();
//            exec.getInputStream().close();
//            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void launch() throws Exception{
        EventLoopGroup workerGroup =new NioEventLoopGroup();
        EventLoopGroup bossGroup =new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(workerGroup,bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(
                                    new StringEncoder(),
                                    new StringDecoder(CharsetUtil.UTF_8),
                                    new ShellServerHandler()
                            );
                        }
                    });
            serverBootstrap.bind(Constant.LOCAL_HOST,Constant.PORT)
                    .channel()
                    .closeFuture()
                    .sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

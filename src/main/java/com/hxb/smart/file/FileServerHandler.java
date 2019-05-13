package com.hxb.smart.file;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.stream.ChunkedFile;

import java.io.RandomAccessFile;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 16:37:08
 */
public class FileServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
        if(!msg.contains("path")){
            return;
        }
        String path="";
        String[] s = msg.split("\\s");
        for (String s1 : s) {
            if(s1.contains("path")){
                path = s1.substring(7);
            }
        }
        System.out.println(path);
        long len ;
        try(RandomAccessFile raf = new RandomAccessFile(path,"r")){
            len = raf.length();
            ctx.write("OK: "+len + '\n');
            ctx.write(new ChunkedFile(raf));
            ctx.writeAndFlush("\n");
        }catch (Exception e){
            ctx.writeAndFlush("ERR: " + e.getClass().getSimpleName() + ": " + e.getMessage() + '\n');
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }
}

package com.hxb.smart.heart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-15 16:19:09
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private Bootstrap client;
    public ClientHandler(Bootstrap client){
        this.client = client;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("hello");
    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(">>>>>>cause:{}",cause.getMessage());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error(">>>>>>inactive:inactive");
        Channel channel = ctx.channel();
        SocketAddress socketAddress = channel.remoteAddress();
        log.info("{}",channel);
        log.info("{}",socketAddress);
        while (true) {
            try {
                log.info("尝试重新连接服务器{}",socketAddress);
                client.connect(socketAddress).sync();
            }catch (Exception e){
                log.error(e.getMessage());
                Thread.sleep(2000);
                continue;
            }
            log.info("重新连接服务器{}成功",socketAddress);
            break;
        }
    }
}

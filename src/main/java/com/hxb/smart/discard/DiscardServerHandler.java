package com.hxb.smart.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-19 17:36:04
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read");
        ByteBuf ms = (ByteBuf) msg;
        ByteBuf back = ms.alloc().buffer(4);
        back.writeInt(100);
        ByteBuf byteBuf = back.asReadOnly();
        System.out.println(byteBuf.isReadOnly());
        ChannelFuture future = ctx.writeAndFlush(byteBuf);
        future.addListener(ChannelFutureListener.CLOSE);
//        ms.release();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

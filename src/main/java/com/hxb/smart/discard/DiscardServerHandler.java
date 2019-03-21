package com.hxb.smart.discard;

import io.netty.buffer.ByteBuf;
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
        ByteBuf m = (ByteBuf) msg;
        System.out.println(m.toString(CharsetUtil.UTF_8));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = ctx.alloc().directBuffer(12);
        buf.writeBytes("server:welcome".getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(buf).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}

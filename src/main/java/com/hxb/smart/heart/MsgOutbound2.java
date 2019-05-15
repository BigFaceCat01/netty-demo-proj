package com.hxb.smart.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-15 16:00:08
 */
@Slf4j
public class MsgOutbound2 extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("MsgOutbound2>>>{}",msg);
        ctx.write(msg,promise);
    }

}

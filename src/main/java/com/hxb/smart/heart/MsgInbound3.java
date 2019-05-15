package com.hxb.smart.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-15 16:00:08
 */
@Slf4j
public class MsgInbound3 extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("MsgInbound3>>>{}",msg);
        ctx.writeAndFlush(msg);
    }
}

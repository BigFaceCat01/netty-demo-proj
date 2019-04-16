package com.hxb.smart.factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 15:19:52
 */
public class NumberArrayDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes()<4){
            return;
        }
        in.markReaderIndex();
        int len = in.readInt();
        if(in.readableBytes() < len  * 4){
            in.resetReaderIndex();
            return;
        }
        List<Integer> ns = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            ns.add(in.readInt());
        }
        out.add(ns);
    }
}

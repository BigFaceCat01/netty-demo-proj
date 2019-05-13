package com.hxb.smart.factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;
import java.util.Objects;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 15:14:29
 */
public class NumberArrayEncode extends MessageToByteEncoder<List<Integer>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, List<Integer> msg, ByteBuf out) throws Exception {
        //若是空列表
        if(Objects.isNull(msg) || msg.isEmpty()){
            out.writeInt(0);
            return;
        }
        out.writeInt(msg.size());
        for (Integer n : msg) {
            out.writeInt(n);
        }
    }
}

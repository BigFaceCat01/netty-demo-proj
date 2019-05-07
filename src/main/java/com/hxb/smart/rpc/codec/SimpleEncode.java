package com.hxb.smart.rpc.codec;

import com.hxb.smart.rpc.serializer.AbstractSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-07 09:17:40
 */
public class SimpleEncode extends MessageToByteEncoder<Object> {

    private AbstractSerializer serializer;

    public SimpleEncode(AbstractSerializer.SerializeEnum serializerEnum){
        this.serializer = serializerEnum.getSerializer();
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] data = serializer.serialize(msg);
        out.writeInt(data.length);
        out.writeBytes(data);
        ctx.flush();
    }
}

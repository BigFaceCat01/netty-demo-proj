package com.hxb.smart.tomcat.decode;

import com.hxb.smart.tomcat.protocol.AbstractProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 11:09:43
 */
public class HttpProtocolDecode extends MessageToByteEncoder<AbstractProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractProtocol msg, ByteBuf out) throws Exception {

    }
}

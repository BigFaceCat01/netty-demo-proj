package com.hxb.smart.rpcv2.core.net.protocol;

import java.nio.ByteBuffer;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 11:24:47
 */
public abstract class Protocol {
    public abstract byte[] encode(Object obj);
    public abstract Object decode(byte[] bytes);

}
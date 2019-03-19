package com.hxb.smart;

import com.hxb.smart.discard.DiscardServer;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-18 20:38:10
 */
public class NettyDemo {
    public static void main(String[] args) throws Exception {
        new DiscardServer().launch();
    }
}

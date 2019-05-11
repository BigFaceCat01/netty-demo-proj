package com.hxb.smart.rpcv2.util;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 15:45:44
 */
public class IpUtil {
    public static Object[] parseIpPort(String address){
        String[] array = address.split(":");

        String host = array[0];
        int port = Integer.parseInt(array[1]);

        return new Object[]{host, port};
    }







    private IpUtil(){}
}

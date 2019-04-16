package com.hxb.smart.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 17:43:19
 */
public class ShellServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    String responseHeaderHtml = "HTTP/1.1 200 OK\n" +
            "Cache-Control: private\n" +
            "Connection: Keep-Alive\n" +
            "Content-Type: text/html;charset=utf-8\n" +
            "Date: Thu, 21 Mar 2019 09:54:01 GMT\n";

    String responseHeaderPlain = "HTTP/1.1 200 OK\n" +
            "Cache-Control: private\n" +
            "Connection: Keep-Alive\n" +
            "Content-Type: text/plain;charset=utf-8\n" +
            "Date: Thu, 21 Mar 2019 09:54:01 GMT\n";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String command="";
        if(msg.contains("HTTP/1.1")){
            String[] s = msg.split("\\s");
            for (String s1 : s) {
                if(s1.trim().equals("/")){
                    ctx.write(responseHeaderHtml);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
//                    HtmlBuilder.build(bos);
                    ctx.write("Content-Length: "+ bos.size());
                    ctx.write("\n\n");

                    ctx.writeAndFlush(new String(bos.toByteArray()));
                    return;
                }
                if(s1.contains("command")){
                    command = s1.substring(10);
                    break;
                }
            }
        }
        if(msg.startsWith("Referer")){
            return;
        }
        String decode = URLDecoder.decode(command, "utf-8");
        System.out.println("exec: " +decode);
        Process exec = Runtime.getRuntime().exec("cmd.exe /k "+command);

        StringBuilder stringBuilder = new StringBuilder(512);
        try(
                InputStream inputStream = exec.getInputStream();
                BufferedReader re = new BufferedReader(new InputStreamReader(inputStream,"gbk"))
        ){
            String line;
            while ((line = re.readLine()) != null){
                stringBuilder.append(line).append("\r\n");
            }
            System.out.println(stringBuilder.toString());
            byte[] bytes = stringBuilder.toString().getBytes();
            ctx.write(responseHeaderPlain);
            ctx.write("Content-Length: "+ bytes.length);
            ctx.write("\n\n");
            String sep = System.getProperty("line.separator");
            String body = new String(bytes,CharsetUtil.UTF_8);
            ctx.write(body);
            ctx.flush();
        }catch (Exception e){
            ctx.write(responseHeaderPlain);
            ctx.write("\n\n");
            String err = "ERR: please check command "+ msg +" is right!!!";
            ctx.write("Content-Length: "+ err.length());
            ctx.write(err);
            ctx.writeAndFlush("\n");
        }
    }
}

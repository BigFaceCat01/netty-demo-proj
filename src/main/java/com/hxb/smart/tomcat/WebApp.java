package com.hxb.smart.tomcat;

import com.hxb.smart.tomcat.v1.SimpleServer;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 10:35:43
 */
public class WebApp {

    private WebApp(){}

    public static void main(String[] args) {
        String S = "";
        String[] words = S.split(" ");
        StringBuilder ap = new StringBuilder(words.length*2)
        for(int i=0;i<words.length;i++){
            switch((int)words[i].charAt(0)){
                case 97:
                case 101:
                case 105:
                case 111:
                case 117:
                case 65:
                case 69:
                case 73:
                case 79:
                case 85:
                    ap.append(words[i]).append("md");
                    for(int j=0;j<=i;j++){
                        ap.append("a");
                    }
                    ap.append(" ");
                    break;
                default:
                    ap.append(words[i]).append("md");
                    for(int j=0;j<=i;j++){
                        ap.append("a");
                    }
                    ap.append(" ");
                    break;
            }
        }
//        start(new SimpleServer());
        System.out.println((int)'a');
        System.out.println((int)'e');
        System.out.println((int)'i');
        System.out.println((int)'o');
        System.out.println((int)'u');
        System.out.println((int)'A');
        System.out.println((int)'E');
        System.out.println((int)'I');
        System.out.println((int)'O');
        System.out.println((int)'U');
    }
    private static List<String> ttt(){
        return null;
    }

    public static void start(AbstractServer server){
        try {
            server.launch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

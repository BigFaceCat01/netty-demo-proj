package com.hxb.smart.http.html;

import com.hxb.smart.tomcat.protocol.HttpResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-22 14:32:02
 */
public class HtmlBuilder {
    /**
     * //1.0 创建配置对象
     * 创建Configuration实例，指定FreeMarker的版本
     */
    private static Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

    private static boolean isBuild;


    public static void init() throws Exception{
        if(isBuild){
            return;
        }
        ClassLoader c = Thread.currentThread().getContextClassLoader();
        //指定模板所在的目录
        URL resource = c.getResource("template");
        String replace = resource.toString().replace(" ", "%20");
        File templatesDir = new File(new URI(replace).getSchemeSpecificPart());
        cfg.setDirectoryForTemplateLoading(templatesDir);
        //设置默认字符集
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.name());
        //设置出现异常处理的方式，开发阶段可以设置为HTML_DEBUG_HANDLER
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        isBuild = true;
    }

    public static void build(OutputStream is) throws Exception{
        init();

        //2.0 创建数据模型
        Map<String, Object> root = new HashMap<>();

        //3.0 获取模板
        Template template = cfg.getTemplate("index.ftl");

        //4.0 给模板绑定数据模型
        Writer out = new OutputStreamWriter(is);
        template.process(root, out);
    }

    public static void build(String resource, HttpResponse response) throws Exception{
        init();

        //2.0 创建数据模型
        Map<String, Object> root = new HashMap<>();

        //3.0 获取模板
        Template template = cfg.getTemplate(resource);

        //4.0 给模板绑定数据模型
        Writer out = new OutputStreamWriter(response.getOutputStream());
        template.process(root, out);
    }
}

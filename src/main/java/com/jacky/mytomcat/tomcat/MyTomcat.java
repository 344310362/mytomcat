package com.jacky.mytomcat.tomcat;

import com.jacky.mytomcat.servlet.Servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * 核心类
 * 如果在web.properties文件中没有找到客户端请求的url，则直接返回“Hello World”
*/
public class MyTomcat {
    public static void main(String[] args){
        MyTomcat tomcat = new MyTomcat();
        tomcat.init();
        tomcat.start();
    }
    //第一步： 定义变量，用于存储相关的内容
    private static final int port = 8099;
    private static final Properties properties = new Properties();
    public static final HashMap<String, Servlet> servletMapping = new HashMap<String, Servlet>();

    //第二步： 定义init方法，读取配置文件此方法重点是将web.properties中的内容读到取ServletMapping中
    private void init() {

        InputStream io = null;

        String basePath;

        try {
            //获取basePath
            basePath = MyTomcat.class.getResource("/").getPath();
            System.out.println(basePath);
            io = new FileInputStream(basePath + "web.properties");
            properties.load(io);
            io.close();

            //初始化ServletMapping
            //返回属性key的集合
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
                if (key.toString().contains("url")) {
                    System.out.println(key.toString() + "=" + properties.get(key));
                    //根据key值获取className
                    Object classname = properties.get(key.toString().replace("url", "classname"));

                    servletMapping.put(properties.get(key.toString()).toString(),
                            (Servlet) Class.forName(classname.toString()).newInstance());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (io != null) {
                try {
                    io.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    //第三步：启动服务，并监听
    private void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Tomcat 服务已启动，地址：localhost ,端口：" + port);
            //持续监听
            do {
                Socket socket = serverSocket.accept();
                System.out.println(socket);
                //处理任务
                Thread thread = new SocketProcess(socket);
                thread.start();
            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

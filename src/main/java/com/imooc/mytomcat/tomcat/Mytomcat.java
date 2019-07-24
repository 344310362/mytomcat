package com.imooc.mytomcat.tomcat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Mytomcat
 *
 * @author zhourj
 * @date 2019/2/27
 * description
 */
public class Mytomcat {

    public static void main(String[] args) {
        Mytomcat server = new Mytomcat();
        server.start();
    }

    private void start(){

        try {
            ServerSocket serverSocket = new ServerSocket(8090);

            do {
                // 监听
                Socket socket = serverSocket.accept();

                // 处理数据
                hander(socket);

            } while (true);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * http response
     *  第一行 协议 返回状态
     *  第二行 媒体类型 josn/html
     *  第三行 空
     *  内容
     * @param socket
     */
    private void hander(Socket socket) throws IOException {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("HTTP/1.1 200 \r\n")
                .append("Content-Type: text/html\r\n")
                .append("\r\n")
                // 内容部分
                .append("hello tomcat");

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(responseBuilder.toString().getBytes());
        outputStream.flush();
        outputStream.close();
    }
}

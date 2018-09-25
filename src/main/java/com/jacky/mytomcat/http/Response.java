package com.jacky.mytomcat.http;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 在此代码中，定义一个http协议头，有些浏览器，比如chrome，无法识别不带协议头的的报文
 */
public class Response {
    public OutputStream outputStream;

    public static final String responseHeader="HTTP/1.1 200 \r\n"
            + "Content-Type: text/html\r\n"
            + "\r\n";

    public Response(OutputStream outputStream) throws IOException {
        this.outputStream= outputStream;
    }


}

package com.jacky.mytomcat.servlet;

import com.jacky.mytomcat.http.Request;
import com.jacky.mytomcat.http.Response;

public abstract class Servlet {


    public void service(Request request, Response response) {

        //判断是调用doget 还是 dopost
        if ("get".equalsIgnoreCase(request.getMethod())) {
            this.doGet(request, response);
        } else {
            this.doPost(request, response);
        }

    }

    public abstract void doGet(Request request, Response response);

    public abstract void doPost(Request request, Response response);

}

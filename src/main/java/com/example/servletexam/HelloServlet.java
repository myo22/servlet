 package com.example.servletexam;

import java.io.*;
import java.util.Enumeration;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 요청정보
        // Get /hello-servlet
        // 헤더들
        // 빈줄

        // Iterator, Enumeration은 모든 자료를 꺼낼때 사용.
        //
        // Iterator는 디자인패턴의 이름.
        // Enumeration Iterator와 비슷
        Enumeration<String> headerNames =  request.getHeaderNames(); // 요청정보가 가지고 있는 모든 헤더 이름들을 리턴해준다.
        while (headerNames.hasMoreElements()) { // hasMoreElements : 꺼낼 것이 있느냐?
            String headerName = headerNames.nextElement(); // nextElement : 꺼내라.
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }

        // 브라우저에게 내가 지금 보내는 것이 html이라고 알려주는 것이다.
        response.setContentType("text/html");

        // 응답의 Body에 쓰기위한 PrintWriter를 받아온다.
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}
## 크롬 개발자 도구

- 네트워크 보기

---

![img.png](img.png)

---

## http://localhost:8080

- http://localhost:8080/

1) 브라우저는 서버에 접속
   2) 요청 정보를 보낸다
            
            Get/
            헤더들
            빈줄

           - GET : 요청메소드

---

3) Tomcat는 / 에 해당하는 요청은 index.jsp가 기본으로 설정되어 있어 읽어들여 응답

            200 OK
            응답헤더들
            빈줄
            index.jsp의 내용

---

## 서블릿이란?

 - HttpServlet을 상속받는 클래스를 말한다. <- Http요청을 처리해주는 클래스가 HttpServlet이다.
 - JSP도 특수한 형태의 서블릿이라고 말할 수 있다.

---

## 서블릿을 실행

http://localhost:8080/hello-servlet

@WebServlet(name = "helloServlet", value = "/hello-servlet")

---

![img_1.png](img_1.png)

---

## Tomcat은?

/hello-servlet 에 해당하는 서블릿을 찾아서 실행.

HelloServlet을 실행

 Get방식으로 요청했기 때문에 HelloServlet의 doGet을 Tomcat이 실행.

---

## IntelliJ로 프로젝트를 생성하면 자동으로 만들어주는 Servlet이 있다.

    package com.example.servletexam;

    import java.io.*;
    import javax.servlet.http.*;
    import javax.servlet.annotation.*;

    @WebServlet(name = "helloServlet", value = "/hello-servlet")
    public class HelloServlet extends HttpServlet {
      private String message;

      public void init() {
        message = "Hello World!";
      }

      public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
      }

      public void destroy() {
      }
    }

---

![img_2.png](img_2.png)

---

## HttpServlet의 동작 원리.

 - Tomcat이 요청을 받아서, 어떤 서블릿을 실행하는가?
 - Tomcat은 요청정보를 HttpServletRequest, 응답을 위해 HttpServletResponse인스턴스를 생성
 - Tomcat은 서블릿의 doGet메소드의 인자로 위의 객체를 전달하여 실행해달라고 한다.

 - Servlet 코드

         - 요청정보로부터 브라우저가 보내주는 값을 읽어들인다.
         - 원하는 코드를 작성
         - 그 결과를 응답에게 써준다. (PrintWriter)

---

## 스프링 웹 프로그래밍

- 가장 핵심이 되는 클래스
      DispatcherServlet

---

![img_3.png](img_3.png)

---

## Plus Form

- http://localhost:8080/
   - / ==> /index.jsp를 요청하는 것과 같다.
   - http://localhost:8080/index.jsp

![img_4.png](img_4.png)

---

## Plus Form
webapp/index.jsp  ---> webapp/form/index.jsp로 옮겼다.

http://localhost:8080/form/
http://localhost:8080/form/index.jsp


---

## Plus Form

 - submit을 하면
    - http://localhost:8080/form/plus?x=20&y=40
 - action="plus" 상대경로 plus
    - http://localhost:8080/form/ + plus
    - http://localhost:8080/plus <-- 원래 이게 호출되어야함.

            <form method="get" action="plus">
                x : <input type="text" name="x"><br>
                y : <input type="text" name="y"><br>
                <input type="submit" value="plus">
            </form>

get방식으로 action에 있는 곳으로 전달하라는 뜻

---

- submit을 하면
    - http://localhost:8080/form/plus?x=20&y=40
- action="/plus" 절대경로 plus
    - http://localhost:8080 + /plus
    - http://localhost:8080/plus <-- 원래 이게 호출되어야함.

            <form method="get" action="/plus">
                x : <input type="text" name="x"><br>
                y : <input type="text" name="y"><br>
                <input type="submit" value="plus">
            </form>

---



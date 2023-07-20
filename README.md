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

![img_5.png](img_5.png)

---

 ## 서블릿에서 서블릿 인스턴스를 생성하는 것은 누구일까?

    Book b1 = new Book();

    - Tomcat 이라는 WAS가 인스턴스를 생성
    - /hello 요청을 받으면, 해당 PATH를 처리하는 서블릿이 메모리에 있느냐?
        HelloServlet hello = new HelloServlet();
        hello.init();
    - 메모리에 있다면 
        hello.service(req, resp);
            hello.doGet(req, resp);

---

 ## Spring 프레임 워크에서 Bean?

 - Spring 컨테이너가 관리하는 객체(인스턴스)

---

- 즉 요약하자면 서블릿은 Tomcat이 관리하는 객체이고 Bean은 스프링이 관리하는 객체이다.
- 우리를 대신하여 인스턴스를 생성하고 라이프사이클을 관리해주는 것이다.

---

 ## Spring으로 웹어플리케이션을 만들기
 
war 파일 ----> Tomcat서버 배포 (servlet, jsp라이브러리)

    ------ WEB-INF --- classes 
                    --- 개발자가 만든 package, calss들(spring설정class)
                --- lib --- spring과 관련된 각종jar파일(외부 라이브러리 파일)
                --- web.xml -> java.config(버전이 올라감에따라)
                --- 각종 리소스, hello.png // 즉 포워딩하는 경로로는 접근할 수 있다.
    ------ 각종 이미지, jsp, logo.png

http://localhost:8080/logo.png 바로 접근이 가능하지만.

WEB-INF 폴더 밑에 있는 리소스는
http://localhost:8080/WEB-INF/hello.png 이거는 접근할 수 없다.

---

 ## 스프링 설정 파일을 작성한다.

- 스프링 설정을 읽어들이는 것은 ApplicationContext (스프링 컨테이너)
- Tomcat은 web.xml파일 이나 Servlet 관련 파일들을 이해하고 실행하기 때문이다.


    @Configuration // Spring Java Config를 알려주는것.
    @EnableWebMvc // <mvc:annotation-driven /> <- xml 태그에선 이렇게 쓰여진다.
    public class WebConfig implements WebMvcConfigurer {
    public WebConfig() {
        System.out.println("WebConfig가 실행됩니다.");
        }
    }

---

 ## Tomcat 설정 파일

 - web ---> Java Config
    - Tomcat이 읽어들이는 Java Config에서 스프링 설정을 읽어들이도록 한다.
    - ApplicationContext가 읽어들이도록 한다.
    - 스프링이 제공하는 WebApplicationInitializer를 구현한 클래스를 만들고 onStartup을 오버라이딩하면, Tomcat이 실행되면서 자동으로 실행한다.
    - Tomcat이 실행될때 자동으로 읽어들이는 파일들은 스프링 jar파일에서 제공한다.
        - 자동으로 제공하는 클래스가 WebApplicationInitializer를 구현하고 있는 클래스를 실행하게 된다.

---

 ## Spring MVC의 핵심 서블릿

 - DispatcherServlet

               /hello                   /hello경로가 붙어있는 서블릿
        브라우저 ---------> Tomcat -----> 서블릿 , 정적파일(img.png, html, ....)

               /hello                         / (단일진입점)
        브라우저 ---------> Tomcat -----> 서블릿(DispatcherServlet) ---> ApplicationContext ---> Bean( Controller("/hello") )

가장 중요한 차이는 서블릿은 Tomcat이 관리하는건데  Bean은 스프링 컨테이너가 관리하기 때문에 다양한 작업을 수행해 줄 수 있다.

---

# 이미지를 호출한다.

 http://localhost:8080 + Context Path(우린 /로 설정) + PATH (/img.png) ? 파라미터들

 내 컴퓨터의 8080 서버에 접속.

---

             /img.png                        / (단일진입점)
        브라우저 ---------> Tomcat -----> 서블릿(DispatcherServlet) ---> ApplicationContext ---> Bean( Controller("/img.png") )
                                                                                                  지금 이게 없으니까 404오류가 뜨는 것이다.
---

             /img.png                        / (단일진입점)
        브라우저 ---------> Tomcat -----> 서블릿(DispatcherServlet) ---> ApplicationContext ---> Bean( Controller("/img.png") )
                                                                  ---> Tomcat의 DefaultServlet ---> /webapp/                                 

하지만 Webconfig에서 configureDefaultServletHandling로 디폴트 서블릿을 추가해준다면 Controller("...")가 없으면 Tomcat의 DefaultServlet이 /img.png를 읽어들인다.

---




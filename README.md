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

 ## 이미지를 호출한다.

 http://localhost:8080 + Context Path(우린 /로 설정) + PATH (/img.png) ? 파라미터들

 내 컴퓨터의 8080 서버에 접속.

---

 ## 이미지와 JSP를 호출

 http://localhost:8080/i mg.png
 http://localhost:8080/hello.jsp

             /img.png                        / (단일진입점)

        브라우저 ---------> Tomcat -----> 서블릿(DispatcherServlet) ---> ApplicationContext ---> Bean( Controller("/img.png") )
                                                                                                  지금 이게 없으니까 404오류가 뜨는 것이다.

             /img.png                        / (단일진입점)

        브라우저 ---------> Tomcat -----> 서블릿(DispatcherServlet) ---> ApplicationContext ---> Bean( Controller("/...") )
                                                                 ----> Tomcat의 DefaultServlet ---> /webapp/img.png                                 

하지만 Webconfig에서 configureDefaultServletHandling로 디폴트 서블릿을 추가해준다면 Controller("...")가 없으면 Tomcat의 DefaultServlet이 /img.png를 읽어들인다.


             /hello.jsp                        / (단일진입점)

        브라우저 ---------> Tomcat -----> 서블릿(DispatcherServlet) ---> ApplicationContext ---> Bean( Controller("/...) )
                                                                 ----> Tomcat의 DefaultServlet ---> /hello.jsp           
---

 ## JSP의 단점

 - Java 코드가 실행된다. - 유지보수 X, 보안 X
 - JSP = HTML + CSS가 섞여있다. ( 퍼블리셔, 프론트개발자 ) <- 자바코드가 섞여있어서 이 사람들이 처리하기도 곤란하고 보안에도 취약
 - JSP는 최대한 Java코드를 줄인다.
 - 결과는 Servlet or Controller에서 만들고 JSP에게 결과를 전달한다.

---

 ## Spring MVC는 여러개의 ViewResolver를 가질 수 있다.

 - 스프링 컨테이너는 ViewResolver인터페이스를 구현하고 있는 Bean이 어떤 것들이 있는지 알 고 있다.
 - ViewResolver인터페이스를 구현하는 Bean을 가질 수 있다.
 - 이 사용자는 JSP를 사용하도록 설정하고 있다(Tomcat사용, build.gradle에서 JSP 설정). 그런데 Bean중에 InternalResourceViewResolver가 있다고?
 - 그러면 컨트롤러가 리턴하는 문자열을 InternalResourceViewResolver가 동작하도록 한다.
 - Servlet or Controller 로부터 포워딩 될 경우 사용

            /hello                        / (단일진입점)

        브라우저 ---------> Tomcat -----> 서블릿(DispatcherServlet) ---> ApplicationContext ---> Bean( Controller("/hello") ) 
                                                                            -- 컨트롤러가 값을 리턴 --> InternalResourceViewResolver가 동작하며
                                                                                                        /WEB-INF/view/hello.jsp 포워딩을 한다. 

---

 ## /WEB-INF 폴더는 외부에서 접근할 수 없다.

 - /WEB-INF는 서버 내부에서 포워딩 할 수 있다.

 /WEB-INF/view/hello.jsp
 
 http://localhost:8080/WEB-INF/view/hello.jsp -> 404오류 (접근불가)
 
---

 ## Controller 와 ViewResolver

 - Controller는 로직을 수행하여 결과를 만들어 낸다. ---> InternalResourceViewResolver ---> JSP는 결과를 출력만하도록 하자.
 - Controller는 로직을 수행하여 결과를 만들어 낸다. ------>    BeanNameViewResolver   ------> Excel파일을 다운로드 할 수 있다.

 다양한 ViewResolver가 있다. 종류마다 조건이 다양하다.
 
---

DBMS안에는 여러개의 Database가 있을 수 있다.
Database는 특정 사용자만 이용할 수 있다. 특정 사용자는 DBMS에서 관리되는 사용자를 말한다.
Database안에는 Table을 생성한다. 회원정보는 회원Table에 저장, 상품정보는 상품Table에 저장

---

1. 내 컴퓨터에 DBMS를 설치한다. (첫번째 시간을 보면 Docker Desktop을 이용해서 MySQL을 실행한다.)
2. Database를 생성
3. Database를 이용하는 사용자 생성, 권한 부여
4. Database에 여러 Table을 생성, 해당 Table에 값을 저장한다.

---

Database 프로그래밍

1. SQL - SQL성능이 안나오면 실행계획을 볼 줄 알아야 한다. (튜닝)
2. SQL을 이용해서 JDBC 프로그래밍 (데이터베이스 프로그래밍)
3. Spring JDBC
4. Spring Data JPA(ORM기술) - 직접 SQL을 사용하지 않아도 된다. (내부적으로 SQL을 생성하여 동작) - 어떤 SQL을 생성할 것인지를 예상할 수 있어야 한다.

---


브라우저 ----- /hello ------> Tomcat --/ (모든요청) --> DispatcherServlet ---> Controller("/hello") --> 결과 ---> JSP

Controller의 메소드
http요청, http응답 <- Controller가 하는 가장 중요한 일
------------------

하나의 컨트롤러는 여러개의 Service를 사용해서 어떤 결과를 만들어 낼 수 있다.

                Service
                비즈니스 로직을 수행
                - 이자계산, 어떤 특정 사용자가 주문한 상품의 전체 가격을 구한다.
                - 트랜잭션(Transaction)
                    - 여러개의 DAO or Repository를 하나의 논리적인 작업단위로 실행할 수 있어야 한다.

                                     DAO (Data Access Object), Repository - Data를 다루기 위한 객체
                                JDBC프로그래밍을 좀 더 간결하게 하는 방법 -> 여러가지 중 대표적인것: Spring JDBC, Spring Data JPA(ORM기술), MyBatis
                                데이터베이스 프로그래밍(JDBC 프로그래밍)  -- SQL ------>   DBMS (Database를 관리) - MySQL

- DTO (Data Transfer Object) - 레이어를 넘나드는 객체
- 브라우저 ----- /hello ------> Tomcat --/ (모든요청) --> DispatcherServlet ---> Controller("/hello") --> 결과 ---> JSP

Controller의 메소드
http요청, http응답 <- Controller가 하는 가장 중요한 일
------------------
하나의 컨트롤러는 여러개의 Service를 사용해서 어떤 결과를 만들어 낼 수 있다.

                Service
                비즈니스 로직을 수행
                - 이자계산, 어떤 특정 사용자가 주문한 상품의 전체 가격을 구한다.
                - 트랜잭션(Transaction)
                    - 여러개의 DAO or Repository를 하나의 논리적인 작업단위로 실행할 수 있어야 한다.

                                     DAO (Data Access Object), Repository - Data를 다루기 위한 객체
                                JDBC프로그래밍을 좀 더 간결하게 하는 방법 -> 여러가지 중 대표적인것: Spring JDBC, Spring Data JPA(ORM기술), MyBatis
                                데이터베이스 프로그래밍(JDBC 프로그래밍)  -- SQL ------>   DBMS (Database를 관리) - MySQL

- DTO (Data Transfer Object) - 레이어를 넘나드는 객체
  ![img_6.png](img_6.png)

DBMS안에는 여러개의 Database가 있을 수 있다.
Database는 특정 사용자만 이용할 수 있다. 특정 사용자는 DBMS에서 관리되는 사용자를 말한다.
Database안에는 Table을 생성한다. 회원정보는 회원Table에 저장, 상품정보는 상품Table에 저장

---

1. 내 컴퓨터에 DBMS를 설치한다. (첫번째 시간을 보면 Docker Desktop을 이용해서 MySQL을 실행한다.)
2. Database를 생성
3. Database를 이용하는 사용자 생성, 권한 부여
4. Database에 여러 Table을 생성, 해당 Table에 값을 저장한다.

---

Database 프로그래밍

1. SQL - SQL성능이 안나오면 실행계획을 볼 줄 알아야 한다. (튜닝)
2. SQL을 이용해서 JDBC 프로그래밍 (데이터베이스 프로그래밍)
3. Spring JDBC
4. Spring Data JPA(ORM기술) - 직접 SQL을 사용하지 않아도 된다. (내부적으로 SQL을 생성하여 동작) - 어떤 SQL을 생성할 것인지를 예상할 수 있어야 한다.

---

![img_7.png](img_7.png)

---

DB에 계속 연결하고 끊어지면 시간이 오래 걸리니까 DBMS랑 미리 연결한 객체를 많이 만든 것이다. -> 커넥션 객체
커넥션 풀은 미리 커넥션을 연결해놓고 빌려온걸 끊는게 아니라 되돌려주는 매커니즘이다. -> 성능이 좋아진다.

![img_8.png](img_8.png)

---

Persistence Context(영속성 관리자)
 
![img_9.png](img_9.png)

엔티티는 하나지만 엔티티 매니저는 여러개가 존재한다. <- 쓰레드에 안전해야 하기 때문에 여러개 있어야함. 즉 필요할때 매니저를 만들어서 사용한다. -> EntityManagerFactory
Entity, Persistence Context, Entity Manager, JPA Exception, Transaction <- 중요한 단어들
이것들을 다 하나로 묶어서 편하게 사용하게 해주는 것이 Spring Data JPA입니다.

![img_10.png](img_10.png)

--- 

![img_11.png](img_11.png)

---

## JSP에서 JSTL을 사용

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    taglib설정을 한다. jstl core라이브러리의 약자인 c를 네임스페이스로 사용한다.

        List<ToDo> toDoList =  toDoService.getToDoList();
        model.addAttribute("toDoList", toDoList);
    위와 같이 컨트롤러가 값을 담아서 보냈다.
    이때 items에는 model.addAttribute에 넣은 key값.
    <c:forEach var="todo" items="${toDoList}" varStatus="status">
        <p>${todo.todo}"/></p> // EL 표기법 todo가 참조하는 객체의 todo프로퍼티(getTodo())를 사용한다.
    </c:forEach>

---

브라우저 ------------- http://localhost/todos/list -------> ToDoController.list() 결과 ------------> list.jsp
        <-----------------------------------------------------------------------------------------

사용자가 할일을 입력, 버튼 클릭   -------Post방식 /todos/addToDo ----->              ToDoController.addToDo (값을 저장)
        <---------------------- 상태코드 301, location응답해더 /todos/list -------

자동요청  ------------- http://localhost/todos/list -------> ToDoController.list() 결과 ------------> list.jsp
        <-----------------------------------------------------------------------------------------

---

## Database vs DBMS

Database와 DBMS를 어린이에게 설명하시오.
아이들이 보는 책       --------> Database
책들을 관리하는 분 - 엄마 ------> DBMS

---

## Database

- 데이터의 집합
- 여러 응용 시스템(프로그램)들의 통합된 정보들을 저장하여 운영할 수 있는 공용 데이터의 집합
- 효율적으로 저장, 검색, 갱신할 수 있도록 데이터 집합들끼리 연관시키고 조직화되어야 한다.

---

## 데이터베이스의 특성

- 실시간 접근성
- 계속적인 변화
- 동시 공유성
- 내용 참조

---

## 데이터 베이스 관리 시스템(DBMS)

- 데이터베이스를 관리하는 소프트웨어
- 여러 응용 소프트웨어 또는 시스템이 동시에 데이터베이스에 접근하여 사용할 수 있게 한다.
- Oracle,SQL server, MySQL, DB2 등의 상용 또는 공개 DBMS가 있다.

---

## 관계형 데이터베이스(Relational Database = RDB)

- 키와 값들의 간단한 관계를 테이블화 시킨 매우 간단한 원칙의 개념의 데이터베이스

---

## docker를 이용한 MySQL 사용하기.

도커 환경이 실행되는 컴퓨터(Mac or Windows)  ---- 폴더 ( ./database/datadir/)   3306 (포트포워딩) -> 설정에서 3306:3306 

------> Docker-desktop -----> MySQL DBMS (port 3306) ( /var/lib/mysql)

( /var/lib/mysql)를 ( ./database/datadir/) 폴더와 연결시켜서 종료하면 사라지는 데이터를 저장할 수 있게 하는 것이다.

만약 내 컴퓨터에서 mysql서버에 4000포트로 접속한다면 포트포워딩하여 docker안에 mysql(3306)로 포트포워딩되어서 접속된다는 것입니다.

---

MySQL workbench (client)

select version(), current_date; (SQL) --------- 전송 -------------> MYSQL DBMS (SQL 실행)
                                      <-------- 결과 --------------

SQL

- 테이블 생성, 테이블 삭제, 테이블 수정
- 값을 저장, 수정, 삭제, 조회
- 권한 부여, 데이터베이스 생성

조회를 하려면 Data가 필요하다.

Oracle DBMS - 예제 Database - HR 사용자 계정.

MySQL 용으로 제작

https://github.com/urstoryp/hr-schema-mysql/blob/master/hr-schema-mysql.sql

---
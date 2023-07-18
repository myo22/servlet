package com.example.servletexam;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// ?이하를 파라미터
// 파라미터 구조 :  이름1=값 & 이름2=값2 & 이름3=값3
// http://localhost:8080/plus?x=10&y=20 <- 여기서 10과 20은 숫자처럼 보이지만 문자열이다.
@WebServlet(name = "plusServlet", value = "/plus") // path가 /plus이다.
public class PlusServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strX = req.getParameter("x"); // ?x=10&y=20 전달되지 않으면 null을 반환
        String strY = req.getParameter("y");

        // 입력받은 값이 올바른지 검증

        int x = Integer.parseInt(strX);
        int y = Integer.parseInt(strY);

        int value = x + y;


        req.setAttribute("x", x);
        req.setAttribute("y", y);
        req.setAttribute("value", value);

        List<String> list = new ArrayList<>();
        list.add("kim");
        list.add("kang");
        list.add("java");

        req.setAttribute("list", list);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/plus.jsp");
        requestDispatcher.forward(req, resp); // 포워딩. (포워딩과 리다이렉트)
    }
}

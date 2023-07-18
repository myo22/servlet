<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>Plus</h1>
<br/>
<%--<img src="java.png">--%>
<%--<a href="hello-servlet">반갑습니다 접니다. 네 저에요</a>--%>
<form method="get" action="/plus">
    x : <input type="text" name="x"><br>
    y : <input type="text" name="y"><br>
    <input type="submit" value="plus">
</form>
</body>
</html>
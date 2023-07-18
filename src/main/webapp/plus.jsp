<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-18
  Time: 오후 2:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>plus.jsp</title>
</head>
<body>
<%--out.write(request.getAttribute("x").toString());--%>
<%--out.write("+");--%>
<%--out.write(request.getAttribute("y").toString());--%>
<%--out.write("=");--%>
<%--out.write(request.getAttribute("value").toString());--%>

<%--el 표기법 ${x}--%>
<h1>${x} + ${y} = ${value}</h1>
</body>
</html>

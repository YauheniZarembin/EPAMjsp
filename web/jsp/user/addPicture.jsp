<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 31.01.2018
  Time: 0:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/upload" enctype="multipart/form-data" method="POST">
    UPLOAD FILE : <input type="file" name="content" accept="image/*">
    <input type="submit" value="uploadFile">
</form>
<ctg:infoTimeTag/>
</body>
</html>

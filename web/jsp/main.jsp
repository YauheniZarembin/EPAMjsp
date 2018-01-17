<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html><head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<h3>Welcome</h3>
<hr/>
${user}, hello!
<hr/>
<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="logout" />

    <br/>
    <input type="submit" value="Logout">
</form>
</body></html>
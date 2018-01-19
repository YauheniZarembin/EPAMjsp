
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>EPAM-cafe</title>
    <h1>LOG IN</h1>
    <link rel="stylesheet" type="text/css" href="css/styles.css">

</head>
<body>
<form name="loginForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="login" />
    Login:<br/>
    <input type="text" name="login" value=""/>
    <br/>Password:<br/>
    <input type="password" name="password" value=""/>
    <br/>
    ${Message}
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
    <br/>
    <input type="submit" value="Log in"/>
    <input type="button" value="Sign up" onClick='location.href="jsp/signup.jsp"'>
</form><hr/>
</body></html>
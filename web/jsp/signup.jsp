<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html><head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css"/>
<body>

<form name="loginForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="signup" />
    <table cellspacing="5" cellpadding="5">
        <tr>
            <td colspan="2">
                <h1> Enter your personal information </h1>
            </td>
        </tr>

        <tr>
            <td align="right" valign="top">Enter user_name</td>
            <td><input type="text" name="username" value=""></td>
        </tr>
        <tr>
            <td align="right" valign="top">Enter password</td>
            <td><input type="password" name="password" value=""></td>
        </tr>
        <tr>
            <td align="right" valign="top"> </td>
            <td align="right" valign="top">Password should contain numbers and letters.</td>
        </tr>
        <tr>
            <td align="right" valign="top">Enter name</td>
            <td><input type="text" name="name" value=""></td>
        </tr>
        <tr>
            <td align="right" valign="top">Enter lastname</td>
            <td><input type="text" name="lastname" value=""></td>
        </tr>
        <tr>
            <td align="right" valign="top">Enter EMail</td>
            <td><input type="text" name="email" value=""></td>
        </tr>
        <tr>
            <td align="right" valign="top">Enter Card number</td>
            <td><input type="text" name="card number" value=""></td>
        </tr>
        <tr>
            <td align="right" valign="top"> </td>
            <td align="right" valign="top">Card should be in bank</td>
        </tr>
        <br/>
        ${errorMessage}
    </table>
    <input type="submit" value="SIGN UP">
</form><hr/>
</body></html>
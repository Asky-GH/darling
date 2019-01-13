<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Index page</title>
    </head>
    
    <body>
        <c:choose>
            <c:when test="${user != null}">
                <p>Welcome ${user.email}!</p>
                <a href="#" onclick="event.preventDefault(); document.getElementById('logout-form').submit();">Logout</a>
                <form id="logout-form" method="post" style="display: none;" action="/logout"></form>
            </c:when>
            <c:otherwise>
                <p>Welcome guest!</p>
                <a href="/login">Login</a>
            </c:otherwise>
        </c:choose>
    </body>
</html>

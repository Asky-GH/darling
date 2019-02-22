<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Error page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/error.css">
    </head>

    <body>
        <div class="overlay"></div>
        <div class="terminal">
            <h1>Error <span class="errorcode">404</span></h1>
            <p class="output">The page you are looking for might have been removed, had its name changed or is temporarily unavailable</ü>
            <p class="output">Please try <a href="#1">this link</a> or <a href="#2">this link</a></p>
            <p class="output">Good luck</p>
        </div>
    </body>
</html>

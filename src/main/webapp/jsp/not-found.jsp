<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${language.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
    <head>
        <title><fmt:message key="key.notFoundPageTitle"/></title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/not-found.css">
    </head>

    <body>
        <div class="overlay"></div>
        <div class="terminal">
            <h1><fmt:message key="key.notFoundPageHeader"/> <span class="errorcode">404</span></h1>
            <p class="output"><fmt:message key="key.notFoundPageMessage"/></p>
            <p class="output">
                <a href="${pageContext.request.contextPath}/main"><fmt:message key="key.notFoundPageLinkToMain"/></a>
            </p>
        </div>
    </body>
</html>

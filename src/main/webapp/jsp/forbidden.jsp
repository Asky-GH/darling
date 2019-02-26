<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${language.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
    <head>
        <title><fmt:message key="key.forbiddenPageTitle"/></title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/forbidden.css">
    </head>

    <body>
        <div class="gandalf">
            <div class="fireball"></div>
            <div class="skirt"></div>
            <div class="sleeves"></div>
            <div class="shoulders">
                <div class="hand left"></div>
                <div class="hand right"></div>
            </div>
            <div class="head">
                <div class="hair"></div>
                <div class="beard"></div>
            </div>
        </div>
        <div class="message">
            <h1><fmt:message key="key.forbiddenPageErrorMessage"/></h1>
            <p>
                <a href="${pageContext.request.contextPath}/main"><fmt:message key="key.forbiddenPageLinkToMain"/></a>
            </p>
        </div>
    </body>
</html>

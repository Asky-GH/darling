<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Main page</title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <ul>
            <c:forEach var="match" items="${users}">
                <c:choose>
                    <c:when test="${user != null && match.id == user.id}">

                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="${pageContext.request.contextPath}/match?id=${match.id}">${match.info.firstName}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

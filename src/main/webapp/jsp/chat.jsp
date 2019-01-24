<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Chat page</title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <table>
            <tr>
                <td>First name</td>
                <td>${match.info.firstName}</td>
            </tr>
            <tr>
                <td>Last name</td>
                <td>${match.info.lastName}</td>
            </tr>
            <tr>
                <td>Gender</td>
                <td>${match.info.gender.name}</td>
            </tr>
            <tr>
                <td>Birthday</td>
                <td>${match.info.birthday}</td>
            </tr>
            <tr>
                <td>Country</td>
                <td>${match.info.country.name}</td>
            </tr>
        </table>

        <table>
            <c:forEach var="message" items="${messages}">
                <tr>
                    <c:choose>
                        <c:when test="${message.user_id == match.id}">
                            <td>
                                ${message.text} - ${message.created_at}
                            </td>
                            <td></td>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                            <td>
                                ${message.text} - ${message.created_at}
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>

        <form method="post" action="${pageContext.request.contextPath}/chat?id=${match.id}">
            <textarea rows="4" cols="50" placeholder="Type your message here..." name="text"></textarea>
            <input type="submit" value="Send">
        </form>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

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
                <td>${receiver.profile.firstName}</td>
            </tr>
            <tr>
                <td>Last name</td>
                <td>${receiver.profile.lastName}</td>
            </tr>
            <tr>
                <td>Birthday</td>
                <td>${receiver.profile.birthday}</td>
            </tr>
            <tr>
                <td>Gender</td>
                <td>${receiver.profile.gender.type}</td>
            </tr>
            <tr>
                <td>Country</td>
                <td>${receiver.profile.country.name}</td>
            </tr>
            <tr>
                <td>City</td>
                <td>${receiver.profile.city.name}</td>
            </tr>
        </table>

        <table>
            <c:forEach var="message" items="${messages}">
                <tr>
                    <c:choose>
                        <c:when test="${message.sender_id == receiver.id}">
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

        <form method="post" action="${pageContext.request.contextPath}/chat?id=${receiver.id}">
            <textarea rows="4" cols="50" placeholder="Type your message here..." name="text"></textarea>
            <input type="submit" value="Send">
        </form>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

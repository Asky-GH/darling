<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Match page</title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <table>
            <tr>
                <td>First name</td>
                <td>${match.profile.firstName}</td>
            </tr>
            <tr>
                <td>Last name</td>
                <td>${match.profile.lastName}</td>
            </tr>
            <tr>
                <td>Birthday</td>
                <td>${match.profile.birthday}</td>
            </tr>
            <tr>
                <td>Gender</td>
                <td>${match.profile.gender.type}</td>
            </tr>
            <tr>
                <td>Country</td>
                <td>${match.profile.country.name}</td>
            </tr>
            <tr>
                <td>City</td>
                <td>${match.profile.city.name}</td>
            </tr>
        </table>

        <a href="${pageContext.request.contextPath}/chat?id=${match.id}">Chat</a>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

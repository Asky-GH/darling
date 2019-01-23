<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Profile page</title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <c:choose>
            <c:when test="${user.info == null}">
                <p>Please fill in the information about yourself, so that more people can see your profile.</p>
                <form method="post" action="${pageContext.request.contextPath}/profile">
                    <input type="text" name="firstName" placeholder="First name" autofocus>
                    <input type="text" name="lastName" placeholder="Last name">
                    <input type="radio" name="gender" value="female">Female
                    <input type="radio" name="gender" value="male">Male
                    <input type="date" name="birthday">Birthday
                    <input name="countryName" list="countries" placeholder="Country">
                    <datalist id="countries">
                        <option value="Kazakhstan">
                        <option value="Russia">
                        <option value="United States">
                        <option value="Spain">
                    </datalist>
                    <p class="help is-danger">${errorMessage}</p>
                    <input type="submit" value="Submit">
                </form>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <td>First name</td>
                        <td>${user.info.firstName}</td>
                    </tr>
                    <tr>
                        <td>Last name</td>
                        <td>${user.info.lastName}</td>
                    </tr>
                    <tr>
                        <td>Gender</td>
                        <td>${user.info.gender.name}</td>
                    </tr>
                    <tr>
                        <td>Birthday</td>
                        <td>${user.info.birthday}</td>
                    </tr>
                    <tr>
                        <td>Country</td>
                        <td>${user.info.country.name}</td>
                    </tr>
                </table>
            </c:otherwise>
        </c:choose>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

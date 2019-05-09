<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Cities page</title>
    </head>

    <body>
        <h1>Cities</h1>

        <table>
            <tr>
                <th>Id</th>
                <th>Language</th>
                <th>Country</th>
                <th>Name</th>
                <th>Edit city</th>
            </tr>
            <c:forEach items="${cities}" var="city">
                <form method="post">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="id" value="${city.id}">
                    <input type="hidden" name="languageId" value="${city.language.id}">
                    <input type="hidden" name="countryId" value="${city.country.id}">
                    <tr>
                        <td>
                            <input type="number" value="${city.id}" disabled>
                        </td>
                        <td>
                            <select disabled>
                                <option>${city.language.name}</option>
                            </select>
                        </td>
                        <td>
                            <select disabled>
                                <option>${city.country.name}</option>
                            </select>
                        </td>
                        <td>
                            <input type="text" value="${city.name}" name="name">
                        </td>
                        <td>
                            <input type="submit" value="Edit">
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>

        <h4>New city</h4>
        <form method="post">
            <input type="hidden" name="action" value="create">
            <label for="id">Id</label>
            <select id="id" name="id">
                <c:set var="idCounter" value="${1}"/>
                <c:forEach var="id" items="${ids}">
                    <option value="${id}">${id}</option>
                    <c:set var="idCounter" value="${idCounter + 1}"/>
                </c:forEach>
                <option value="${idCounter}">New</option>
            </select>
            <label for="language">Language</label>
            <select id="language" name="languageId">
                <c:forEach items="${languages}" var="language">
                    <option value="${language.id}">${language.name}</option>
                </c:forEach>
            </select>
            <label for="country">Country</label>
            <select id="country" name="countryId">
                <c:forEach items="${countries}" var="country">
                    <option value="${country.id}">${country.name}</option>
                </c:forEach>
            </select>
            <label for="name">Name</label>
            <input id="name" type="text" name="name">
            <input type="submit" value="Create">
        </form>

        <a href="${pageContext.request.contextPath}/admin">Go back</a>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
	<script>var ctx="${pageContext.request.contextPath}"</script>
        <script defer src="${pageContext.request.contextPath}/static/js/countries.js"></script>
    </body>
</html>

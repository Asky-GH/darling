<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Countries page</title>
    </head>

    <body>
        <h1>Countries</h1>

        <table>
            <tr>
                <th>Id</th>
                <th>Language</th>
                <th>Name</th>
                <th>Edit country</th>
            </tr>
            <c:forEach items="${countries}" var="country">
                <form method="post">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="id" value="${country.id}">
                    <input type="hidden" name="languageId" value="${country.language.id}">
                    <tr>
                        <td>
                            <input type="number" value="${country.id}" disabled>
                        </td>
                        <td>
                            <select disabled>
                                <option>${country.language.name}</option>
                            </select>
                        </td>
                        <td>
                            <input type="text" value="${country.name}" name="name">
                        </td>
                        <td>
                            <input type="submit" value="Edit">
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>

        <h4>New country</h4>
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
            <label for="name">Name</label>
            <input id="name" type="text" name="name">
            <input type="submit" value="Create">
        </form>

        <a href="${pageContext.request.contextPath}/admin">Go back</a>
    </body>
</html>

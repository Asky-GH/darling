<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Genders page</title>
    </head>

    <body>
        <h1>Genders</h1>

        <table>
            <tr>
                <th>Id</th>
                <th>Language</th>
                <th>Type</th>
                <th>Edit gender</th>
            </tr>
            <c:forEach items="${genders}" var="gender">
                <form method="post">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="id" value="${gender.id}">
                    <input type="hidden" name="languageId" value="${gender.language.id}">
                    <tr>
                        <td>
                            <input type="number" value="${gender.id}" disabled>
                        </td>
                        <td>
                            <select disabled>
                                <option>${gender.language.name}</option>
                            </select>
                        </td>
                        <td>
                            <input type="text" value="${gender.type}" name="type">
                        </td>
                        <td>
                            <input type="submit" value="Edit">
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>

        <h4>New gender</h4>
        <form method="post">
            <input type="hidden" name="action" value="create">
            <label for="id">Id</label>
            <select id="id" name="id">
                <c:forEach var="id" items="${ids}">
                    <option value="${id}">${id}</option>
                </c:forEach>
            </select>
            <label for="language">Language</label>
            <select id="language" name="languageId">
                <c:forEach items="${languages}" var="language">
                    <option value="${language.id}">${language.name}</option>
                </c:forEach>
            </select>
            <label for="type">Type</label>
            <input id="type" type="text" name="type">
            <input type="submit" value="Create">
        </form>

        <a href="${pageContext.request.contextPath}/admin">Go back</a>
    </body>
</html>

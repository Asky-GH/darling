<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Languages page</title>
    </head>

    <body>
        <h1>Languages</h1>

        <table>
            <tr>
                <th>Id</th>
                <th>Locale</th>
                <th>Name</th>
                <th>Edit language</th>
            </tr>
            <c:forEach var="language" items="${languages}">
                <form action="?id=${language.id}" method="post">
                    <input type="hidden" name="action" value="edit">
                    <tr>
                        <td>${language.id}</td>
                        <td>
                            <input type="text" value="${language.locale}" name="locale">
                        </td>
                        <td>
                            <input type="text" value="${language.name}" name="name">
                        </td>
                        <td>
                            <input type="submit" value="Edit">
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>

        <h4>New language</h4>
        <form method="post">
            <input type="hidden" name="action" value="create">
            <label for="locale">Locale</label>
            <input id="locale" type="text" name="locale">
            <label for="name">Name</label>
            <input id="name" type="text" name="name">
            <input type="submit" value="Create">
        </form>

        <a href="${pageContext.request.contextPath}/admin">Go back</a>
    </body>
</html>

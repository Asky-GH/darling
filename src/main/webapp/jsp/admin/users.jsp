<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Users page</title>
    </head>

    <body>
        <h1>Users</h1>

        <table>
            <tr>
                <th>Id</th>
                <th>Email</th>
                <th>Role</th>
                <th>Delete user</th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.email}</td>
                    <td>
                        <form action="?id=${user.id}" method="post">
                            <input type="hidden" name="action" value="change-role">
                            <select name="roleId" onchange="this.form.submit()">
                                <c:forEach items="${roles}" var="role">
                                    <c:choose>
                                        <c:when test="${role.id == user.role.id}">
                                            <option value="${role.id}" selected>${role.type}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${role.id}">${role.type}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </form>
                    </td>
                    <td>
                        <form action="?id=${user.id}" method="post">
                            <input type="hidden" name="action" value="delete">
                            <input type="submit" value="Delete">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <a href="${pageContext.request.contextPath}/admin">Go back</a>
    </body>
</html>

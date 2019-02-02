<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Main page</title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns is-multiline">
                    <c:forEach var="user" items="${users}">
                        <c:if test="${user.role.type == 'user'}">
                            <div class="column is-one-quarter">
                                <a href="${pageContext.request.contextPath}/match?id=${user.id}">
                                    <div class="card">
                                        <div class="card-image">
                                            <figure class="image is-4by3">
                                                <c:choose>
                                                    <c:when test="${user.profile.image.url != null}">
                                                        <img src="${user.profile.image.url}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${user.profile.gender.type == 'female' ? 'static/img/female.png'
                                                                                             : 'static/img/male.png'}">
                                                    </c:otherwise>
                                                </c:choose>
                                            </figure>
                                        </div>
                                        <div class="card-content">
                                            <div class="media">
                                                <div class="media-content">
                                                    <p class="title is-4">${user.profile.firstName} ${user.profile.lastName}</p>
                                                    <p class="subtitle is-6">Country: ${user.profile.country.name}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

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
                <div class="columns is-multiline is-centered">
                    <c:forEach var="user" items="${users}">
                        <c:if test="${user.role.type == 'user' && user.id != principal.id}">
                            <div class="column is-narrow" style="padding: 50px">
                                <a href="${pageContext.request.contextPath}/match?id=${user.id}">
                                    <div class="media">
                                        <div class="media-left">
                                            <figure class="image is-48x48">
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
                                        <div class="media-content">
                                            <div class="content">
                                                <p class="title is-4">${user.profile.firstName} ${user.profile.lastName}</p>
                                                <p class="subtitle is-6">${user.profile.country.name}, ${user.profile.city.name}</p>
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

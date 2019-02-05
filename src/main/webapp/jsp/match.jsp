<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Match page</title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-8 is-offset-2">
                        <div class="tile is-ancestor">
                            <div class="tile is-vertical">
                                <div class="tile is-parent">
                                    <article class="tile is-child notification">
                                        <p class="title">${match.profile.firstName} ${match.profile.lastName}</p>
                                    </article>
                                </div>
                                <div class="tile">
                                    <div class="tile is-parent is-vertical">
                                        <article class="tile is-child notification">
                                            <p class="title">${match.profile.birthday}</p>
                                        </article>
                                        <c:choose>
                                            <c:when test="${match.profile.gender.type == 'female'}">
                                                <article class="tile is-child notification is-danger">
                                                    <p class="title">${match.profile.gender.type}</p>
                                                </article>
                                            </c:when>
                                            <c:otherwise>
                                                <article class="tile is-child notification is-link">
                                                    <p class="title">${match.profile.gender.type}</p>
                                                </article>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="tile is-parent">
                                        <article class="tile is-child">
                                            <figure class="image">
                                                <c:choose>
                                                    <c:when test="${match.profile.image.url != null}">
                                                        <img src="${match.profile.image.url}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${match.profile.gender.type == 'female' ? 'static/img/female.png'
                                                                                             : 'static/img/male.png'}">
                                                    </c:otherwise>
                                                </c:choose>
                                            </figure>
                                        </article>
                                    </div>
                                </div>
                                <div class="tile is-parent">
                                    <article class="tile is-child notification">
                                        <p class="title">${match.profile.country.name}, ${match.profile.city.name}</p>
                                    </article>
                                </div>
                                <a href="${pageContext.request.contextPath}/chat?id=${match.id}">
                                    <div class="tile is-parent">
                                        <article class="tile is-child notification is-primary">
                                            <p class="title has-text-centered">Chat</p>
                                        </article>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

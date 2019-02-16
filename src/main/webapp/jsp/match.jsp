<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:setLocale value="${language.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>${match.profile.firstName} ${match.profile.lastName}</title>
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
                                            <p class="title user-age">${match.profile.birthday}</p>
                                        </article>
                                        <c:choose>
                                            <c:when test="${match.profile.gender.type == 'Female' ||
                                                            match.profile.gender.type == 'Женский'}">
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
                                                        <c:choose>
                                                            <c:when test="${match.profile.gender.type == 'Female' ||
                                                                            match.profile.gender.type == 'Женский'}">
                                                                <img src="static/img/female.png">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img src="static/img/male.png">
                                                            </c:otherwise>
                                                        </c:choose>
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
                                <a href="chat?id=${match.id}">
                                    <div class="tile is-parent">
                                        <article class="tile is-child notification is-primary">
                                            <p class="title has-text-centered"><fmt:message key="key.matchPageLinkToChat"/></p>
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

        <script defer src="static/js/age.js"></script>
    </body>
</html>

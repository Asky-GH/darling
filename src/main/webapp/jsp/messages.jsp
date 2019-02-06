<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Messages page</title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-8 is-offset-2">
                        <c:forEach var="dialog" items="${dialogs}">
                            <article class="media">
                                <figure class="media-left">
                                    <p class="image is-64x64">
                                        <c:choose>
                                            <c:when test="${dialog.key.profile.image.url != null}">
                                                <img src="${dialog.key.profile.image.url}">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${dialog.key.profile.gender.type == 'female' ? 'static/img/female.png'
                                                                                             : 'static/img/male.png'}">
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </figure>
                                <div class="media-content">
                                    <a href="${pageContext.request.contextPath}/chat?id=${dialog.key.id}"
                                       style="color: black">
                                        <div class="content">
                                            <p>
                                                <strong>${dialog.key.profile.firstName} ${dialog.key.profile.lastName}</strong>
                                                <br>
                                                    ${dialog.value.text}
                                            </p>
                                        </div>
                                    </a>
                                </div>
                                <c:choose>
                                    <c:when test="${dialog.value.statusId == 1}">
                                        <div class="media-right">
                                            <span class="tag is-danger">
                                                Unread
                                            </span>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </article>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${language.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title><fmt:message key="key.messagesPageTitle"/></title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-8 is-offset-2">
                        <c:forEach var="dialog" items="${dialogs}">
                            <a href="chat?id=${dialog.key.id}" style="color: black">
                                <div class="notification" style="margin: 20px">
                                    <article class="media">
                                        <figure class="media-left">
                                            <p class="image is-64x64">
                                                <c:choose>
                                                    <c:when test="${dialog.key.profile.image.url != null}">
                                                        <img src="${dialog.key.profile.image.url}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${dialog.key.profile.gender.type == 'Female' ||
                                                                        dialog.key.profile.gender.type == 'Женский'}">
                                                                <img src="static/img/female.png">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img src="static/img/male.png">
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>
                                        </figure>
                                        <div class="media-content">
                                            <div class="content">
                                                <p>
                                                    <strong>${dialog.key.profile.firstName} ${dialog.key.profile.lastName}</strong>
                                                    &emsp;<small><fmt:formatDate value="${dialog.value.createdAt}" type="both"/></small>
                                                    <br>${dialog.value.text}
                                                </p>
                                            </div>
                                        </div>
                                        <c:choose>
                                            <c:when test="${dialog.value.statusId == 1}">
                                                <div class="media-right">
                                                <span class="tag is-danger">
                                                    <fmt:message key="key.messagesPageUnreadTag"/>
                                                </span>
                                                </div>
                                            </c:when>
                                        </c:choose>
                                    </article>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

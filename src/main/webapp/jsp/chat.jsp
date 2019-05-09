<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${language.locale}"/>
<input id="locale" type="hidden" value="${language.locale}">
<fmt:setBundle basename="locale"/>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title><fmt:message key="key.chatPageTitle"/> ${receiver.profile.firstName} ${receiver.profile.lastName}</title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section id="top" class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-8 is-offset-2">
                        <a href="match?id=${receiver.id}" style="color: black">
                            <div class="notification is-warning">
                                <div class="media">
                                    <div class="media-left">
                                        <figure class="image is-64x64">
                                            <c:choose>
                                                <c:when test="${receiver.profile.image.url != null}">
                                                    <img src="${receiver.profile.image.url}">
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${receiver.profile.gender.type == 'Female' ||
                                                                        receiver.profile.gender.type == 'Женский'}">
                                                            <img src="static/img/female.png">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="static/img/male.png">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </figure>
                                    </div>
                                    <div class="media-content">
                                        <div class="content">
                                            <p>
                                                <strong>${receiver.profile.firstName} ${receiver.profile.lastName}</strong>
                                                <br>${receiver.profile.country.name}, ${receiver.profile.city.name}
                                                <br><small class="user-age">${receiver.profile.birthday}</small>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
                <div id="msgs">
                    <c:forEach var="message" items="${messages}">
                        <div class="columns">
                            <c:choose>
                                <c:when test="${message.senderId == receiver.id}">
                                    <div class="column is-5 is-offset-2 is-11-mobile">
                                        <div class="notification content">
                                            <p>${message.text}</p>
                                            <p style="text-align: right">
                                                <small><fmt:formatDate value="${message.createdAt}" type="both"/></small>
                                            </p>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="column is-5 is-offset-5 is-11-mobile is-offset-1-mobile">
                                        <div class="notification is-primary content">
                                            <p>${message.text}</p>
                                            <p style="text-align: right">
                                                <small><fmt:formatDate value="${message.createdAt}" type="both"/></small>
                                            </p>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </div>
                <form method="post" action="chat?id=${receiver.id}">
                    <input id="sender" type="hidden" value="${principal.id}">
                    <input id="receiver" type="hidden" value="${receiver.id}">
                    <div class="columns">
                        <div class="column is-8 is-offset-2">
                            <c:choose>
                                <c:when test="${textError != null}">
                                    <div class="field">
                                        <div class="control">
                                            <fmt:message key="key.chatPageMessagePlaceholder" var="textPlaceholder"/>
                                            <c:choose>
                                                <c:when test="${text != null}">
                                                    <textarea class="textarea is-danger" name="text" autofocus>${text}</textarea>
                                                </c:when>
                                                <c:otherwise>
                                                    <textarea class="textarea is-danger" placeholder="${textPlaceholder}" name="text" autofocus></textarea>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <p class="help is-danger"><fmt:message key="${textError}"/></p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="field">
                                        <div class="control">
                                            <fmt:message key="key.chatPageMessagePlaceholder" var="textPlaceholder"/>
                                            <textarea class="textarea" placeholder="${textPlaceholder}" name="text" autofocus></textarea>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="columns">
                        <div class="column is-8 is-offset-2">
                            <div class="field is-grouped">
                                <div class="control">
                                    <fmt:message key="key.chatPageSendButton" var="send"/>
                                    <input class="button is-info is-fullwidth" type="submit" value="${send}">
                                </div>
                                <div class="control">
                                    <a class="button" href="chat?${pageContext.request.queryString}#top">
                                        <fmt:message key="key.chatPageBackToTopButton"/>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

        </section>

        <jsp:include page="layout/footer.jsp"/>

        <script>var ctx="${pageContext.request.contextPath}"</script>
	<script defer src="static/js/chat.js"></script>
        <script defer src="static/js/age.js"></script>
    </body>
</html>

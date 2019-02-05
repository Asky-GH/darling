<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Chat page</title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div id="msgs">
                    <c:forEach var="message" items="${messages}">
                        <div class="columns">
                            <c:choose>
                                <c:when test="${message.sender_id == receiver.id}">
                                    <div class="column is-4 is-offset-2">
                                        <div class="notification">${message.text} - ${message.created_at}</div>
                                    </div>
                                    <div class="column is-4">

                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="column is-4 is-offset-2">

                                    </div>
                                    <div class="column is-4">
                                        <div class="notification is-primary">${message.text} - ${message.created_at}</div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </div>
                <form method="post" action="${pageContext.request.contextPath}/chat?id=${receiver.id}">
                    <input id="sender" type="hidden" value="${principal.id}">
                    <input id="receiver" type="hidden" value="${receiver.id}">
                    <div class="columns">
                        <div class="column is-8 is-offset-2">
                            <div class="field">
                                <div class="control">
                                    <textarea class="textarea" placeholder="Type your message here..." name="text" autofocus></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="columns">
                        <div class="column is-8 is-offset-2">
                            <nav class="level">
                                <div class="level-left">
                                    <div class="level-item">
                                        <div class="media">
                                            <div class="media-left">
                                                <figure class="image is-48x48">
                                                    <c:choose>
                                                        <c:when test="${receiver.profile.image.url != null}">
                                                            <img src="${receiver.profile.image.url}">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="${receiver.profile.gender.type == 'female' ? 'static/img/female.png'
                                                                                             : 'static/img/male.png'}">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </figure>
                                            </div>
                                            <div class="media-content">
                                                <div class="content">
                                                    <p class="title is-4">${receiver.profile.firstName} ${receiver.profile.lastName}</p>
                                                    <p class="subtitle is-6">${receiver.profile.country.name}, ${receiver.profile.city.name}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="level-right">
                                    <div class="level-item">
                                        <div class="control">
                                            <input class="button is-info" type="submit" value="Send">
                                        </div>
                                    </div>
                                </div>
                            </nav>
                        </div>
                    </div>
                </form>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>

        <script defer src="static/js/chat.js"></script>
    </body>
</html>

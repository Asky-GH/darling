<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Profile page</title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-4">
                        <figure class="image" style="margin-bottom: 10px">
                            <c:choose>
                                <c:when test="${principal.profile.image.url != null}">
                                    <img src="${principal.profile.image.url}">
                                </c:when>
                                <c:otherwise>
                                    <img src="${principal.profile.gender.type == 'female' ? 'static/img/female.png'
                                                                                             : 'static/img/male.png'}">
                                </c:otherwise>
                            </c:choose>
                        </figure>
                        <hr>
                        <form method="post" action="${pageContext.request.contextPath}/profile" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="change-avatar">
                            <div class="field">
                                <div class="file is-left">
                                    <label class="file-label">
                                        <input class="file-input" type="file" name="image" id="file">
                                        <span class="file-cta">
                                            <span class="file-icon">
                                              <i class="fas fa-upload"></i>
                                            </span>
                                            <span class="file-label">
                                              Choose file…
                                            </span>
                                        </span>
                                        <span class="file-name" id="filename">
                                            No file chosen
                                        </span>
                                    </label>
                                </div>
                            </div>
                            <div class="field">
                                <p class="help is-danger">${avatarErrorMessage}</p>
                                <input class="button is-fullwidth is-info" type="submit" value="Change avatar"/>
                            </div>
                        </form>
                    </div>
                    <div class="column is-8">
                        <p class="title">${principal.profile.firstName} ${principal.profile.lastName}</p>
                        <div class="columns">
                            <div class="column">
                                <p class="title">${principal.profile.birthday}</p>
                            </div>
                            <div class="column">
                                <p class="title">${principal.profile.gender.type}</p>
                            </div>
                        </div>
                        <hr>
                        <div class="columns" style="margin-bottom: 27px">
                            <div class="column">
                                <p class="title">${principal.email}</p>
                                <form method="post" action="${pageContext.request.contextPath}/profile">
                                    <input type="hidden" name="action" value="change-email">
                                    <div class="field">
                                        <div class="control">
                                            <div id="emailControl" class="control has-icons-left has-icons-right">
                                                <input id="email" name="email" type="text" placeholder="new email" class="input">
                                                <span class="icon is-small is-left"><i class="fas fa-envelope"></i></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div>
                                        <p class="help is-danger">${emailErrorMessage}</p>
                                        <input class="button is-fullwidth is-info" type="submit" value="Change email"/>
                                    </div>
                                </form>
                            </div>
                            <div class="column" style="padding-top: 24px">
                                <form method="post" action="${pageContext.request.contextPath}/profile">
                                    <input type="hidden" name="action" value="change-password">
                                    <div class="field">
                                        <div id="passwordControl" class="control has-icons-left has-icons-right">
                                            <input id="password" name="password" type="password" placeholder="new password"
                                                   class="input">
                                            <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div id="confirmControl" class="control has-icons-left has-icons-right">
                                            <input id="confirm" name="confirmPassword" type="password"
                                                   placeholder="confirm password" class="input">
                                            <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                        </div>
                                    </div>
                                    <div>
                                        <p class="help is-danger">${passwordErrorMessage}</p>
                                        <input class="button is-fullwidth is-info" type="submit" value="Change password"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <p class="title">${principal.profile.country.name}, ${principal.profile.city.name}</p>
                        <form method="post" action="${pageContext.request.contextPath}/profile">
                            <input type="hidden" name="action" value="change-location">
                            <div class="field is-grouped">
                                <div class="control has-icons-left">
                                    <div class="select">
                                        <select id="country" name="country_id">
                                            <c:forEach var="country" items="${countries}">
                                                <option value="${country.id}">${country.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="icon is-small is-left">
                                        <i class="fas fa-globe"></i>
                                    </div>
                                </div>
                                <div class="select is-rounded">
                                    <select id="city" name="city_id">
                                        <c:forEach var="city" items="${cities}">
                                            <option value="${city.id}">${city.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div>
                                <p class="help is-danger">${errorMessage}</p>
                                <input class="button is-fullwidth is-info" type="submit" value="Change location"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>

        <script defer src="static/js/location.js"></script>
        <script defer src="static/js/file.js"></script>
    </body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${language.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title><fmt:message key="key.profilePageTitle"/></title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-4">
                        <figure class="image" style="margin-bottom: 13px">
                            <c:choose>
                                <c:when test="${principal.profile.image.url != null}">
                                    <img src="${principal.profile.image.url}">
                                </c:when>
                                <c:otherwise>
                                    <img src="${principal.profile.gender.type == 'Female' ? 'static/img/female.png'
                                                                                             : 'static/img/male.png'}">
                                </c:otherwise>
                            </c:choose>
                        </figure>
                        <form method="post" action="profile" enctype="multipart/form-data">
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
                                              <fmt:message key="key.profilePageChooseButton"/>
                                            </span>
                                        </span>
                                        <span class="file-name" id="filename">
                                            <fmt:message key="key.profilePageFileName"/>
                                        </span>
                                    </label>
                                </div>
                            </div>
                            <div class="field">
                                <p class="help is-danger">${avatarErrorMessage}</p>
                                <fmt:message key="key.profilePageChangeAvatarButton" var="changeAvatar"/>
                                <input class="button is-fullwidth is-info" type="submit" value="${changeAvatar}"/>
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
                        <div class="columns">
                            <div class="column" style="padding-top: 28px">
                                <form method="post" action="profile">
                                    <input type="hidden" name="action" value="change-email">
                                    <div class="field">
                                        <div class="label">${principal.email}</div>
                                        <div class="control">
                                            <div class="control has-icons-left has-icons-right">
                                                <fmt:message key="key.profilePageEmailPlaceholder" var="newEmail"/>
                                                <input name="email" type="text" placeholder="${newEmail}" class="input">
                                                <span class="icon is-small is-left"><i class="fas fa-envelope"></i></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div>
                                        <p class="help is-danger">${emailErrorMessage}</p>
                                        <fmt:message key="key.profilePageChangeEmailButton" var="changeEmail"/>
                                        <input class="button is-fullwidth is-info" type="submit" value="${changeEmail}"/>
                                    </div>
                                </form>
                            </div>
                            <div class="column">
                                <form method="post" action="profile">
                                    <input type="hidden" name="action" value="change-password">
                                    <div class="field">
                                        <div class="control has-icons-left has-icons-right">
                                            <fmt:message key="key.profilePagePasswordPlaceholder" var="newPassword"/>
                                            <input name="password" type="password" placeholder="${newPassword}" class="input">
                                            <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="control has-icons-left has-icons-right">
                                            <fmt:message key="key.profilePageConfirmPasswordPlaceholder" var="confirmPassword"/>
                                            <input name="confirmPassword" type="password" placeholder="${confirmPassword}" class="input">
                                            <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                        </div>
                                    </div>
                                    <div>
                                        <p class="help is-danger">${passwordErrorMessage}</p>
                                        <fmt:message key="key.profilePageChangePasswordButton" var="changePassword"/>
                                        <input class="button is-fullwidth is-info" type="submit" value="${changePassword}"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <form method="post" action="profile">
                            <input type="hidden" name="action" value="change-location">
                            <div class="label">${principal.profile.country.name}, ${principal.profile.city.name}</div>
                            <div class="field is-grouped">
                                <div class="control has-icons-left">
                                    <div class="select">
                                        <select id="country" name="countryId">
                                            <option><fmt:message key="key.profilePageCountryLabel"/></option>
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
                                    <select id="city" name="cityId">
                                        <option><fmt:message key="key.profilePageCityLabel"/></option>
                                        <c:forEach var="city" items="${cities}">
                                            <option value="${city.id}">${city.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div>
                                <p class="help is-danger">${errorMessage}</p>
                                <option><fmt:message key="key.profilePageChangeLocationButton" var="changeLocation"/></option>
                                <input class="button is-fullwidth is-info" type="submit" value="${changeLocation}"/>
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

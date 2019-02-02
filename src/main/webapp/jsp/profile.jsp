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
                    <div class="column is-4 is-offset-4 has-text-centered">
                        <table class="table is-striped">
                            <tr>
                                <th>Avatar</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${principal.profile.image.url != null}">
                                            <img src="${principal.profile.image.url}">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${principal.profile.gender.type == 'female' ? 'static/img/female.png'
                                                                                             : 'static/img/male.png'}">
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/profile"
                                          enctype="multipart/form-data">
                                        <input type="hidden" name="action" value="change-avatar">
                                        <div class="field">
                                            <input type="file" name="image">
                                        </div>
                                        <div>
                                            <p class="help is-danger">${avatarErrorMessage}</p>
                                            <input class="button is-fullwidth is-info" type="submit" value="Change avatar"/>
                                        </div>
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <th>Email</th>
                                <td>${principal.email}</td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/profile">
                                        <input type="hidden" name="action" value="change-email">
                                        <div class="field">
                                            <div class="control">
                                                <div id="emailControl" class="control has-icons-left has-icons-right">
                                                    <input id="email" name="email" type="text" placeholder="new email"
                                                           class="input">
                                                    <span class="icon is-small is-left"><i class="fas fa-envelope"></i></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div>
                                            <p class="help is-danger">${emailErrorMessage}</p>
                                            <input class="button is-fullwidth is-info" type="submit" value="Change email"/>
                                        </div>
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <th>First name</th>
                                <td>${principal.profile.firstName}</td>
                                <td rowspan="4">
                                    <form method="post" action="${pageContext.request.contextPath}/profile">
                                        <input type="hidden" name="action" value="change-password">
                                        <div class="field">
                                            <div id="passwordControl" class="control has-icons-left has-icons-right">
                                                <input id="password" name="password" type="password"
                                                       placeholder="new password" class="input">
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
                                </td>
                            </tr>
                            <tr>
                                <th>Last name</th>
                                <td>${principal.profile.lastName}</td>
                            </tr>
                            <tr>
                                <th>Birthday</th>
                                <td>${principal.profile.birthday}</td>
                            </tr>
                            <tr>
                                <th>Gender</th>
                                <td>${principal.profile.gender.type}</td>
                            </tr>
                            <tr>
                                <th>Country</th>
                                <td>${principal.profile.country.name}</td>
                                <td rowspan="2">
                                    <form method="post" action="${pageContext.request.contextPath}/profile">
                                        <input type="hidden" name="action" value="change-location">
                                        <div class="field is-grouped">
                                            <div class="control has-icons-left">
                                                <div class="select">
                                                    <select name="country_id">
                                                        <option value="${principal.profile.country.id}">
                                                            ${principal.profile.country.name}
                                                        </option>
                                                        <option value="2">USA</option>
                                                    </select>
                                                </div>
                                                <div class="icon is-small is-left">
                                                    <i class="fas fa-globe"></i>
                                                </div>
                                            </div>
                                            <div class="select is-rounded">
                                                <select name="city_id">
                                                    <option value="${principal.profile.city.id}">
                                                        ${principal.profile.city.name}
                                                    </option>
                                                    <option value="2">New York</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div>
                                            <p class="help is-danger">${errorMessage}</p>
                                            <input class="button is-fullwidth is-info" type="submit" value="Change location"/>
                                        </div>
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <th>City</th>
                                <td>${principal.profile.city.name}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

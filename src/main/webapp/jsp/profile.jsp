<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Profile page</title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <table class="table">
            <tr>
                <td>First name</td>
                <td>${user.profile.firstName}</td>
            </tr>
            <tr>
                <td>Last name</td>
                <td>${user.profile.lastName}</td>
            </tr>
            <tr>
                <td>Birthday</td>
                <td>${user.profile.birthday}</td>
            </tr>
            <tr>
                <td>Gender</td>
                <td>${user.profile.gender.type}</td>
            </tr>
        </table>
        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-4 is-offset-4 has-text-centered">
                        <form method="post" action="${pageContext.request.contextPath}/profile">
                            <div class="field">
                                <div class="control">
                                    <div id="emailControl" class="control has-icons-left has-icons-right">
                                        <input id="email" name="email" type="text" value="${user.email}" class="input">
                                        <span class="icon is-small is-left"><i class="fas fa-envelope"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="field is-grouped">
                                <div class="control has-icons-left">
                                    <div class="select">
                                        <select name="country_id">
                                            <option value="${user.profile.country.id}">${user.profile.country.name}</option>
                                            <option value="2">USA</option>
                                        </select>
                                    </div>
                                    <div class="icon is-small is-left">
                                        <i class="fas fa-globe"></i>
                                    </div>
                                </div>
                                <div class="select is-rounded">
                                    <select name="city_id">
                                        <option value="${user.profile.city.id}">${user.profile.city.name}</option>
                                        <option value="2">New York</option>
                                    </select>
                                </div>
                            </div>
                            <div>
                                <p class="help is-danger">${errorMessage}</p>
                                <input class="button is-fullwidth is-info" type="submit" value="Save"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

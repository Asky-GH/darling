<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${language.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title><fmt:message key="key.mainPageTitle"/></title>
    </head>

    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <form action="main" method="post">
                    <div class="columns is-centered has-background-light">
                        <div class="column is-narrow">
                            <div class="field">
                                <label class="label"><fmt:message key="key.mainPageGenderLabel"/></label>
                                <div class="control">
                                    <c:forEach var="gender" items="${genders}">
                                        <label class="radio">
                                            <input type="radio" name="genderId" value="${gender.id}">
                                            ${gender.type}
                                        </label>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div class="column is-narrow">
                            <label class="label"><fmt:message key="key.mainPageAgeLabel"/></label>
                            <div class="field is-grouped">
                                <div class="control">
                                    <fmt:message key="key.mainPageFromAgePlaceholder" var="from"/>
                                    <input class="input" type="number" placeholder="${from}" name="fromAge"
                                           style="width: 100px;" min="18" max="100">
                                </div>
                                <div class="control">
                                    <fmt:message key="key.mainPageToAgePlaceholder" var="to"/>
                                    <input class="input" type="number" placeholder="${to}" name="toAge"
                                           style="width: 100px;" min="18" max="100">
                                </div>
                            </div>
                            <c:if test="${error != null}">
                                <div class="field">
                                    <p class="help is-danger"><fmt:message key="${error}"/></p>
                                </div>
                            </c:if>
                        </div>
                        <div class="column is-narrow">
                            <label class="label"><fmt:message key="key.mainPageLocationLabel"/></label>
                            <div class="field is-grouped">
                                <div class="control has-icons-left">
                                    <div class="select">
                                        <select id="country" name="countryId">
                                            <option><fmt:message key="key.mainPageCountryLabel"/></option>
                                            <c:forEach var="country" items="${countries}">
                                                <option value="${country.id}">${country.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="icon is-small is-left">
                                        <i class="fas fa-globe"></i>
                                    </div>
                                </div>
                                <div class="control">
                                    <div class="select is-rounded">
                                        <select id="city" name="cityId">
                                            <option id="cityLabel"><fmt:message key="key.mainPageCityLabel"/></option>
                                            <c:forEach var="city" items="${cities}">
                                                <option value="${city.id}">${city.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="column is-narrow">
                            <div class="field">
                                <div class="control">
                                    <fmt:message key="key.mainPageSearchButton" var="search"/>
                                    <input class="button is-fullwidth is-info" type="submit" value="${search}">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="columns is-multiline is-centered">
                    <c:forEach var="user" items="${users}">
                        <c:if test="${user.role.type == 'user' && user.id != principal.id}">
                            <div class="column is-narrow" style="padding: 50px">
                                <a href="match?id=${user.id}" style="color: black">
                                    <div class="media">
                                        <div class="media-left">
                                            <figure class="image is-64x64">
                                                <c:choose>
                                                    <c:when test="${user.profile.image.url != null}">
                                                        <img src="${user.profile.image.url}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${user.profile.gender.type == 'Female' ||
                                                                            user.profile.gender.type == 'Женский'}">
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
                                                    <strong>${user.profile.firstName} ${user.profile.lastName}</strong>
                                                    <br>${user.profile.country.name}, ${user.profile.city.name}
                                                    <br><small class="user-age">${user.profile.birthday}</small>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>

        <script>var ctx="${pageContext.request.contextPath}"</script>
	<script defer src="static/js/location.js"></script>
        <script defer src="static/js/age.js"></script>
    </body>
</html>

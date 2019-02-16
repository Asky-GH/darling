<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${language.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
    <head>
        <jsp:include page="../layout/header.jsp"/>
        <title><fmt:message key="key.registrationPageTitle"/></title>
    </head>
    
    <body>
        <jsp:include page="../layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-8 is-offset-2">
                        <form method="post" action="registration">
                            <div class="columns">
                                <div class="column">
                                    <div class="field">
                                        <div class="control has-icons-left has-icons-right">
                                            <c:choose>
                                                <c:when test="${email != null}">
                                                    <c:choose>
                                                        <c:when test="${fn:contains(email, 'key')}">
                                                            <fmt:message key="key.registrationPageEmailPlaceholder" var="emailPlaceholder"/>
                                                            <input class="input is-danger" name="email" type="text" placeholder="${emailPlaceholder}" autofocus>
                                                            <span class="icon is-small is-left"><i class="fas fa-envelope"></i></span>
                                                            <span class="icon is-small is-right"><i class="fas fa-exclamation-triangle"></i></span>
                                                            <p class="help is-danger"><fmt:message key="${email}"/></p>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="input" name="email" type="text" value="${email}">
                                                            <span class="icon is-small is-left"><i class="fas fa-envelope"></i></span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="key.registrationPageEmailPlaceholder" var="emailPlaceholder"/>
                                                    <input class="input" name="email" type="text" placeholder="${emailPlaceholder}" autofocus>
                                                    <span class="icon is-small is-left"><i class="fas fa-envelope"></i></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="control has-icons-left has-icons-right">
                                            <c:choose>
                                                <c:when test="${password != null}">
                                                    <fmt:message key="key.registrationPagePasswordPlaceholder" var="passwordPlaceholder"/>
                                                    <input class="input is-danger" name="password" type="password" placeholder="${passwordPlaceholder}" autofocus>
                                                    <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                                    <span class="icon is-small is-right"><i class="fas fa-exclamation-triangle"></i></span>
                                                    <p class="help is-danger"><fmt:message key="${password}"/></p>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="key.registrationPagePasswordPlaceholder" var="passwordPlaceholder"/>
                                                    <input class="input" name="password" type="password" placeholder="${passwordPlaceholder}">
                                                    <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="control has-icons-left has-icons-right">
                                            <c:choose>
                                                <c:when test="${confirmPassword != null}">
                                                    <fmt:message key="key.registrationPageConfirmPasswordPlaceholder" var="confirmPasswordPlaceholder"/>
                                                    <input class="input is-danger" name="confirmPassword" type="password" placeholder="${confirmPasswordPlaceholder}" autofocus>
                                                    <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                                    <span class="icon is-small is-right"><i class="fas fa-exclamation-triangle"></i></span>
                                                    <p class="help is-danger"><fmt:message key="${confirmPassword}"/></p>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="key.registrationPageConfirmPasswordPlaceholder" var="confirmPasswordPlaceholder"/>
                                                    <input class="input" name="confirmPassword" type="password" placeholder="${confirmPasswordPlaceholder}">
                                                    <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="control has-icons-left has-icons-right">
                                            <c:choose>
                                                <c:when test="${firstName != null}">
                                                    <c:choose>
                                                        <c:when test="${fn:contains(firstName, 'key')}">
                                                            <fmt:message key="key.registrationPageFirstNamePlaceholder" var="firstNamePlaceholder"/>
                                                            <input class="input is-danger" name="firstName" type="text" placeholder="${firstNamePlaceholder}" autofocus>
                                                            <span class="icon is-small is-left"><i class="fas fa-user"></i></span>
                                                            <span class="icon is-small is-right"><i class="fas fa-exclamation-triangle"></i></span>
                                                            <p class="help is-danger"><fmt:message key="${firstName}"/></p>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="input" name="firstName" type="text" value="${firstName}">
                                                            <span class="icon is-small is-left"><i class="fas fa-user"></i></span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="key.registrationPageFirstNamePlaceholder" var="firstNamePlaceholder"/>
                                                    <input class="input" name="firstName" type="text" placeholder="${firstNamePlaceholder}">
                                                    <span class="icon is-small is-left"><i class="fas fa-user"></i></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="control has-icons-left has-icons-right">
                                            <c:choose>
                                                <c:when test="${lastName != null}">
                                                    <c:choose>
                                                        <c:when test="${fn:contains(lastName, 'key')}">
                                                            <fmt:message key="key.registrationPageLastNamePlaceholder" var="lastNamePlaceholder"/>
                                                            <input class="input is-danger" name="lastName" type="text" placeholder="${lastNamePlaceholder}" autofocus>
                                                            <span class="icon is-small is-left"><i class="fas fa-user"></i></span>
                                                            <span class="icon is-small is-right"><i class="fas fa-exclamation-triangle"></i></span>
                                                            <p class="help is-danger"><fmt:message key="${lastName}"/></p>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="input" name="lastName" type="text" value="${lastName}">
                                                            <span class="icon is-small is-left"><i class="fas fa-user"></i></span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="key.registrationPageLastNamePlaceholder" var="lastNamePlaceholder"/>
                                                    <input class="input" name="lastName" type="text" placeholder="${lastNamePlaceholder}">
                                                    <span class="icon is-small is-left"><i class="fas fa-user"></i></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <div class="column">
                                    <div class="field">
                                        <label class="label"><fmt:message key="key.registrationPageBirthdayLabel"/></label>
                                        <div class="control">
                                            <c:choose>
                                                <c:when test="${birthday != null}">
                                                    <c:choose>
                                                        <c:when test="${fn:contains(birthday, 'key')}">
                                                            <input id="date" class="input is-danger" type="date" name="birthday" autofocus>
                                                            <p class="help is-danger"><fmt:message key="${birthday}"/></p>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input id="date" class="input" type="date" name="birthday" value="${birthday}">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <input id="date" class="input" type="date" name="birthday">
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <label class="label"><fmt:message key="key.registrationPageLocationLabel"/></label>
                                        <div class="columns">
                                            <div class="column">
                                                <div class="control has-icons-left">
                                                    <div class="select">
                                                        <select id="country" name="countryId">
                                                            <option><fmt:message key="key.registrationPageCountryLabel"/></option>
                                                            <c:forEach var="country" items="${countries}">
                                                                <option value="${country.id}">${country.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="icon is-small is-left">
                                                        <i class="fas fa-globe"></i>
                                                    </div>
                                                    <c:choose>
                                                        <c:when test="${countryError != null}">
                                                            <p class="help is-danger"><fmt:message key="${countryError}"/></p>
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            <div class="column">
                                                <div class="control">
                                                    <div class="select is-rounded">
                                                        <select id="city" name="cityId">
                                                            <option id="cityLabel"><fmt:message key="key.registrationPageCityLabel"/></option>
                                                            <c:forEach var="city" items="${cities}">
                                                                <option value="${city.id}">${city.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <c:choose>
                                                        <c:when test="${cityError != null}">
                                                            <p class="help is-danger"><fmt:message key="${cityError}"/></p>
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <label class="label"><fmt:message key="key.registrationPageGenderLabel"/></label>
                                        <c:choose>
                                            <c:when test="${genderId != null}">
                                                <c:choose>
                                                    <c:when test="${fn:contains(genderId, 'key')}">
                                                        <div class="control">
                                                            <c:forEach var="gender" items="${genders}">
                                                                <label class="radio">
                                                                    <input type="radio" name="genderId" value="${gender.id}">
                                                                        ${gender.type}
                                                                </label>
                                                            </c:forEach>
                                                        </div>
                                                        <p class="help is-danger"><fmt:message key="${genderId}"/></p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="control">
                                                            <c:forEach var="gender" items="${genders}">
                                                                <label class="radio">
                                                                    <c:choose>
                                                                        <c:when test="${genderId == gender.id}">
                                                                            <input type="radio" name="genderId" value="${gender.id}" checked>
                                                                            ${gender.type}
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <input type="radio" name="genderId" value="${gender.id}">
                                                                            ${gender.type}
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </label>
                                                            </c:forEach>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="control">
                                                    <c:forEach var="gender" items="${genders}">
                                                        <label class="radio">
                                                            <input type="radio" name="genderId" value="${gender.id}">
                                                                ${gender.type}
                                                        </label>
                                                    </c:forEach>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                            <div class="field has-text-centered">
                                <fmt:message key="key.registrationPageRegisterButton" var="register"/>
                                <input class="button is-fullwidth is-info" type="submit" value="${register}"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="../layout/footer.jsp"/>

        <script defer src="static/js/location.js"></script>
        <script defer src="static/js/calendar.js"></script>
    </body>
</html>

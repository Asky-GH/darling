<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                                            <fmt:message key="key.registrationPageEmailPlaceholder" var="email"/>
                                            <input class="input" name="email" type="text" placeholder="${email}" autofocus>
                                            <span class="icon is-small is-left"><i class="fas fa-envelope"></i></span>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="control has-icons-left has-icons-right">
                                            <fmt:message key="key.registrationPagePasswordPlaceholder" var="password"/>
                                            <input class="input" name="password" type="password" placeholder="${password}">
                                            <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="control has-icons-left has-icons-right">
                                            <fmt:message key="key.registrationPageConfirmPasswordPlaceholder" var="confirmPassword"/>
                                            <input class="input" name="confirmPassword" type="password" placeholder="${confirmPassword}">
                                            <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="control has-icons-left has-icons-right">
                                            <fmt:message key="key.registrationPageFirstNamePlaceholder" var="firstName"/>
                                            <input class="input" name="firstName" type="text" placeholder="${firstName}">
                                            <span class="icon is-small is-left"><i class="fas fa-user"></i></span>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="control has-icons-left has-icons-right">
                                            <fmt:message key="key.registrationPageLastNamePlaceholder" var="lastName"/>
                                            <input class="input" name="lastName" type="text" placeholder="${lastName}">
                                            <span class="icon is-small is-left"><i class="fas fa-user"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="column">
                                    <div class="field">
                                        <label class="label"><fmt:message key="key.registrationPageBirthdayLabel"/></label>
                                        <div class="control">
                                            <input class="input" type="date" name="birthday">
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
                                                </div>
                                            </div>
                                            <div class="column">
                                                <div class="control">
                                                    <div class="select is-rounded">
                                                        <select id="city" name="cityId">
                                                            <option><fmt:message key="key.registrationPageCityLabel"/></option>
                                                            <c:forEach var="city" items="${cities}">
                                                                <option value="${city.id}">${city.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <label class="label"><fmt:message key="key.registrationPageGenderLabel"/></label>
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
                            </div>
                            <div class="field has-text-centered">
                                <p class="help is-danger">${errorMessage}</p>
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
    </body>
</html>

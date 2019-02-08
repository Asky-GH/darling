<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${language.locale}"/>
<fmt:setBundle basename="locale"/>

<nav class="navbar has-shadow is-spaced" roleId="navigation" aria-label="main navigation">
    <div class="navbar-brand">
        <a class="navbar-item" href="">
            <img src="static/img/logo.png" style="margin-right:5px;"><b>Darling</b>
        </a>
        <a roleId="button" class="navbar-burger" data-target="navMenu" aria-label="menu" aria-expanded="false">
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
        </a>
    </div>
    <div class="navbar-menu">
        <div class="navbar-end">
            <a class="navbar-item" href="main"><fmt:message key="key.navbarLinkToMain"/></a>
            <c:choose>
                <c:when test="${principal != null}">
                    <div class="navbar-item has-dropdown is-hoverable">
                        <p class="navbar-link">${principal.email}</p>
                        <div class="navbar-dropdown is-boxed">
                            <a class="navbar-item" href="profile"><fmt:message key="key.navbarLinkToProfile"/></a>
                            <a class="navbar-item" href="messages"><fmt:message key="key.navbarLinkToMessages"/></a>
                            <hr class="navbar-divider">
                            <a class="navbar-item" href="logout"><fmt:message key="key.navbarLinkToLogout"/></a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a class="navbar-item" href="login"><fmt:message key="key.navbarLinkToLogin"/></a>
                    <a class="navbar-item" href="registration"><fmt:message key="key.navbarLinkToRegistration"/></a>
                </c:otherwise>
            </c:choose>
            <div class="navbar-item">
                <div class="select">
                    <form action="language" method="post">
                        <input type="hidden" name="from" value="${pageContext.request.requestURI}">
                        <select name="languageId" onchange="this.form.submit()">
                            <option><fmt:message key="key.navbarLanguageSelect"/></option>
                            <c:forEach var="language" items="${languages}">
                                <option value="${language.id}">${language.name}</option>
                            </c:forEach>
                        </select>
                    </form>
                </div>
            </div>
        </div>
    </div>
</nav>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar has-shadow is-spaced" roleId="navigation" aria-label="main navigation">
    <div class="navbar-brand">
        <a class="navbar-item" href="${pageContext.request.contextPath}/">
            <img src="static/img/logo.png" alt="Logo" style="margin-right:5px;"><b>Darling</b>
        </a>
        <a roleId="button" class="navbar-burger" data-target="navMenu" aria-label="menu" aria-expanded="false">
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
        </a>
    </div>
    <div class="navbar-menu">
        <div class="navbar-end">
            <a class="navbar-item" href="${pageContext.request.contextPath}/main">Main</a>
            <c:choose>
                <c:when test="${principal != null}">
                    <div class="navbar-item has-dropdown is-hoverable">
                        <p class="navbar-link">${principal.email}</p>
                        <div class="navbar-dropdown is-boxed">
                            <a class="navbar-item" href="${pageContext.request.contextPath}/profile">Profile</a>
                            <hr class="navbar-divider">
                            <a class="navbar-item" href="${pageContext.request.contextPath}/logout">Logout</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a class="navbar-item" href="${pageContext.request.contextPath}/login">Login</a>
                    <a class="navbar-item" href="${pageContext.request.contextPath}/registration">Registration</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

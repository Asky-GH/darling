<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar has-shadow is-spaced" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
        <a class="navbar-item" href="${pageContext.request.contextPath}/">
            <img src="img/logo.png" alt="Logo" style="margin-right:5px;"><b>Darling</b>
        </a>
        <a role="button" class="navbar-burger" data-target="navMenu" aria-label="menu" aria-expanded="false">
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
        </a>
    </div>
    <div class="navbar-menu">
        <div class="navbar-end">
            <a class="navbar-item" href="${pageContext.request.contextPath}/">Main</a>
            <c:choose>
                <c:when test="${user != null}">
                    <div class="navbar-item has-dropdown is-hoverable">
                        <p class="navbar-link">${user.email}</p>
                        <div class="navbar-dropdown is-boxed">
                            <a class="navbar-item" href="#"
                               onclick="event.preventDefault();
                                        document.getElementById('profile-form').submit();">Profile</a>
                            <form id="profile-form" method="post" style="display: none;"
                                  action="${pageContext.request.contextPath}/profile">
                                <input type="hidden" name="action" value="get-profile">
                            </form>
                            <hr class="navbar-divider">
                            <a class="navbar-item" href="#"
                               onclick="event.preventDefault();
                                        document.getElementById('logout-form').submit();">Logout</a>
                            <form id="logout-form" method="post" style="display: none;"
                                  action="${pageContext.request.contextPath}/logout">
                                <input type="hidden" name="action" value="logout">
                            </form>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a class="navbar-item" href="#" onclick="event.preventDefault();
                                                             document.getElementById('login-form').submit();">Login</a>
                    <form id="login-form" method="post" style="display: none;"
                          action="${pageContext.request.contextPath}/login">
                        <input type="hidden" name="action" value="get-login">
                    </form>
                    <a class="navbar-item" href="#"
                       onclick="event.preventDefault(); document.getElementById('login-form').submit();">Registration</a>
                    <form id="registration-form" method="post" style="display: none;"
                          action="${pageContext.request.contextPath}/registration">
                        <input type="hidden" name="action" value="get-registration">
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

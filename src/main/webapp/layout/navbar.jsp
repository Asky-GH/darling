<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar has-shadow is-spaced" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
        <a class="navbar-item" href="/"><img src="/img/logo.png" alt="Logo" style="margin-right:5px;" width="42"><b>Darling</b></a>
        <a role="button" class="navbar-burger" data-target="navMenu" aria-label="menu" aria-expanded="false">
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
        </a>
    </div>
    <div class="navbar-menu">
        <div class="navbar-end">
            <a class="navbar-item" href="/">Main</a>
            <c:choose>
                <c:when test="${user != null}">
                    <div class="navbar-item has-dropdown is-hoverable">
                        <p class="navbar-link">${user.email}</p>
                        <div class="navbar-dropdown is-boxed">
                            <a class="navbar-item" href="/profile">Profile</a>
                            <hr class="navbar-divider">
                            <a class="navbar-item" href="#" onclick="event.preventDefault(); document.getElementById('logout-form').submit();">Logout</a>
                            <form id="logout-form" method="post" style="display: none;" action="/logout"></form>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a class="navbar-item" href="/login">Login</a>
                    <a class="navbar-item" href="/registration">Registration</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${language.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
    <head>
        <jsp:include page="../layout/header.jsp"/>
        <title><fmt:message key="key.loginPageTitle"/></title>
    </head>
    
    <body>
        <jsp:include page="../layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns has-text-centered">
                    <div class="column is-4 is-offset-4">
                        <form method="post" action="login">
                            <input type="hidden" name="from" value="${from}">
                            <div class="field">
                                <div class="control has-icons-left has-icons-right">
                                    <fmt:message key="key.loginPageEmailPlaceholder" var="email"/>
                                    <input class="input" type="text" name="email" placeholder="${email}" autofocus>
                                    <span class="icon is-small is-left"><i class="fas fa-envelope"></i></span>
                                </div>
                            </div>
                            <div class="field">
                                <div class="control has-icons-left has-icons-right">
                                    <fmt:message key="key.loginPagePasswordPlaceholder" var="password"/>
                                    <input class="input" type="password" name="password" placeholder="${password}">
                                    <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                </div>
                            </div>
                            <div class="field">
                                <p class="help is-danger">${errorMessage}</p>
                                <div class="control">
                                    <fmt:message key="key.loginPageLoginButton" var="login"/>
                                    <input class="button is-fullwidth is-info" type="submit" value="${login}">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="../layout/footer.jsp"/>
    </body>
</html>

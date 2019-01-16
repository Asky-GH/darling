<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Registration page</title>
    </head>
    
    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-4 is-offset-4 has-text-centered">
                        <form method="post" action="/registration">
                            <div class="field">
                                <div class="control">
                                    <div id="emailControl" class="control has-icons-left has-icons-right">
                                        <input id="email" name="email" tabindex="1" type="text" placeholder="email" autofocus class="input">
                                        <span class="icon is-small is-left"><i class="fas fa-envelope"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="field">
                                <div id="passwordControl" class="control has-icons-left has-icons-right">
                                    <input id="password" name="password" tabindex="2" type="password" placeholder="password" class="input">
                                    <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                </div>
                            </div>
                            <div class="field">
                                <div id="confirmControl" class="control has-icons-left has-icons-right">
                                    <input id="confirm" name="confirmPassword" tabindex="3" type="password" placeholder="confirm password" class="input">
                                    <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                </div>
                                <p class="help is-danger">${errorMessage}</p>
                            </div>
                            <div>
                                <input tabindex="4" class="button is-fullwidth is-info" type="submit" value="Register"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

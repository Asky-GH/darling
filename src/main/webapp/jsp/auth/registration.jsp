<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="../layout/header.jsp"/>
        <title>Registration page</title>
    </head>
    
    <body>
        <jsp:include page="../layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-4 is-offset-4 has-text-centered">
                        <form method="post" action="${pageContext.request.contextPath}/registration">
                            <div class="field">
                                <div class="control">
                                    <div id="emailControl" class="control has-icons-left has-icons-right">
                                        <input id="email" name="email" type="text" placeholder="email"
                                               autofocus class="input">
                                        <span class="icon is-small is-left"><i class="fas fa-envelope"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="field">
                                <div id="passwordControl" class="control has-icons-left has-icons-right">
                                    <input id="password" name="password" type="password"
                                           placeholder="password" class="input">
                                    <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                </div>
                            </div>
                            <div class="field">
                                <div id="confirmControl" class="control has-icons-left has-icons-right">
                                    <input id="confirm" name="confirmPassword" type="password"
                                           placeholder="confirm password" class="input">
                                    <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                </div>
                            </div>
                            <div class="field">
                                <div id="fNameControl" class="control has-icons-left has-icons-right">
                                    <input id="fName" name="firstName" type="text"
                                           placeholder="first name" class="input">
                                    <span class="icon is-small is-left"><i class="fas fa-user"></i></span>
                                </div>
                            </div>
                            <div class="field">
                                <div id="lNameControl" class="control has-icons-left has-icons-right">
                                    <input id="lName" name="lastName" type="text"
                                           placeholder="last name" class="input">
                                    <span class="icon is-small is-left"><i class="fas fa-user"></i></span>
                                </div>
                            </div>
                            <div class="field is-grouped">
                                <div class="control">Birthday</div>
                                <div class="control">
                                    <input class="input" type="date" name="birthday">
                                </div>
                            </div>
                            <div class="field is-grouped">
                                <div class="control">Gender</div>
                                <div class="control">
                                    <label class="radio">
                                        <input type="radio" name="gender" value="1" checked>
                                        Female
                                    </label>
                                    <label class="radio">
                                        <input type="radio" name="gender" value="2">
                                        Male
                                    </label>
                                </div>
                            </div>
                            <div class="field is-grouped">
                                <div class="control has-icons-left">
                                    <div class="select">
                                        <select name="country_id">
                                            <option value="1">Kazakhstan</option>
                                            <option>Select dropdown</option>
                                            <option>With options</option>
                                        </select>
                                    </div>
                                    <div class="icon is-small is-left">
                                        <i class="fas fa-globe"></i>
                                    </div>
                                </div>
                                <div class="select is-rounded">
                                    <select name="city_id">
                                        <option value="1">Astana</option>
                                        <option>With options</option>
                                    </select>
                                </div>
                            </div>
                            <div>
                                <p class="help is-danger">${errorMessage}</p>
                                <input class="button is-fullwidth is-info" type="submit" value="Register"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="../layout/footer.jsp"/>
    </body>
</html>

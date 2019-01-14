<!DOCTYPE html>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Login page</title>
    </head>
    
    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-4 is-offset-4 has-text-centered">
                        <form method="post" action="/login">
                            <div class="field">
                                <div class="control has-icons-left has-icons-right">
                                    <input tabindex="1" class="input" type="text" name="email" placeholder="email" autofocus>
                                    <span class="icon is-small is-left"><i class="fas fa-user"></i></span>
                                </div>
                            </div>
                            <div class="field">
                                <div class="control has-icons-left has-icons-right">
                                    <input tabindex="2" class="input" type="password" name="password" placeholder="password">
                                    <span class="icon is-small is-left"><i class="fas fa-lock"></i></span>
                                </div>
                                <p class="help is-danger">${errorMessage}</p>
                            </div>
                            <div class="field">
                                <div class="control">
                                    <input tabindex="3" class="button is-fullwidth is-info" type="submit" value="Login">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

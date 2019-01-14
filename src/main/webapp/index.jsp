<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="layout/header.jsp"/>
        <title>Main page</title>
    </head>
    
    <body>
        <jsp:include page="layout/navbar.jsp"/>

        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-4 is-offset-4 has-text-centered">
                        <c:choose>
                            <c:when test="${user != null}">
                                <p>Welcome ${user.email}!</p>
                            </c:when>
                            <c:otherwise>
                                <p>Welcome guest!</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>

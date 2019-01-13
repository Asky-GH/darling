<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login page</title>
    </head>
    
    <body>
        <form action="/login" method="post">
            <input type="text" name="email" placeholder="email">
            <input type="password" name="password" placeholder="password">
            <input type="submit"> ${errorMessage}
        </form>
    </body>
</html>

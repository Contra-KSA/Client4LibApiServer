<html>
<head>
    <title>Login form</title>
</head>
<body>
<h1>Login form</h1>
<form action="/auth" method="POST">
    login: <input name="login" type="text"/><br/>
    password: <input name="password" type="password"/><br/>
    <input type="submit" value="Login"/>
</form>
<h1>${message}</h1>
</body>
</html>
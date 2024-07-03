<html>
<head>
    <title>Create user form</title>
</head>
<body>
<h1>Create user form</h1>
<form action="users" method="POST">
    login: <input name="login" type="text"/><br/>
    password: <input name="password" type="password"/><br/>
    <input type="submit" value="Create"/>
</form>
<h1>${message}</h1>
</body>
</html>
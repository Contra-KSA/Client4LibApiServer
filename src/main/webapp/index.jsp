<html>
<head>
    <title>Catalog start page</title>
</head>
<body>
<h1>Catalog search page</h1>
<form action="catalog/search" method="GET">
    <h4>Searching books by title and/or year</h4>
    Title: <input name="title"><br>
    Year: <input name="year"><br>
    Author: <input name="author"><br>
    <br>
    <input type="submit" value="Search">
</form>

<br>
<form action="auth/logout" method="GET">
    <input type="submit" value="Logout">
</form>
</body>
</html>
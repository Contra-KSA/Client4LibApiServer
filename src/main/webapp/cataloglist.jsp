<%@ page import="my.exam.catalog.client.catalog_client.dto.BookDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>

    <title>Catalog</title>
</head>
<body>
<h1>Catalog</h1>
<form action="/catalog">
    <input type="submit" value="Back to search">
</form>

<%--&lt;%&ndash;%>
<%--    String[] tagTypes = new String[]{"for", "if", "case", "import"};--%>
<%--    request.setAttribute("urls", tagTypes);--%>
<%--%>--%>

<h1>Result:</h1>
<table border="1px solid gray">
<thead>
<tr>
    <th>Id</th>
    <th>Title</th>
    <th>Year</th>
    <th>ISBH</th>
    <th>Authors</th>
</tr>
</thead>

    <%
        List<BookDTO> books = (List) request.getAttribute("books");
    %>
    <%

        for (BookDTO book : books) {
            int yearInt = book.getYear();
            String year = ( yearInt == 0) ? "" : String.valueOf(yearInt);
    %>
    <tr>
        <td><%=book.getId()%>
        </td>
        <td><%=book.getTitle()%></td>
<%--        <td><%=book.getYear()%></td>--%>
        <td><%=year%></td>
        <td><%=book.getIsbn()%></td>
        <td><%=book.getAuthorsForWeb()%></td>
    </tr>
    <%
        }
    %>

    <h3>${message}</h3>
</table>

</body>

</html>
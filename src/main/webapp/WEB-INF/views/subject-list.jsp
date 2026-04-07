<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.alexnta.curriculumplanner.model.Subject" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Subject List</title>
</head>
<body>
<h1>Subject List</h1>
<p><a href="${pageContext.request.contextPath}/home">Back to Home</a></p>

<%
    List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
%>

<table border="1" cellpadding="6" cellspacing="0">
    <thead>
    <tr>
        <th>Code</th>
        <th>Name</th>
        <th>Term</th>
        <th>Active</th>
    </tr>
    </thead>
    <tbody>
    <% if (subjects != null) { %>
        <% for (Subject subject : subjects) { %>
        <tr>
            <td><%= subject.getCode() %></td>
            <td><%= subject.getName() %></td>
            <td><%= subject.getTermNo() %></td>
            <td><%= subject.isActive() ? "Yes" : "No" %></td>
        </tr>
        <% } %>
    <% } %>
    </tbody>
</table>
</body>
</html>

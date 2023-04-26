<%@ page import="org.bson.Document" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Bobby Yang
  Email: zehuay@andrew.cmu.edu
  Date: 4/6/23
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>NBA STATS</title>
</head>
<body>
<div>
  <h2>Operation Analysis</h2>
  <h3>The fastest api search time</h3>
  <%= request.getSession().getAttribute("fast_api") %>
  <h3>The fastest android search time</h3>
  <%= request.getSession().getAttribute("fast_android") %>
  <h3>The number of searching times</h3>
  <%= request.getSession().getAttribute("count") %>
  <div>___________________________________________________________________________
    ___________________________________________</div>
  <h2>LOGS</h2>
  <table class="table table-striped table-bordered">
    <thead>
    <tr>
      <th scope="col">id</th>
      <th scope="col">Start Time</th>
      <th scope="col">End Time</th>
      <th scope="col">Total Api Time</th>
      <th scope="col">Total Android Time</th>
      <th scope="col">Search Input</th>
      <th scope="col">Result</th>
    </tr>
    </thead>
    <% List<Document> logs = (List<Document>) request.getSession().getAttribute("logs"); %>
    <tbody>
    <% for (Document doc: logs) { %>
    <tr>
      <td><%= doc.get("_id")%></td>
      <td><%= doc.get("Start Time") %></td>
      <td><%= doc.get("End Time") %></td>
      <td><%= doc.get("Total Api Time")%></td>
      <td><%= doc.get("Total Android Time")%></td>
      <td><%= doc.get("Search Input")%></td>
      <td><%= doc.get("Result")%></td>
    </tr>
    <%} %>
    </tbody>
  </table>
</div>
</body>
</html>

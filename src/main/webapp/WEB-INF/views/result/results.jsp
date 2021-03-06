<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="hu.icell.entities.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Results</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />"/>
</head>
<body>
<div id="body">
	<div id="nav">
		<ul>
			<li><a href="<%= request.getContextPath() %>">Game</a></li>
			<li><a class="active" href="<%= request.getContextPath() %>/result">Results</a></li>
			<li><a href="<%= request.getContextPath() %>/result/<%= ((User)request.getSession().getAttribute("user")).getId() %>">My results</a></li>
			<li class="right" ><a href="<%= request.getContextPath() %>/logout">Logout</a></li>
		</ul>
	</div>
	<h3>Results (only 6x6)</h3>
	<% 
		List<Result> list = (List<Result>) request.getAttribute("list");
		if (list != null && !list.isEmpty()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			out.println("<table>");
			out.println("<tr><th>ID</th><th>Date</th><th>Seconds</th><th>Username</th></tr>");
			for(Result r: list) {%>
				<tr><th><%= r.getId() %></th><th><%= dateFormat.format(r.getResultDate()) %></th><th><%= r.getSeconds() %></th><th><%= r.getUser() %></th></tr>
			<%
			}
			out.println("</table>");
		} else {
			out.println("LIST: " + list);
		}
	%>
</div>
</body>
</html>
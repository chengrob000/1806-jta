<%@page import="com.revature.dao.EmployeeDao"%>
<%@page import="com.revature.displaywrapper.EmployeeDisplay"%>
<%@page import="com.revature.utils.HtmlTemplates"%>
<%@page import="com.revature.service.UserService"%>
<%@page import="com.revature.service.EventService"%>
<%@page import="com.revature.service.EmployeeService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" type="text/css" href="../resources/css/main.css">
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<!-- Bring Bootstrap 3 libraries -->
<script src="http://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<title>Welcome to Project1</title>
<script src="../resources/js/employeeHome.js"></script>
<script src="../resources/js/events.js"></script>
<script src="../resources/js/common.js"></script>
<link rel="import" href="../template/userbody.html">
<link rel="import" href="../template/navbar.html">
<link rel="import" href="../template/subevents.html">
</head>
<body>

	<div id="navDiv"></div>
	<br>	
	<div id="container" class="container-fluid"></div>

	<script>
		createNavbar();
	    addFromTemplate("userbody", "#container");
	    addFromTemplate("subevents", "#container");
	   
	    <%Integer userId = (Integer) session.getAttribute("userId");%>;
	    
	    let json = <%out.write(EmployeeService.getEmployeeDisplay(userId));%>;
		createEmployeeSummary(json);
		setAmountRemaining(json);
		
		fetchEvents("all", "subTableBody", createEventTableWithEmpName, true);
		fetchEvents("all", "tableBody", createEventTableSmall, false);
	</script>
	
</body>
</html>
<jsp:include page="header.jsp" />
<script src="js/validation.js"></script>

<form method="post" action="#" id="registerForm" onsubmit="return validateRegistration()">
	Username:<input type="text" name="username" /><br/>
	E-mail:<input type="email" name="email" /><br/>
	Login password:
	<div id="passwordMessage">
	<input type="password1" name="password" /><br/>
	<input type="password" name="password2" /><br/>
	</div>
	Verify password:<input type="password" name="verifyPassword" /><br/>
	First name:<input type="text" name="firstName" /><br/>
	Last name:<input type="text" name="lastName" /><br />
	Phone number:<input type="text" name="phoneNumber" /><br />
	<input type="submit" value="register" />
</form>
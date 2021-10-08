<html>
<head>
	<title>Online Banking System</title>
	<script type="text/javascript">
	function validateLoginForm(){

		var user_nm	= document.getElementById("user_nm");
		var pass_wd	= document.getElementById("pass_wd");
		var user_ln = user_nm.value.length;
		var pass_ln = pass_wd.value.length;

		if (user_ln == 0) {

			alert("Username should not be empty!!");  
			user_nm.focus();  
			return false;
		}
	 
		if (pass_ln == 0) {

			alert("Password should not be empty!!");
			pass_wd.focus();
			return false;
		}

		return true;
	}
	</script>
</head>
<body bgcolor="33FFB2">
	<div id="container" align="center"><br/>
	<h2>Banking System</h2>
		<form name="loginForm" action="main" onsubmit="javascript:return validateLoginForm();" method="post">
			<%
			String error = (String)request.getAttribute("error");
			if(error == "yes") {
			%>
				<p style="color:red;font-size:20px;text-align:center">Invalid Username/ Password</p><br/>
			<% } else { %>
				<br/>
			<% } %>
			<table border="0" align="center">
				<tr><td><b>Username</b></td><td>:</td><td><input type="text" name="user_nm" id="user_nm" align="center" />*</td></tr>
				<tr><td><b>Password</b></td><td>:</td><td><input type="password" name="pass_wd" id="pass_wd" align="center" />*</td></tr>
				<tr><td colspan="3">&nbsp;</td></tr>
				<tr><td colspan="3" align="center"><input type="submit" value="Submit" align="center"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Clear" align="center"/></td></tr>
			</table>
		</form>
	</div>
</body>
</html>
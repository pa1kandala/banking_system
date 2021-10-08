<html>
<head>
	<title>Account Creation</title>
	<script type="text/javascript">
	function validateAccountForm(){

		var user_name	= document.getElementById("user_name");
		var user_dtob	= document.getElementById("user_dtob");
		var user_addr	= document.getElementById("user_addr");
		var user_email	= document.getElementById("user_email");

		var min_len = 6;
		var max_len = 10;
		var user_ln = user_name.value.length;
		var pattern = /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;

		if (user_ln == 0) {

			alert("Name should not be empty!!");  
			user_name.focus();  
			return false;

		} else if (user_ln > max_len || user_ln < min_len) {

			alert("Name length should be between "+min_len+" to "+max_len+" characters.");  
			user_name.focus();  
			return false;
		}

		if (!pattern.test(user_dtob.value)) {

			alert("Input a Valid DOB [dd/mm/yyyy or dd-mm-yyyy format]");
			user_dtob.focus();
			return false;

		}
		return true;
	}
	</script>
</head>
<body bgcolor="33FFB2">
	<div id="container" align="center"><br/>
	<h2>Account Creation Screen</h2>
		<form name="accountForm" action="account" onsubmit="javascript:return validateAccountForm();" method="post">
			<%
			String error = (String)request.getAttribute("error");
			if (error == "yes") {
			%>
				<p style="color:red;font-size:20px;text-align:left">User Account is not created!!</p><br/>
			<% } %>

			<table border="0" align="left">
				<tr><td><b>Name</b></td><td>:</td><td><input type="text" name="user_name" id="user_name" align="left" />*</td></tr>
				<tr><td><b>DOB</b></td><td>:</td><td><input type="text" name="user_dtob" id="user_dtob" align="left" />*</td></tr>
				<tr><td valign="top"><b>Address</b></td><td valign="top">:</td><td><textarea name="user_addr" id="user_addr" align="left" rows="7" cols="40"></textarea>*</td></tr>
				<tr><td><b>Email</b></td><td>:</td><td><input type="text" name="user_email" id="user_email" align="left" />*</td></tr>
				<tr><td><b>Type&nbsp;of&nbsp;Account</b></td><td>:</td>
					<td>
						<select name="user_acct" id="user_acct">
							<option value="saving" selected>SB Account</option>
							<option value="current">CR Account</option>
						</select><span valign="top">*</span>
					</td>
				</tr>
				<tr><td colspan="3">&nbsp;</td></tr>
				<tr><td colspan="3" align="left"><input type="submit" value="Create" align="left"/></td></tr>
			</table>
		</form>
	</div>
</body>
</html>